package hbs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder
public class BookingInfoRequestDto {

  @NotNull
  @JsonFormat(pattern = "yyyy-mm-dd")
  LocalDate startDate;

  @NotNull
  @JsonFormat(pattern = "yyyy-mm-dd")
  LocalDate endDate;
}
