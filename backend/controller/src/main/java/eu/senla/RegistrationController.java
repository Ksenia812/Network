package eu.senla;

import eu.senla.dto.UserInfoDTO;
import eu.senla.dto.UserRegistrationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private UserService userService;

    @PostMapping
    public UserInfoDTO registeredUser(@RequestBody UserRegistrationDTO userRegistrationDto) {
        return userService.add(userRegistrationDto);
    }
}
