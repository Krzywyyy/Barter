package pl.krzywyyy.barter.users.roles;

import lombok.Data;
import pl.krzywyyy.barter.users.User;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
