package eu.senla;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.REMOVE)
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
