package eu.senla;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface CommunityMessageService {
    MessageDTO add(Integer communityId, MessageDTO messageDto);

    void deleteById(Integer id);

    MessageDTO update(MessageDTO messageDto);

    List<MessageDTO> findAllMessagesByUser(PaginationDTO paginationDTO);

    MessageDTO findOne(int id);

    List<MessageDTO> getMessagesByWord(String word, PaginationDTO paginationDTO);

   }
