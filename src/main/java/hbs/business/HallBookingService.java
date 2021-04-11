package hbs.business;

import hbs.data.HallBookingEntity;
import hbs.data.HallBookingRepository;
import hbs.data.HallEntity;
import hbs.data.HallRepository;
import hbs.dto.BookingInfoRequestDto;
import hbs.dto.BookingInfoResponseDto;
import hbs.dto.HallBookingRequestDto;
import hbs.dto.HallInfoResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HallBookingService {

  private final HallRepository hallRepository;
  private final HallBookingRepository hallBookingRepository;

  public HallBookingService(
      final HallRepository hallRepository, final HallBookingRepository hallBookingRepository) {
    this.hallRepository = hallRepository;
    this.hallBookingRepository = hallBookingRepository;
  }

  public HallInfoResponseDto checkHallAvailability(
      final HallBookingRequestDto hallBookingRequestDto) {
    final HallEntity hallEntity = validateBookingAndGetHall(hallBookingRequestDto);
    return buildHallInfoResponse(hallEntity);
  }

  private HallEntity validateBookingAndGetHall(final HallBookingRequestDto hallBookingRequestDto) {
    validateInputTime(hallBookingRequestDto);
    final List<HallEntity> accomodableHalls =
        hallRepository.findAll().stream()
            .filter(hallEntity -> hallEntity.getCapacity() >= hallBookingRequestDto.getCapacity())
            .collect(Collectors.toList());
    if (accomodableHalls.isEmpty()) {
      throw new RuntimeException("No halls present with desired capacity");
    }
    return accomodableHalls.stream()
        .filter(hall -> !isSlotBlocked(hall.getUuid(), hallBookingRequestDto))
        .sorted()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No halls present for desired time slot"));
  }

  private void validateInputTime(final HallBookingRequestDto dto) {
    if (dto.getBookingDate().isBefore(LocalDate.now())
        || (dto.getBookingDate().isEqual(LocalDate.now())
            && dto.getStartTime().isBefore(LocalTime.now()))) {
      throw new RuntimeException("Input date/time cannot be before current time");
    }
    if (dto.getStartTime().isAfter(dto.getEndTime())) {
      throw new RuntimeException("End time cannot be before start time");
    }
  }

  public BookingInfoResponseDto bookHall(
      final UUID hallId, final HallBookingRequestDto hallBookingRequestDto) {
    final HallEntity hallEntity =
        hallRepository.findById(hallId).orElseThrow(() -> new RuntimeException("Invalid hall id"));
    validateInputTime(hallBookingRequestDto);
    if (isSlotBlocked(hallEntity.getUuid(), hallBookingRequestDto)) {
      throw new RuntimeException(
          "The hall was booked since last checked, please check availability again");
    }
    final HallBookingEntity hallBookingEntity = new HallBookingEntity();
    hallBookingEntity.setHallEntity(hallEntity);
    hallBookingEntity.setBookingDate(hallBookingRequestDto.getBookingDate());
    hallBookingEntity.setStartTime(hallBookingRequestDto.getStartTime());
    hallBookingEntity.setEndTime(hallBookingRequestDto.getEndTime());
    hallEntity.getHallBookings().add(hallBookingEntity);
    return buildBookingResponse(hallBookingRepository.save(hallBookingEntity));
  }

  private boolean isSlotBlocked(final UUID hallId, final HallBookingRequestDto dto) {
    return hallBookingRepository
        .findAllByHallEntity_UuidAndBookingDate(hallId, dto.getBookingDate()).stream()
        .anyMatch(booking -> isConflictingWithAnotherBooking(booking, dto));
  }

  private boolean isConflictingWithAnotherBooking(
      HallBookingEntity booking, final HallBookingRequestDto dto) {
    return dto.getEndTime().isAfter(booking.getStartTime())
        && booking.getEndTime().isAfter(dto.getStartTime());
  }

  public List<BookingInfoResponseDto> getBookings(final BookingInfoRequestDto dto) {
    return hallBookingRepository.findAll().stream()
        .filter(booking -> isInRange(booking.getBookingDate(), dto))
        .sorted()
        .map(this::buildBookingResponse)
        .collect(Collectors.toList());
  }

  private BookingInfoResponseDto buildBookingResponse(final HallBookingEntity booking) {
    return BookingInfoResponseDto.builder()
        .bookingId(booking.getUuid())
        .bookedAt(booking.getCreatedAt().toLocalDateTime())
        .bookingDate(booking.getBookingDate())
        .startTime(booking.getStartTime())
        .endTime(booking.getEndTime())
        .hallInfo(buildHallInfoResponse(booking.getHallEntity()))
        .build();
  }

  private HallInfoResponseDto buildHallInfoResponse(final HallEntity hallEntity) {
    return HallInfoResponseDto.builder()
        .hallId(hallEntity.getUuid())
        .hallName(hallEntity.getHallName())
        .build();
  }

  private boolean isInRange(LocalDate bookingDate, BookingInfoRequestDto dto) {
    if (bookingDate.isAfter(dto.getStartDate()) && bookingDate.isBefore(dto.getEndDate())) {
      return true;
    }
    return bookingDate.isEqual(dto.getStartDate()) || bookingDate.isEqual(dto.getEndDate());
  }
}
