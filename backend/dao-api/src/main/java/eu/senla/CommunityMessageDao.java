package eu.senla;

import eu.senla.dto.PaginationDTO;

import java.util.List;

public interface CommunityMessageDao extends GenericDao<CommunityMessage, Integer> {
    List<CommunityMessage> getAllCommunityMessageByUser(Integer userId, PaginationDTO paginationDTO);

}
