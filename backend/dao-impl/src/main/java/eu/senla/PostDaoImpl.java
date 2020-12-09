package eu.senla;

import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl extends AbstractDao<Post, Integer> implements PostDao {
    public PostDaoImpl() {
        setClazz(Post.class);
    }
}
