package eu.senla;

import eu.senla.converter.Mapper;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;
import eu.senla.dto.UserInfoDTO;
import eu.senla.dto.UserRegistrationDTO;
import eu.senla.exception.EntityNotFoundException;
import eu.senla.exception.EntityNotUniqueException;
import eu.senla.exception.SortParameterNotFoundException;
import eu.senla.exception.UpdateInfoException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static eu.senla.Credentials_.LOGIN;
import static eu.senla.User_.EMAIL;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private CredentialsDao credentialsDao;
    private Mapper mapper;
    private UserDetailsServiceImpl userDetailsService;
    private RoleDao roleDao;
    private WallDao wallDao;
    private static final Integer USER_ROLE_ID = 2;


    @Override
    public UserInfoDTO add(UserRegistrationDTO userRegistrationDto) {
        log.info("Attempt to add user");
        log.info("Check whether the user with such login exists");
        User existedUserWithLogin = userDao.findByLogin(userRegistrationDto.getCredentialsDto().getLogin());
        log.info("Check whether the user with such email exists");
        User existedUserWithEmail = userDao.findByEmail(userRegistrationDto.getCredentialsDto().getLogin());
        if (existedUserWithLogin != null) {
            throw new EntityNotUniqueException(User.class, LOGIN);
        }
        if (existedUserWithEmail != null) {
            throw new EntityNotUniqueException(User.class, EMAIL);
        }
        User newUser = (User) mapper.convertToEntity(new User(), userRegistrationDto);
        newUser.setActiveStatus(true);
        Credentials credentials = newUser.getCredentials();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        credentials.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getCredentialsDto().getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.findOne(USER_ROLE_ID));
        newUser.setRoles(roles);
        Wall wall = new Wall();
        User registeredUser = userDao.save(newUser);
        credentials.setUser(registeredUser);
        wall.setUser(registeredUser);
        credentialsDao.save(credentials);
        wallDao.save(wall);
        log.info("New user was added");
        return (UserInfoDTO) mapper.convertToDto(registeredUser, new UserInfoDTO());
    }

    @Override
    public UserInfoDTO addFriend(Integer friendId) {
        log.info("Attempt to add friend");
        User user = userDao.findByLogin(userDetailsService.getCurrentLogin());
        User requestFriend = userDao.findOne(friendId);
        if (requestFriend == null) {
            throw new EntityNotFoundException(User.class, User_.ID, friendId);
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, LOGIN, userDetailsService.getCurrentLogin());
        }
        UserRelationship userRelationship = new UserRelationship();
        UserRelationshipKey userRelationshipKey = new UserRelationshipKey(user, requestFriend);
        log.info("Change user status from request to added");
        userRelationship.setStatus(UserStatus.REQUEST);
        userRelationship.setUserRelationshipKey(userRelationshipKey);
        user.getFriends().add(userRelationship);
        userDao.update(user);
        log.info("User was added to friends");
        return (UserInfoDTO) mapper.convertToDto(requestFriend, new UserInfoDTO());
    }

    @Override
    public UserInfoDTO acceptFriendRequest(Integer friendId) {
        log.info("Attempt to accept friend request");
        return changeStatus(friendId, UserStatus.ADDED);
    }

    @Override
    public UserInfoDTO blockUser(Integer userId) {
        log.info("Attempt to block user");
        return changeStatus(userId, UserStatus.BLOCKED);
    }

    private UserInfoDTO changeStatus(Integer friendId, UserStatus userStatus) {
        log.info("Attempt to change user status");
        User user = userDao.findByLogin(userDetailsService.getCurrentLogin());
        Set<UserRelationship> userFriendRequests = user.getFriends();
        UserRelationship userRequest = userFriendRequests
                .stream()
                .filter(userRelationship -> userRelationship.getUserId().getId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(User.class, User_.ID, friendId.toString()));
        userRequest.setStatus(userStatus);
        user.getFriends().add(userRequest);
        log.info("Update user");
        userDao.update(user);
        User friend = userDao.findOne(friendId);
        UserRelationshipKey userRelationshipKey = new UserRelationshipKey(user, friend);
        UserRelationship friendAcceptance = new UserRelationship();
        friendAcceptance.setUserRelationshipKey(userRelationshipKey);
        log.info("Create relationship between users");
        friend.getFriends().add(friendAcceptance);
        friendAcceptance.setStatus(userStatus);
        userDao.update(friend);
        log.info("Status was changed");
        return (UserInfoDTO) mapper.convertToDto(userRequest.getUserId(), new UserInfoDTO());
    }


    @Override
    public UserInfoDTO update(UserInfoDTO userInfoDto) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            User user = userDao.findOne(userInfoDto.getId());
            log.info("Attempt to update user {} info", user.getId());
            user.setName(userInfoDto.getName());
            user.setSurname(userInfoDto.getSurname());
            user.setBirthday(simpleDateFormat.parse(userInfoDto.getBirthday()));
            User updatedUser = userDao.update(user);
            log.info("User {} info ", user.getId());
            return (UserInfoDTO) mapper.convertToDto(updatedUser, new UserInfoDTO());
        } catch (Exception e) {
            log.error("Cannot update user info: " + e.getMessage());
        }
        throw new UpdateInfoException();
    }

    @Override
    public List<UserInfoDTO> findAll(PaginationDTO paginationDTO) {
        log.info("Attempt to find all users");
        List<User> users = userDao.findAll(paginationDTO);
        log.info("Return all users");
        return users
                .stream()
                .map(user -> (UserInfoDTO) mapper.convertToDto(user, new UserInfoDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public UserInfoDTO findOne(Integer id) {
        log.info("Attempt to find user by id {}", id);
        User user = userDao.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, User_.ID, id.toString());
        }
        log.info("Return searched user with id {} ", id);
        return (UserInfoDTO) mapper.convertToDto(user, new UserInfoDTO());
    }

    @Override
    public User getByLogin(String login) {
        log.info("Attempt to find user by login {}", login);
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException(User.class, LOGIN, login);
        }
        log.info("Return the user with login {}", login);
        return user;
    }

    @Override
    public UserInfoDTO deactivateUser(Integer id) {
        log.info("Attempt to deactivate user");
        User user = userDao.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException(User.class, LOGIN, id);
        }
        user.setActiveStatus(false);
        User updatedUser = userDao.update(user);
        UserInfoDTO userInfoDTO = (UserInfoDTO) mapper.convertToDto(updatedUser, new UserInfoDTO());
        log.info("User was deactivated");
        return userInfoDTO;
    }

}
