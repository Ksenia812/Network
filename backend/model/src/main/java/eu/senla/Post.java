package eu.senla;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "post")
@EqualsAndHashCode(exclude = "user")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String header;
    @Column
    private String text;
    @Column
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wall_id", nullable = false)
    private Wall wall;
    //    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
