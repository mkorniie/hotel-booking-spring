package ua.mkorniie.model.pojo;

import lombok.*;
import org.slf4j.Logger;
import ua.mkorniie.model.util.Rounder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Simple Java Bean object that represents a bill that a {@link User}
 * will pay if his/her {@link Request} for a {@link Room} has been approved
 * by Admin.
 * If the {@link User} has paid the bill, the boolean 'isPaid' would be set to 'TRUE'.
 */

//TODO: apply builder? at least at servlet?

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill implements Serializable {
	private static final Logger logger = getLogger(Bill.class);

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Getter @Setter private Long             	id;

	@Getter 		private double				sum;
	@Getter @Setter private boolean             isPaid;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "request_id", referencedColumnName = "id")
	@Getter 		private Request				request;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	@Getter 		 private Room				room;

	public Bill(double sum, boolean isPaid, Request request, Room room) {
		setSum(sum);
		this.isPaid = isPaid;
		setRequest(request);
		setRoom(room);

		logger.info("Object Bill successfully created");
	}

	public void setSum(double sum) {
			this.sum = Rounder.round(sum);
			logger.info("Double value of 'sum' field set succesfully: " + this.sum);
	}

	public void setRequest(Request request) {
		if (request != null) {
			this.request = request;
		} else {
			logger.error("Error: 'request' object can not be null");
			throw new NullPointerException();
		}
	}

	public void setRoom(Room room) {
		if (room != null) {
			this.room = room;
		} else {
			logger.error("Error: 'room' object can not be null");
			throw new NullPointerException();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bill bill = (Bill) o;
		return id == bill.id &&
				Double.compare(bill.sum, sum) == 0 &&
				isPaid == bill.isPaid &&
				request.equals(bill.request) &&
				room.equals(bill.room);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, sum, isPaid, request, room);
	}
}