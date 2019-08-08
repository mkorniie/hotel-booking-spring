package ua.mkorniie.model.pojo;

import lombok.*;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * Simple Java Bean object that represents site user
 */

//$2a$10$m4iDyYtrNaq5FKsymaCw/OnrTyM1.ydJU3j/J6xmAZoDoc.I/LEhC
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
//@AllBasicFalse
public class User implements Serializable {
    private static final Logger logger = Logger.getLogger(Room.class);

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter                             private Long           id;

    @Column(unique = true, columnDefinition="NVARCHAR(255)")
    @Getter         @Basic(optional = false)    private String         name;

    @Enumerated(EnumType.STRING)
    @Getter         @Basic(optional = false)    private Role           role;
    @Getter         @Basic(optional = false)    private String         passwordEncoded;
    @Getter         @Basic(optional = false)    private String         email;
    @Enumerated(EnumType.ORDINAL)
    @Getter         @Basic(optional = false)    private Language       language;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @Getter @Setter                             private Set<Request> requests;

    public User(String name, Role role, String passwordEncoded, String email, Language language) {
        setName(name);
        setRole(role);
        setPasswordEncoded(passwordEncoded);
        setEmail(email);
        setLanguage(language);

        logger.info("Object User successfully created");
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        } else {
            logger.error("Error: 'name' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setPasswordEncoded(String passwordEncoded) {
        if (passwordEncoded != null) {
            this.passwordEncoded = passwordEncoded;
        } else {
            logger.error("Error: 'passwordEncoded' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        } else {
            logger.error("Error: 'email' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setRole(Role role) {
        if (role != null) {
            this.role = role;
        } else {
            logger.error("Error: 'role' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setLanguage(Language language) {
        if (language != null) {
            this.language = language;
        } else {
            logger.error("Error: 'language' object can not be null");
            throw new NullPointerException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                role == user.role &&
                passwordEncoded.equals(user.passwordEncoded) &&
                email.equals(user.email) &&
                language == user.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, passwordEncoded, email, language);
    }
}

