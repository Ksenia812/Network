package eu.senla.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends GenericFilterBean {

    private JwtProvider jwtProvider;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Optional<String> token = jwtProvider.getJwtFromRequest((HttpServletRequest) request);
            if (token.isPresent() && jwtProvider.validateToken(token.get())
                    && jwtProvider.getUserFromToken(token.get()).getActiveStatus()) {
                Authentication authentication = jwtProvider.getAuthentication(token.get());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw new JwtAuthenticationException();
        }
        chain.doFilter(request, response);
    }
}
