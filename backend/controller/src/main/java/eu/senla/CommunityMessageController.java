package eu.senla;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
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

import java.util.List;
@RestController
@RequestMapping("/community/message")
@AllArgsConstructor
public class CommunityMessageController {

    private CommunityMessageService communityMessageService;

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/community/messages")
    public List<MessageDTO> findAllCommunityMessagesByUser(@RequestBody PaginationDTO paginationDTO) {
        return communityMessageService.findAllMessagesByUser(paginationDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/community/message/{id}")
    public MessageDTO findCommunityMessageById(@PathVariable("id") Integer id) {
        return communityMessageService.findOne(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    @PostMapping("/community/message/{id}")
    public MessageDTO sendCommunityMessage(@PathVariable("id") Integer id, @RequestBody MessageDTO messageDTO) {
        return communityMessageService.add(id, messageDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MODERATOR')")
    @PutMapping("/community/message")
    public MessageDTO updateCommunityMessageDTO(@RequestBody MessageDTO messageDTO) {
        return communityMessageService.update(messageDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping("/community/message/{id}")
    public void deleteCommunityMessage(@PathVariable("id") Integer id) {
        communityMessageService.deleteById(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @GetMapping("/community/message")
    public List<MessageDTO> findAllMessagesByWord(@RequestParam("word") String word, @RequestBody PaginationDTO paginationDTO) {
        return communityMessageService.getMessagesByWord(word, paginationDTO);
    }
}
