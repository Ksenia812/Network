package eu.senla;

import eu.senla.dto.PaginationDTO;
import eu.senla.dto.PostDTO;
import eu.senla.dto.PostInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/info/{id}")
    public List<PostDTO> getPostsById(@PathVariable("id") Integer id,@RequestBody PaginationDTO paginationDTO) {
        return postService.findAllById(id,paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public List<PostDTO> getPostsByWord(@RequestParam("word") String word, @RequestBody PaginationDTO paginationDTO) {
        return postService.findAllByWord(word, paginationDTO);
    }

   /* @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/post/sort")
    public List<PostDTO> getSortedPosts(@RequestBody PaginationDTO paginationDTO) {
        return postService.sortAndPagination(paginationDTO);
    }*/

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable("id") Integer id) {
        return postService.findOne(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public PostDTO updatePostInfo(@PathVariable Integer id, @RequestBody @Valid PostDTO postDTO) {
        return postService.update(id, postDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postService.deleteById(id);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/post/{id}")
    public PostInfoDTO sendMessage(@PathVariable(required = false) Integer id, @RequestBody @Valid PostDTO postDTO) {
        return postService.add(id, postDTO);
    }


}
