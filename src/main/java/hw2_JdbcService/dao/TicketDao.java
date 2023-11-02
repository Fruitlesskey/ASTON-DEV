package hw2_JdbcService.dao;

import hw2_JdbcService.dto.TicketFilter;
import hw2_JdbcService.entity.Ticket;
import hw2_JdbcService.exception.DaoException;
import hw2_JdbcService.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();
    private static final String DELETE_SQL = """
            DELETE FROM ticket WHERE id = ?
            """;

    public static final String SAVE_SQl = """
            INSERT INTO ticket (passenger_no,passenger_name,flight_id,seat_no,cost) 
                        
            VALUES (?,?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET passenger_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
                WHERE id = ?
            """;
    private static final String FIND_ALL_SQL = """
            SELECT ticket.id,
            passenger_no,
            passenger_name,
             flight_id,
             seat_no,
             cost,
             f.status ,
             f.aircraft_id,
             f.arrival_airport_code,
             f.arrival_date,
             f.departure_airport_code,
             f.departure_date
             FROM ticket
             JOIN public.flight f on f.id = ticket.flight_id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE ticket.id = ?
            """;
    private static final FlightDao flightDao = FlightDao.getInstance();

    private TicketDao() {
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.seatNo() != null) {
            whereSql.add("seat_no LIKE ?");
            parameters.add("%" + filter.seatNo() + "%");
        }
        if (filter.passengerName() != null) {
            whereSql.add("passenger_name = ?");
            parameters.add(filter.passengerName());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        String sql = FIND_ALL_SQL + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public List<Ticket> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Optional<Ticket> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Ticket ticket = null;
            while (resultSet.next()) {
                ticket = buildTicket(resultSet);

            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    public void update(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticket.getPassengerN());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlight().id());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getCost());
            preparedStatement.setLong(6, ticket.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Ticket save(Ticket ticket) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQl, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticket.getPassengerN());
            preparedStatement.setString(2, ticket.getPassengerName());
            preparedStatement.setLong(3, ticket.getFlight().id());
            preparedStatement.setString(4, ticket.getSeatNo());
            preparedStatement.setBigDecimal(5, ticket.getCost());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getLong("id"));
            }
            return ticket;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getString("passenger_no"),
                resultSet.getString("passenger_name"),
                flightDao.findById(resultSet.getLong("flight_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost")

        );
    }


}
