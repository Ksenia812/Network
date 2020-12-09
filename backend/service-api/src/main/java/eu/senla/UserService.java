package eu.senla;

import eu.senla.dto.PaginationDTO;
import eu.senla.dto.UserInfoDTO;
import eu.senla.dto.UserRegistrationDTO;

import java.util.List;

public interface UserService {

//    Map<String, String> login(CredentialsDTO credentialsDto);

    UserInfoDTO add(UserRegistrationDTO userRegistrationDto);

    UserInfoDTO addFriend(Integer friendId);

    UserInfoDTO acceptFriendRequest(Integer friendId);

    UserInfoDTO blockUser(Integer userId);

    UserInfoDTO update(UserInfoDTO userInfoDto);

    List<UserInfoDTO> findAll(PaginationDTO paginationDTO);

    UserInfoDTO findOne(Integer id);

    User getByLogin(String login);

    UserInfoDTO deactivateUser(Integer id);


}
