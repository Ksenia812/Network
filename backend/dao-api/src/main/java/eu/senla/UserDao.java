package eu.senla;

import eu.senla.dto.MessageDTO;
import eu.senla.dto.PaginationDTO;

import java.util.List;

public interface UserDao extends GenericDao<User, Integer> {
    User findByLogin(String login);

    User findByEmail(String email);



}
