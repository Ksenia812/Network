package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class CredentialsDaoImpl extends AbstractDao<Credentials, Integer> implements CredentialsDao {
    public CredentialsDaoImpl() {
        setClazz(Credentials.class);
    }
}
