package hw2_JdbcService;

import hw2_JdbcService.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        long flightId = 9;
        String deleteFlightSql = "DELETE FROM flight WHERE id = ?";
        String deleteTickets = "DELETE FROM ticket WHERE flight_id = ?";
        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketsStatement = null;
        try  {
            connection.setAutoCommit(false);

            deleteFlightStatement.setLong(1, flightId);
            deleteTicketsStatement.setLong(1, flightId);

            deleteTicketsStatement.executeUpdate();
            if (true) {
                throw new RuntimeException("Ooops"); 
            }

            deleteFlightStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();

            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }if (deleteTicketsStatement != null) {
                deleteTicketsStatement.close();
            }
        }
    }
}
