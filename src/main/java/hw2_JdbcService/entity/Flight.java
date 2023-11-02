package hw2_JdbcService.entity;

import java.time.LocalDateTime;

public record Flight(Long id,
                     String flightNo,
                     LocalDateTime departureDate,
                     String departureAirportCode,
                     LocalDateTime arrivalDate,
                     String arrivalAirportCode,
                     Integer aircraftID,
                     String status) {
}
