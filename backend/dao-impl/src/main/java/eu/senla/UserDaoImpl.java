package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User, Integer> implements UserDao {
    public UserDaoImpl() {
        setClazz(User.class);
    }

    @Override
    public User findByLogin(String login) {
        return findByParameter(Credentials_.LOGIN, User_.credentials, login);
    }

    @Override
    public User findByEmail(String email) {
        return findByParameter(User_.EMAIL, null, email);
    }


}
