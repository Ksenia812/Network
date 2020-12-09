package eu.senla.security;

import eu.senla.User;
import eu.senla.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    private UserDetailsService userDetailsService;
    private UserDao userDao;

    @Autowired
    public JwtProvider(UserDetailsService userDetailsService, UserDao userDao) {
        this.userDetailsService = userDetailsService;
        this.userDao = userDao;
    }

    public static final long _5HOURS_TOKEN_VALIDITY = 18000000;
    public static final String BEARER = "Bearer ";


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public User getUserFromToken(String token) {
        String login = getClaimFromToken(token, Claims::getSubject);
        return userDao.findByLogin(login);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {
        final UserDetails user = (UserDetails) authentication.getPrincipal();
        return generateToken(secret, user.getUsername());
    }

    private String generateToken(String secret, String subject) {
        final long now = System.currentTimeMillis();
        final long expirationTime = now + _5HOURS_TOKEN_VALIDITY;
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret);
        return builder.compact();
    }

    public Boolean validateToken(String token) {
        final String username = getUsernameFromToken(token);
        String subject = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return username != null && !isTokenExpired(token);
    }

    public Optional<String> getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
