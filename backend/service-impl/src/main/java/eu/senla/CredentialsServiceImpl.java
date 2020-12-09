package eu.senla;

import eu.senla.converter.Mapper;
import eu.senla.dto.CredentialsDTO;
import eu.senla.exception.EntityNotUniqueException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static eu.senla.Credentials_.LOGIN;

@Log4j2
@AllArgsConstructor
@Service
public class CredentialsServiceImpl implements CredentialsService {
    private Mapper mapper;
    private UserDao userDao;
    private CredentialsDao credentialsDao;

    @Override
    public void update(CredentialsDTO credentialsDto) {
        log.info("Attempt to update credentials with id {}", credentialsDto.getId());
        log.info("Check whether the user with such login exists ");
        User existedUser = userDao.findByLogin(credentialsDto.getLogin());
        if (existedUser != null) {
            throw new EntityNotUniqueException(User.class, LOGIN);
        }
        Credentials credentials = (Credentials) mapper.convertToEntity(new Credentials(), credentialsDto);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        credentials.setPassword(bCryptPasswordEncoder.encode(credentialsDto.getPassword()));
        credentials.setLogin(credentialsDto.getLogin());
        credentialsDao.update(credentials);
        log.info("Credentials were updated");
    }
}
