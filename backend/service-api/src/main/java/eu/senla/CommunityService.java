package eu.senla;

import eu.senla.dto.CommunityDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.SortDto;

import java.util.List;

public interface CommunityService {

    void deleteById(int id);

    CommunityDTO update(CommunityDTO communityDto);

    CommunityDTO createCommunity(CommunityDTO communityDTO);

    List<CommunityDTO> findAll(PaginationDTO paginationDTO);

    CommunityDTO findOne(int id);

    CommunityDTO findCommunityByName(String name);

    List<CommunityDTO> findCommunityByTopic(String topic,PaginationDTO paginationDTO);


}
