package hbs.data;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "hall_booking")
@Data
public class HallBookingEntity extends BaseEntity implements Comparable<HallBookingEntity> {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "hall_id",
      referencedColumnName = "uuid",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_halls_hall_booking"))
  private HallEntity hallEntity;

  @Column(name = "booking_date")
  private LocalDate bookingDate;

  @Column(name = "start_time")
  private LocalTime startTime;

  @Column(name = "end_time")
  private LocalTime endTime;

  @Override
  public int compareTo(HallBookingEntity o) {
    if (this.bookingDate.isEqual(o.getBookingDate())) return this.startTime.compareTo(o.startTime);
    return this.getBookingDate().compareTo(o.getBookingDate());
  }
}
