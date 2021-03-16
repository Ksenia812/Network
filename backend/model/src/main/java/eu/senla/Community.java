package eu.senla;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "community")
@EqualsAndHashCode(exclude = {"users", "moderator"})
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String topic;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "communities")
    private Set<User> users = new HashSet<>();
    @OneToOne
    private User moderator;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "community")
    private List<CommunityMessage> communityMessages = new ArrayList<>();
}
