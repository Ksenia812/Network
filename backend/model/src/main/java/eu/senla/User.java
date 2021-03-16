package eu.senla;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Date birthday;
    @Column
    private String email;
    @Column(name = "active_status")
    private Boolean activeStatus;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Message> receivedMessages;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userRelationshipKey.userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRelationship> friendRequests = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userRelationshipKey.friendId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRelationship> friends = new HashSet<>();
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Credentials credentials;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Wall wall;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommunityMessage> communityMessages = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "community_has_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id"))
    private Set<Community> communities = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
