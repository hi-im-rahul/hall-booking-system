package hbs.data;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "halls")
@Data
public class HallEntity extends BaseEntity implements Comparable<HallEntity>{

    @Column(name = "hall_name")
    private String hallName;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "hallEntity", cascade = CascadeType.ALL)
    private List<HallBookingEntity> hallBookings = new ArrayList<>();

    @Override
    public int compareTo(HallEntity o) {
        return Integer.compare(this.capacity, o.capacity);
    }
}
