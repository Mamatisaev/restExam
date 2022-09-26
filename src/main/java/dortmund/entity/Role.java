package dortmund.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "role_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_generator", sequenceName = "role_id_sequence", allocationSize = 1)
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    private List<User> users;


    @Override
    public String getAuthority() {
        return roleName;
    }

}