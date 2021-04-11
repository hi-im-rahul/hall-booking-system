package hbs.web;

import hbs.business.HallBookingService;
import hbs.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/hall")
public class HallBookingController {

  private final HallBookingService hallBookingService;

  public HallBookingController(final HallBookingService hallBookingService) {
    this.hallBookingService = hallBookingService;
  }

  @GetMapping("/availability/check")
  public HallInfoResponseDto checkAvailability(
      @RequestBody final HallBookingRequestDto hallBookingRequestDto) {
    return hallBookingService.checkHallAvailability(hallBookingRequestDto);
  }

  @PostMapping("/{hall-id}/book")
  public BookingInfoResponseDto bookHall(
      @PathVariable(name = "hall-id") final UUID hallId,
      @RequestBody final HallBookingRequestDto hallBookingRequestDto) {
    return hallBookingService.bookHall(hallId, hallBookingRequestDto);
  }

  @GetMapping("/bookings/fetch")
  public List<BookingInfoResponseDto> getBookings(
      @RequestBody final BookingInfoRequestDto bookingInfoRequestDto) {
    return hallBookingService.getBookings(bookingInfoRequestDto);
  }
}
