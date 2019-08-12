package ua.mkorniie.model.pojo;

import lombok.*;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.exceptions.NotEnoughDataException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Simple Java Bean object that represents site user
 */

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @Getter @Setter                             private Set<Request> requests;

    public User(@NotNull String name, @NotNull Role role, @NotNull String passwordEncoded, @NotNull String email, @NotNull Language language) {
        setName(name);
        setRole(role);
        setPasswordEncoded(passwordEncoded);
        setEmail(email);
        setLanguage(language);

        logger.info("Object User successfully created");
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setPasswordEncoded(@NotNull String passwordEncoded) {
        this.passwordEncoded = passwordEncoded;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public void setLanguage(@NotNull Language language) {
        this.language = language;
    }

    public static class Builder {
        private User u;

        /***
         * The Builder class, standard architecture pattern, allows to create new object of User class.
         * All parameters should be present (except Id); otherwise, throws NotEnoughDataException.
         */
        public Builder() {
            u = new User();
        }

        public Builder withId(@NotNull Long id) {
            u.id = id;
            return this;
        }

        public Builder withName(@NotNull String name) {
            u.name = name;
            return this;
        }

        public Builder withRole(@NotNull Role role) {
            u.role = role;

            return this;
        }

        public Builder withPasswordEncoded(@NotNull String pass) {
            u.passwordEncoded = pass;

            return this;
        }

        public Builder withEmail(@NotNull String email) {
            u.email = email;

            return this;
        }

        public Builder withLanguage(@NotNull Language language) {
            u.language = language;

            return this;
        }

        public User build() throws NotEnoughDataException {
            if (u.name == null || u.role == null ||
            u.passwordEncoded == null || u.email == null || u.language == null) {
                throw new NotEnoughDataException("Not enough data to build object User : " + u);
            }
            return u;
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

