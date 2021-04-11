//package hbs.common;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class RestExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Object> handleRuntimeException(final RuntimeException ex) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//          .body(ExceptionResponse.builder().message(ex.getMessage()).build());
//    }
//
//  @Override
//  protected ResponseEntity handleExceptionInternal(
//      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
//      request.setAttribute("javax.servlet.error.exception", ex, 0);
//    }
//
//    return new ResponseEntity(
//        ExceptionResponse.builder().message(ex.getMessage()).build(), headers, status);
//  }
//}
