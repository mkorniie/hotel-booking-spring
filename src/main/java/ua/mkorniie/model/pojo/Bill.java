package ua.mkorniie.model.pojo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ua.mkorniie.service.util.Rounder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Simple Java Bean object that represents a bill that a {@link User}
 * will pay if his/her {@link Request} for a {@link Room} has been approved
 * by Admin.
 * If the {@link User} has paid the bill, the boolean 'isPaid' would be set to 'TRUE'.
 */


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Getter 		private Long             	id;

	@Getter 		private double				sum;
	@Getter @Setter private boolean             isPaid;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "request_id", referencedColumnName = "id")
	@Getter 		private Request				request;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "room_id", referencedColumnName = "id")
	@Getter			private Room				room;

	public Bill(double sum, boolean isPaid, @NonNull Request request,
				@NonNull Room room) {
		setSum(sum);
		this.isPaid = isPaid;
		this.request = request;
		this.room = room;

		log.info("Object Bill successfully created");
	}

	public void setSum(double sum) {
			this.sum = Rounder.round(sum);
			log.info("Double value of 'sum' field set succesfully: " + this.sum);
	}

	public void setId(@NonNull Long id) {
		this.id = id;
	}

	public void setRequest(@NonNull Request request) {
		this.request = request;
	}

	public void setRoom(@NonNull Room room) {
		this.room = room;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Bill bill = (Bill) o;
		return id.equals(bill.id) &&
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