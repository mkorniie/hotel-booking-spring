package ua.mkorniie.model.pojo;

import lombok.*;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Simple Java Bean object that represents a request made by {@link User}
 * and contains the User, number of places in the {@link Room},
 * class of the {@link Room}, start and end dates {@link Date} and
 * a boolean identifying if the request has been approved byAdmin.
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    private static final Logger logger = Logger.getLogger(Request.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long                 id;

    @ManyToOne
    @Getter         private User                user;

    @Getter @Setter  @Basic(optional = false) private int                 places;

    @Enumerated(EnumType.ORDINAL)
    @Getter          @Basic(optional = false) private RoomClass           roomClass;

    @Column(columnDefinition="VARCHAR(50)")
    @Getter          @Basic(optional = false) private String                startDate;
    @Column(columnDefinition="VARCHAR(50)")
    @Getter          @Basic(optional = false) private String                endDate;
    @Getter @Setter  @Basic(optional = false) private boolean             isApproved;

    public Request(User user, int places, RoomClass roomClass, String startDate, String endDate, boolean isApproved) {
        setUser(user);
        this.places = places;
        setRoomClass(roomClass);
        setStartDate(startDate);
        setEndDate(endDate);
        this.isApproved = isApproved;

        logger.info("Object Request successfully created");
    }

    public void setUser(User user) {
        if (user != null) {
            this.user = user;
        } else {
            logger.error("Error: 'user' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setRoomClass(RoomClass roomClass) {
        if (roomClass != null) {
            this.roomClass = roomClass;
        } else {
            logger.error("Error: 'roomClass' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setStartDate(String startDate) {
        if (startDate != null) {
            this.startDate = startDate;
        } else {
            logger.error("Error: 'startDate' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setEndDate(String endDate) {
        if (endDate != null) {
            this.endDate = endDate;
        } else {
            logger.error("Error: 'endDate' object can not be null");
            throw new NullPointerException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                places == request.places &&
                isApproved == request.isApproved &&
                user.equals(request.user) &&
                startDate.equals(request.startDate) &&
                endDate.equals(request.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, places, startDate, endDate, isApproved);
    }
}
