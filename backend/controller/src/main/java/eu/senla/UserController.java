package eu.senla;

import eu.senla.dto.CommunityDTO;
import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.PostDTO;
import eu.senla.dto.PostInfoDTO;
import eu.senla.dto.UserInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
//TODO think about findAll(weather do pagination or not)
    private UserService userService;
    private PostService postService;
    private CommunityService communityService;
    private MessageService messageService;
    private CommunityMessageService communityMessageService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/info/{id}")
    public UserInfoDTO getUserInfo(@PathVariable("id") Integer id) {
        return userService.findOne(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserInfoDTO> getAllUsers(@RequestBody PaginationDTO paginationDTO){
        return userService.findAll(paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public UserInfoDTO updateUserInfo(@RequestBody @Valid UserInfoDTO userInfoDTO) {
        return userService.update(userInfoDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/deactivate/{id}")
    public UserInfoDTO deactivateUser(@PathVariable Integer id) {
        return userService.deactivateUser(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{id}")
    public void blockUser(@PathVariable("id") Integer id) {
        userService.blockUser(id);
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add/friend/{id}")
    public UserInfoDTO addFriend(@PathVariable Integer id) {
        return userService.addFriend(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/friends/accept/{id}")
    public UserInfoDTO acceptFriendRequest(@PathVariable Integer id) {
        return userService.acceptFriendRequest(id);
    }


}
