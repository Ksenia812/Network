package eu.senla;

import eu.senla.converter.Mapper;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.PostDTO;
import eu.senla.dto.PostInfoDTO;
import eu.senla.dto.SortDto;
import eu.senla.exception.SaveEntityException;
import eu.senla.exception.SortParameterNotFoundException;
import eu.senla.exception.UpdateInfoException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private UserDao userDao;
    private PostDao postDao;
    private WallDao wallDao;
    private Mapper mapper;
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public PostInfoDTO add(Integer wallId, PostDTO postDto) {
        try {
            log.info("Attempt to add post");
            User user = userDao.findByLogin(userDetailsService.getCurrentLogin());
            Post post = (Post) mapper.convertToEntity(new Post(), postDto);
            post.setUser(user);
            Wall wall = Optional.ofNullable(wallDao.findOne(wallId)).orElse(user.getWall());
            post.setWall(wall);
            post.setDate(new Date());
            Post savedPost = postDao.save(post);
            PostInfoDTO postInfoDTO = (PostInfoDTO) mapper.convertToDto(savedPost, new PostInfoDTO());
            log.info("Post was added");
            return postInfoDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new SaveEntityException();
    }

    @Override
    public void deleteById(int id) {
        log.info("Attempt to delete post with id {}",id);
        postDao.deleteById(id);
        log.info("Post was deleted");
    }

    @Override
    public PostDTO update(Integer id, PostDTO postDTO) {
        try {
            log.info("Post with id {} was updated",id);
            Post post = postDao.findOne(id);
            post.setHeader(postDTO.getHeader());
            post.setText(postDTO.getText());
            postDao.update(post);
            PostDTO updatedPostDTO = (PostDTO) mapper.convertToDto(post, new PostDTO());
            log.info("Post with id {} was updated",id);
            return updatedPostDTO;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new UpdateInfoException();

    }

    @Override
    public List<PostDTO> findAllByWord(String word, PaginationDTO paginationDTO) {
        log.info("Attempt to find posts by given word {} ",word);
        List<Post> posts = postDao.findEntryByParameter(Message_.TEXT, word, paginationDTO);
        log.info("Return all posts with given word {}",word);
        return posts
                .stream()
                .map(message -> (PostDTO) mapper.convertToDto(message, new PostDTO()))
                .collect(Collectors.toList());
    }


    @Override
    public List<PostDTO> findAllById(Integer id,PaginationDTO paginationDTO) {
        log.info("Attempt to find all posts with given id{}",id);
        List<Post> posts = postDao.findAllById(Post_.WALL, id,paginationDTO);
        log.info("Return all found posts");
        return posts
                .stream()
                .map(post -> {
                    PostDTO postDTO = (PostDTO) mapper.convertToDto(post, new PostDTO());
                    String userName = post.getUser().getName();
                    String surname = post.getUser().getSurname();
                    postDTO.setUserName(userName);
                    postDTO.setUserSurname(surname);
                    return postDTO;
                })
                .collect(Collectors.toList());
    }


    @Override
    public PostDTO findOne(int id) {
        log.info("Attempt to find post by given id{}",id);
        PostDTO postDTO = (PostDTO) mapper.convertToDto(postDao.findOne(id), new PostDTO());
        log.info("Return the post with given id{}",id);
        return postDTO;
    }


}
