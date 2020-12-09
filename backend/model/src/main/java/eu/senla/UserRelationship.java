package eu.senla;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "user_has_friends")
public class UserRelationship {
    @EmbeddedId
    private UserRelationshipKey userRelationshipKey;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false, insertable=false, updatable=false)
    private User userId;
    @ManyToOne
    @JoinColumn(name="friend_id", nullable = false, insertable=false, updatable=false)
    private User friendId;
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

}
