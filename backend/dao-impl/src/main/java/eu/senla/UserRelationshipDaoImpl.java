package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class UserRelationshipDaoImpl extends AbstractDao<UserRelationship, Integer> implements UserRelationshipDao {
    public UserRelationshipDaoImpl() {
        setClazz(UserRelationship.class);
    }
}
