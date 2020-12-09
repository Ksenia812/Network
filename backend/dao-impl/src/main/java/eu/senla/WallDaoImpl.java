package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class WallDaoImpl extends AbstractDao<Wall, Integer> implements WallDao {
    public WallDaoImpl() {
        setClazz(Wall.class);
    }
}
