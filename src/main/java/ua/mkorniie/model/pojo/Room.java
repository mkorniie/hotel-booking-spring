package ua.mkorniie.model.pojo;


import lombok.*;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.util.Rounder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Simple Java Bean object representing a room in a hotel that can be
 * booked by a {@link User} upon his/her {@link Request}
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    private static final Logger logger = Logger.getLogger(Room.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter private Long                id;

    @Getter @Setter @Basic(optional = false) private int                 places;

    @Enumerated(EnumType.ORDINAL)
    @Getter         @Basic(optional = false) private RoomClass           roomClass;

    @Getter         @Basic(optional = false) private String              picURL;

    @Getter         @Basic(optional = false) private double              price;


    @OneToMany (mappedBy = "room")
    @Getter @Setter                          private List<Bill>          billList = new ArrayList<>();

    public Room(int places, RoomClass roomClass, String picURL, double price) {
        this.places = places;
        setRoomClass(roomClass);
        setPicURL(picURL);
        setPrice(price);

        logger.info("Object Room successfully created");
    }

    public void setPrice(double price) {
        this.price = Rounder.round(price);
        logger.info("Double value of 'price' field set succesfully: " + this.price);
    }

    public void setRoomClass(RoomClass roomClass) {
        if (roomClass != null) {
            this.roomClass = roomClass;
        } else {
            logger.error("Error: 'roomClass' object can not be null");
            throw new NullPointerException();
        }
    }

    public void setPicURL(String picURL) {
        if (picURL != null) {
            this.picURL = picURL;
        } else {
            logger.error("Error: 'picURL' object can not be null");
            throw new NullPointerException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                places == room.places &&
                Double.compare(room.price, price) == 0 &&
                roomClass == room.roomClass &&
                picURL.equals(room.picURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, places, roomClass, picURL, price);
    }
}
