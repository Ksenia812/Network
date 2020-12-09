package eu.senla;

import eu.senla.dto.CredentialsDTO;
import eu.senla.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    //TODO move to UserService login
    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody @Valid CredentialsDTO credentialsDto) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentialsDto.getLogin(),
                            credentialsDto.getPassword()));
            String token = jwtProvider.generateToken(authentication);
            response.put("login", credentialsDto.getLogin());
            response.put("token", token);
            return response;
        } catch (AuthenticationException authenticationException) {
            response.put("Error", "Bad credentials");
            return response;
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
