package eu.senla;

import eu.senla.dto.PaginationDTO;
import eu.senla.dto.PostDTO;
import eu.senla.dto.PostInfoDTO;
import eu.senla.dto.SortDto;

import java.util.List;

public interface PostService {
    PostInfoDTO add(Integer wallId,PostDTO postDto);

    void deleteById(int id);

    PostDTO update(Integer id, PostDTO postDTO);

    List<PostDTO> findAllByWord(String word, PaginationDTO paginationDTO);

    List<PostDTO> findAllById(Integer id,PaginationDTO paginationDTO);

    PostDTO findOne(int id);


}
