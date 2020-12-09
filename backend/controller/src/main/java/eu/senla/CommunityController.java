package eu.senla;

import eu.senla.dto.CommunityDTO;
import eu.senla.dto.PaginationDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/community")
@AllArgsConstructor
public class CommunityController {
    private CommunityService communityService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/create")
    public CommunityDTO createCommunity(@RequestBody CommunityDTO communityDTO) {
        return communityService.createCommunity(communityDTO);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/name")
    public CommunityDTO findCommunityByName(@RequestParam("name") String name) {
        return communityService.findCommunityByName(name);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public CommunityDTO findCommunityById(@PathVariable("id") Integer id) {
        return communityService.findOne(id);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public List<CommunityDTO> findCommunities(@RequestBody PaginationDTO paginationDTO) {
        return communityService.findAll(paginationDTO);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/topic")
    public List<CommunityDTO> findCommunityByTopic(@RequestParam("topic") String topic, @RequestBody PaginationDTO paginationDTO) {
        return communityService.findCommunityByTopic(topic, paginationDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteCommunity(@PathVariable("id") Integer id) {
        communityService.deleteById(id);
    }
}
