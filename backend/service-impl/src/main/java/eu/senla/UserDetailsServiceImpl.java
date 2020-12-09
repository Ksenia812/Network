package eu.senla;

import eu.senla.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
@Log4j2
@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Attempt to load user by login {}",login);
        User user = userDao.findByLogin(login);
        log.info("Get user's roles");
        Set<GrantedAuthority> roles = new HashSet<>();
        user.getRoles().forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName())));
        log.info("Get user's credentials");
        Credentials userCredentials = user.getCredentials();
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(userCredentials.getLogin(),
                        userCredentials.getPassword(),
                        roles);
        log.info("Return user");
        return userDetails;

    }

    public String getCurrentLogin() {
        log.info("Attempt to get current user by login");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName() == null) {
            throw new EntityNotFoundException(User.class, "login", null);
        }
        log.info("Return current login");
        return auth.getName();
    }
}
