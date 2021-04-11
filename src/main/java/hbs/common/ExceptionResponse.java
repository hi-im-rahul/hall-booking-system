package hbs.common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExceptionResponse {

  String message;
}
