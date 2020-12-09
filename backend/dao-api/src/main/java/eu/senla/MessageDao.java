package eu.senla;

import eu.senla.dto.PaginationDTO;

import java.util.List;

public interface MessageDao extends GenericDao<Message,Integer> {

    List<Message> getAllReceivedMessagesByCurrentUser(PaginationDTO paginationDTO, Integer id);

    List<Message> getAllSentMessagesByCurrentUser(PaginationDTO paginationDTO, Integer id);


}
