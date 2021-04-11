package hbs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface HallBookingRepository extends JpaRepository<HallBookingEntity, UUID> {

  List<HallBookingEntity> findAllByHallEntity_UuidAndBookingDate(
      final UUID hallId, final LocalDate date);
}
