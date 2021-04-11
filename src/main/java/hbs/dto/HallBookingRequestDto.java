package hbs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
public class HallBookingRequestDto {

  @NotNull Integer capacity;

  @NotNull
  @JsonFormat(pattern = "yyyy-mm-dd")
  LocalDate bookingDate;

  @NotNull LocalTime startTime;

  @NotNull LocalTime endTime;
}
