package eu.senla;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;
import eu.senla.dto.PostDTO;
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
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {
    private MessageService messageService;
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.send(messageDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/received")
    public List<MessageDTO> getAllReceivedMessagesByUser(@RequestBody PaginationDTO paginationDTO) {
        return messageService.findAllReceivedMessagesByCurrentUser(paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/sent")
    public List<MessageDTO> getAllSentMessagesByUser(@RequestBody PaginationDTO paginationDTO) {
        return messageService.findAllSentMessagesByCurrentUser(paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public List<MessageDTO> getAllReceivedMessagesBySender(@PathVariable("id") Integer senderId, @RequestBody PaginationDTO paginationDTO) {
        return messageService.getMessagesBySender(senderId, paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/message/{id}")
    public MessageDTO getMessageById(@PathVariable("id") Integer messageId) {
        return messageService.findOne(messageId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/word")
    public List<MessageDTO> getMessageByWord(@RequestParam("word") String word, @RequestBody PaginationDTO paginationDTO) {
        return messageService.getMessagesByWord(word, paginationDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable("id") Integer messageId) {
        messageService.deleteById(messageId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/update")
    public MessageDTO updateMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.update(messageDTO);
    }

}
