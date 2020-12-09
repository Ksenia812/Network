package eu.senla;

import eu.senla.dto.PaginationDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDaoImpl extends AbstractDao<Message, Integer> implements MessageDao {
    public MessageDaoImpl() {
        setClazz(Message.class);
    }
    @Override
    public List<Message> getAllReceivedMessagesByCurrentUser(PaginationDTO paginationDTO, Integer id) {
        return findListByParameter(Message_.RECEIVER,null,id,paginationDTO);
    }

    @Override
    public List<Message> getAllSentMessagesByCurrentUser(PaginationDTO paginationDTO, Integer id) {
        return findListByParameter(Message_.SENDER,null,id,paginationDTO);
    }


}
