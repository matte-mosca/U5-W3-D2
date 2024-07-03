package matteomoscardini.u5w3d2.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "employees")
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    private String username;
    private String name;
    private String surname;
    private String eMail;
    private String avatarUrl;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany
    private List<Device> devicesList = new ArrayList<>();

    public Employee(String username, String name, String surname, String eMail, String avatarUrl, List<Device> devicesList, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.eMail = eMail;
        this.avatarUrl = avatarUrl;
        this.devicesList = devicesList;
        this.password = password;
        this.role = Role.USER;

    }

    public Employee(String eMail, String password) {
        this.password = password;
        this.eMail = getEMail();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
