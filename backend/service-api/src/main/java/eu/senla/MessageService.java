package eu.senla;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface MessageService {

    MessageDTO send(MessageDTO messageDTO);

    void deleteById(Integer id);

    MessageDTO update(MessageDTO messageDto);

    List<MessageDTO> findAllReceivedMessagesByCurrentUser(PaginationDTO paginationDTO);

    List<MessageDTO> findAllSentMessagesByCurrentUser(PaginationDTO paginationDTO);

    MessageDTO findOne(int id);

    List<MessageDTO> getMessagesByWord(String word, PaginationDTO paginationDTO);

    List<MessageDTO> getMessagesBySender(Integer senderId,PaginationDTO paginationDTO);


}
