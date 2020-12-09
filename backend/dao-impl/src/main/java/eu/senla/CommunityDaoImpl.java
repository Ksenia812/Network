package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class CommunityDaoImpl extends AbstractDao<Community, Integer> implements CommunityDao {
    public CommunityDaoImpl() {
        setClazz(Community.class);
    }


    @Override
    public Community findByName(String name) {
        return findByParameter(Community_.NAME, null, name);
    }
}
