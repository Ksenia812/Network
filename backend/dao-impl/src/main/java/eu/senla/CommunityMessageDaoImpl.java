package eu.senla;

import eu.senla.dto.PaginationDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommunityMessageDaoImpl extends AbstractDao<CommunityMessage, Integer> implements CommunityMessageDao {
    public CommunityMessageDaoImpl() {
        setClazz(CommunityMessage.class);
    }

    @Override
    public List<CommunityMessage> getAllCommunityMessageByUser(Integer userId, PaginationDTO paginationDTO) {
        return findListByParameter(CommunityMessage_.USER, null, userId, paginationDTO);
    }
}
