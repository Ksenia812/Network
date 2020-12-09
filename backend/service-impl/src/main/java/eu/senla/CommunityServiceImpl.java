package eu.senla;

import eu.senla.converter.Mapper;
import eu.senla.dto.CommunityDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;
import eu.senla.exception.EntityNotFoundException;
import eu.senla.exception.EntityNotUniqueException;
import eu.senla.exception.SortParameterNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private CommunityDao communityDao;
    private Mapper mapper;
    private UserDao userDao;
    private UserDetailsServiceImpl userDetailsService;
    private RoleDao roleDao;
    private static final Integer MODERATOR_ROLE_ID = 2;


    @Override
    public void deleteById(int id) {
        log.info("Attempt to delete community with id {}", id);
        communityDao.deleteById(id);
        log.info("Community with id {} was deleted", id);
    }

    @Override
    public CommunityDTO update(CommunityDTO communityDTO) {
        log.info("Attempt to update community with id {}", communityDTO.getId());
        Community community = communityDao.findOne(communityDTO.getId());
        community.setName(communityDTO.getName());
        community.setTopic(communityDTO.getTopic());
        log.info("Community with id {} was updated", community.getId());
        return (CommunityDTO) mapper.convertToDto(community, new CommunityDTO());

    }

    @Override
    public CommunityDTO createCommunity(CommunityDTO communityDTO) {
        log.info("Attempt to create community with id {}", communityDTO.getId());
        log.info("Check whether the community with such name {} exists", communityDTO.getName());
        Community existedCommunity = communityDao.findByName(communityDTO.getName());
        if (existedCommunity != null) {
            throw new EntityNotUniqueException(Community.class, Community_.NAME);
        }
        Community community = (Community) mapper.convertToEntity(new Community(), communityDTO);
        User user = userDao.findByLogin(userDetailsService.getCurrentLogin());
        Role moderator = roleDao.findOne(MODERATOR_ROLE_ID);
        user.getRoles().add(moderator);
        community.setModerator(user);
        community.getUsers().add(user);
        Community savedCommunity = communityDao.save(community);
        user.getCommunities().add(savedCommunity);
        userDao.update(user);
        log.info("Return the created community with name {}", community.getName());
        return (CommunityDTO) mapper.convertToDto(savedCommunity, new CommunityDTO());
    }

    @Override
    public List<CommunityDTO> findAll(PaginationDTO paginationDTO) {
        log.info("Attempt to get all communities");
        return communityDao.findAll(paginationDTO)
                .stream()
                .map(community -> (CommunityDTO) mapper.convertToDto(community, new CommunityDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public CommunityDTO findOne(int id) {
        log.info("Attempt to find community by id {}", id);
        Community community = communityDao.findOne(id);
        if (community == null) {
            throw new EntityNotFoundException(Community.class, Community_.ID, id);
        }
        CommunityDTO communityDTO = (CommunityDTO) mapper.convertToDto(community, new CommunityDTO());
        log.info("Return the searched community with id {}", id);
        return communityDTO;

    }

    @Override
    public CommunityDTO findCommunityByName(String name) {
        log.info("Attempt to find community by name {}", name);
        Community community = communityDao.findByName(name);
        if(community==null){
            throw new EntityNotFoundException(Community.class,Community_.NAME,name);
        }
        CommunityDTO communityDTO = (CommunityDTO) mapper.convertToDto(community, new CommunityDTO());
        log.info("Return the searched community with name {}",name);
        return communityDTO;
    }

    @Override
    public List<CommunityDTO> findCommunityByTopic(String topic, PaginationDTO paginationDTO) {
        log.info("Attempt to find community by topic{}",topic);
        List<Community> communities = communityDao.findListByParameter(Community_.TOPIC, null, topic,paginationDTO);
        log.info("Return the searched communities");
        return communities.stream()
                .map(community -> (CommunityDTO) mapper.convertToDto(community, new CommunityDTO()))
                .collect(Collectors.toList());

    }



}
