package eu.senla;

import eu.senla.converter.Mapper;
import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;
import eu.senla.exception.EntityNotFoundException;
import eu.senla.exception.SortParameterNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class CommunityMessageServiceImpl implements CommunityMessageService {
    private Mapper mapper;
    private CommunityMessageDao communityMessageDao;
    private UserDao userDao;
    private CommunityDao communityDao;
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public MessageDTO add(Integer communityId, MessageDTO messageDto) {
        CommunityMessage message = (CommunityMessage) mapper.convertToEntity(new CommunityMessage(), messageDto);
        Community community = communityDao.findOne(communityId);
        log.info("Attempt to add {} community message", communityId);
        if (community == null) {
            throw new EntityNotFoundException(Community.class, Community_.ID, communityId);
        }
        message.setCommunity(community);
        User user = userDao.findByLogin(userDetailsService.getCurrentLogin());
        if (user == null) {
            throw new EntityNotFoundException(User.class, User_.ID, messageDto.getUserId());
        }
        message.setUser(user);
        message.setDate(new Date());
        CommunityMessage savedMessage = communityMessageDao.save(message);
        MessageDTO messageDTO = (MessageDTO) mapper.convertToDto(savedMessage, new MessageDTO());
        log.info("Community message was added");
        return messageDTO;
    }

    @Override
    public void deleteById(Integer id) {
        communityMessageDao.deleteById(id);
        log.info("Community message was deleted");
    }

    @Override
    public MessageDTO update(MessageDTO messageDTO) {
        log.info("Attempt to update {} community message", messageDTO.getId());
        CommunityMessage message = communityMessageDao.findOne(messageDTO.getId());
        message.setDate(new Date());
        message.setText(messageDTO.getText());
        communityMessageDao.update(message);
        MessageDTO updatedMessage = (MessageDTO) mapper.convertToDto(message, new MessageDTO());
        log.info("Community message {} was updated", updatedMessage.getId());
        return updatedMessage;

    }

    @Override
    public List<MessageDTO> findAllMessagesByUser(PaginationDTO paginationDTO) {
        String currentLogin = userDetailsService.getCurrentLogin();
        User currentUser = userDao.findByLogin(currentLogin);
        log.info("Attempt to find all messages by user with login {}", currentLogin);
        List<CommunityMessage> messages = communityMessageDao.getAllCommunityMessageByUser(currentUser.getId(),paginationDTO);
        log.info("Return all messages by user");
        return messages.stream()
                .map(message -> (MessageDTO) mapper.convertToDto(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageDTO findOne(int id) {
        log.info("Attempt to find community message by id {}", id);

        CommunityMessage message = communityMessageDao.findOne(id);
        if (message == null) {
            throw new EntityNotFoundException(CommunityMessage.class, CommunityMessage_.ID, id);
        }
        log.info("Return community with id {}", id);
        return (MessageDTO) mapper.convertToDto(message, new MessageDTO());
    }

    public List<MessageDTO> getMessagesByWord(String word, PaginationDTO paginationDTO) {
        log.info("Attempt to get messages by word {}", word);
        return communityMessageDao.findEntryByParameter(Message_.TEXT, word, paginationDTO)
                .stream()
                .map(message -> (MessageDTO) mapper.convertToDto(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

}
