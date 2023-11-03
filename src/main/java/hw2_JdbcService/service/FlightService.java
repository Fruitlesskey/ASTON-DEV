package hw2_JdbcService.service;

import hw2_JdbcService.dao.FlightDao;
import hw2_JdbcService.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream()
                .map(flight -> new FlightDto(
                        flight.getId(),
                                """
                                %s  - %s  -%s
                                """.formatted(
                                flight.getDepartureAirportCode(),
                                flight.getArrivalAirportCode(),
                                flight.getStatus()
                        )))
                .collect(Collectors.toList());
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }
}
