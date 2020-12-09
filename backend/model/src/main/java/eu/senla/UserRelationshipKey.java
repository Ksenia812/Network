package eu.senla;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class UserRelationshipKey implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friendId;

    public UserRelationshipKey() {}

    public UserRelationshipKey(User userId, User friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
