package hbs.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class HallInfoResponseDto {

  UUID hallId;
  String hallName;
}
