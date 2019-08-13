package ua.mkorniie.model.pojo;


import lombok.*;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.util.Rounder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Getter         private Long                id;

    @Getter @Setter @Basic(optional = false) private int                 places;

    @Enumerated(EnumType.ORDINAL)
    @Getter         @Basic(optional = false) private RoomClass           roomClass;

    @Getter         @Basic(optional = false) private String              picURL;

    @Getter         @Basic(optional = false) private double              price;


    @OneToMany (mappedBy = "room")
    @Getter @Setter                          private List<Bill>          billList = new ArrayList<>();

    public Room(@NotNull int places, @NotNull RoomClass roomClass, @NotNull String picURL, double price) {
        this.places = places;
        this.roomClass = roomClass;
        this.picURL = picURL;
        setPrice(price);

        logger.info("Object Room successfully created");
    }

    public void setPrice(double price) {
        this.price = Rounder.round(price);
        logger.info("Double value of 'price' field set succesfully: " + this.price);
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public void setRoomClass(@NotNull RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public void setPicURL(@NotNull String picURL) {
        this.picURL = picURL;
    }

    public void setBillList(@NotNull List<Bill> billList) {
        this.billList = billList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id) &&
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
