package hw2_JdbcService.dao;

import hw2_JdbcService.entity.Flight;
import hw2_JdbcService.entity.FlightStatus;
import hw2_JdbcService.entity.Ticket;
import hw2_JdbcService.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();

    public static final String FIND_ALL_BY_FLIGHT_ID = """
            SELECT *
            FROM ticket
            WHERE flight_id = ?
            """;

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private TicketDao() {

    }

    public List<Ticket> findAllByFlightId(Long flightId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_FLIGHT_ID)) {
            preparedStatement.setObject(1, flightId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public Optional<Ticket> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(Ticket entity) {

    }

    @Override
    public Ticket save(Ticket entity) {
        return null;
    }

    private Ticket buildTicket(ResultSet resultSet) {
        try {
            return new Ticket(
                    resultSet.getObject("id", Long.class),
                    resultSet.getObject("passenger_no", String.class),
                    resultSet.getObject("passenger_name", String.class),
                    resultSet.getObject("flight_id", Long.class),
                    resultSet.getObject("seat_no", String.class),
                    resultSet.getObject("cost", BigDecimal.class)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
