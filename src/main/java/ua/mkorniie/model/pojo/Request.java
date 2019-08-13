package ua.mkorniie.model.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Simple Java Bean object that represents a request made by {@link User}
 * and contains the User, number of places in the {@link Room},
 * class of the {@link Room}, start and end dates {@link Date} and
 * a boolean identifying if the request has been approved byAdmin.
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long                 id;

    @ManyToOne private User                user;

    @Setter @Basic(optional = false) private int                 places;

    @Enumerated(EnumType.ORDINAL)
    @Basic(optional = false) private RoomClass           roomClass;

    @Column(columnDefinition="VARCHAR(30)")
    @Basic(optional = false) private String                startDate;
    @Column(columnDefinition="VARCHAR(30)")
    @Basic(optional = false) private String                endDate;

    @Enumerated(EnumType.ORDINAL)
    @Basic(optional = false) private Status status;


    public Request(@NonNull User user, int places, @NonNull RoomClass roomClass, @NonNull String startDate,
                   @NonNull String endDate, @NonNull Status status) {
        this.user = user;
        this.places = places;
        this.roomClass = roomClass;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;

        log.info("Object Request successfully created");
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    public void setRoomClass(@NonNull RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public void setStartDate(@NonNull String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(@NonNull String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id.equals(request.id) &&
                places == request.places &&
                status == request.status &&
                user.equals(request.user) &&
                startDate.equals(request.startDate) &&
                endDate.equals(request.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, places, startDate, endDate, status);
    }
}
