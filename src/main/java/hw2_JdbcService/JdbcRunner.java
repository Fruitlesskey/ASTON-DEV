package hw2_JdbcService;

import hw2_JdbcService.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        Long flightId = 2L;
        List<Long> ticketsByFlightId = getTicketsByFlightId(flightId);
        System.out.println(ticketsByFlightId);
        List<Long> result = getFlightsBetween(LocalDate.of(2020, 10, 1).atStartOfDay(), LocalDateTime.now());
        System.out.println(result);
        try {
            checkMEtaData();
        }finally {
            ConnectionManager.closePool();
        }
    }

    private static void checkMEtaData() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            System.out.println(catalogs);
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));
            }
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                                
                """;
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(10 );
            preparedStatement.setMaxRows(100);

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        }
        return result;
    }

    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                          """;
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flightId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }
        return result;
    }
}



