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
public class MessageServiceImpl implements MessageService {

    private MessageDao messageDao;
    private Mapper mapper;
    private UserDao userDao;
    private UserDetailsServiceImpl userDetailsService;
    public static final String LOGIN = "login";


    @Override
    public MessageDTO send(MessageDTO messageDTO) {
        log.info("Attempt to  send new message");
        User receiver = userDao.findOne(messageDTO.getUserId());
        User sender = userDao.findByLogin(userDetailsService.getCurrentLogin());
        if (receiver == null) {
            throw new EntityNotFoundException(User.class, User_.ID, messageDTO.getUserId());
        }
        if (sender == null) {
            throw new EntityNotFoundException(User.class, LOGIN, userDetailsService.getCurrentLogin());
        }
        Message message = (Message) mapper.convertToEntity(new Message(), messageDTO);
        message.setDate(new Date());
        message.setReceiver(receiver);
        message.setSender(sender);
        Message savedMessage = messageDao.save(message);
        messageDTO.setId(savedMessage.getId());
        log.info("Return the sent message with id{}", savedMessage.getId());
        return messageDTO;
    }

    @Override
    public void deleteById(Integer id) {
        log.info("Attempt to delete message by id{}", id);
        Message message = messageDao.findOne(id);
        messageDao.delete(message);
        log.info("Message with id {}was deleted", id);
    }

    @Override
    public MessageDTO update(MessageDTO messageDTO) {
        log.info("Attempt to update message with id{}", messageDTO.getId());
        Message message = messageDao.findOne(messageDTO.getId());
        message.setDate(new Date());
        message.setText(messageDTO.getText());
        messageDao.update(message);
        MessageDTO updatedMessage = (MessageDTO) mapper.convertToDto(message, new MessageDTO());
        log.info("Message with id {} was updated ", updatedMessage.getId());
        return updatedMessage;

    }

    @Override
    public List<MessageDTO> findAllReceivedMessagesByCurrentUser(PaginationDTO paginationDTO) {
        log.info("Attempt to get all received messages by user");
        User currentUser = userDao.findByLogin(userDetailsService.getCurrentLogin());
        List<Message> messages = messageDao.getAllReceivedMessagesByCurrentUser(paginationDTO,currentUser.getId());
        log.info("Return all received messages by given user");
        return messages.stream()
                .map(message -> (MessageDTO) mapper.convertToDto(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> findAllSentMessagesByCurrentUser(PaginationDTO paginationDTO) {
        log.info("Attempt to get all sent messages by user");
        User currentUser = userDao.findByLogin(userDetailsService.getCurrentLogin());
        List<Message> messages = messageDao.getAllSentMessagesByCurrentUser(paginationDTO,currentUser.getId());
        log.info("Return all sent messages by given user");
        return messages.stream()
                .map(message -> (MessageDTO) mapper.convertToDto(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public MessageDTO findOne(int id) {
        log.info("Attempt to find message with given id{}", id);
        Message message = messageDao.findOne(id);
        log.info("Return the message with id {}", id);
        return (MessageDTO) mapper.convertToDto(message, new MessageDTO());
    }

    @Override
    public List<MessageDTO> getMessagesByWord(String word, PaginationDTO paginationDTO) {
        log.info("Attempt to get messages with given word {}", word);
        List<Message> messages = messageDao.findEntryByParameter(Message_.TEXT, word, paginationDTO);
        log.info("Return all messages with given word{}", word);
        return messages
                .stream()
                .map(message -> {
                    MessageDTO messageDTO = (MessageDTO) mapper.convertToDto(message, new MessageDTO());
                    messageDTO.setUserId(message.getReceiver().getId());
                    return messageDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getMessagesBySender(Integer senderId, PaginationDTO paginationDTO) {
        log.info("Attempt to get messages by sender");
        List<Message> messages = messageDao.findListByParameter(Message_.SENDER, null, senderId,paginationDTO);
        log.info("Return messages by sender");
        return messages.stream()
                .map(message -> (MessageDTO) mapper.convertToDto(message, new MessageDTO()))
                .collect(Collectors.toList());
    }


}
