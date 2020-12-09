package eu.senla.security;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException() {
        log.error("Jwt token is expired or invalid");
    }
}
