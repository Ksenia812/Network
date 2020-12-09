package eu.senla;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class MessageKey implements Serializable {
    //@Column(name = "receiver_id")
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiverId;
    // @Column(name = "sender_id")
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User senderId;

    MessageKey() {
    }

    public MessageKey(User receiverId, User senderId) {
        this.receiverId = receiverId;
        this.senderId = senderId;
    }
}
