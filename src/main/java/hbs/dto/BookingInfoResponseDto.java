package hbs.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Value
@Builder
public class BookingInfoResponseDto {

  UUID bookingId;
  LocalDateTime bookedAt;
  LocalDate bookingDate;
  LocalTime startTime;
  LocalTime endTime;
  HallInfoResponseDto hallInfo;
}
