package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role, Integer> implements RoleDao {
    public RoleDaoImpl() {
        setClazz(Role.class);
    }
}
