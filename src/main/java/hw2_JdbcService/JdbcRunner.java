package hw2_JdbcService;

import hw2_JdbcService.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
String sql = """
       SELECT *
       FROM ticket
        
               """;
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement()) {

            System.out.println(connection.getTransactionIsolation());

            ResultSet execute = statement.executeQuery(sql);
            while (execute.next()) {
                System.out.println(execute.getLong("id"));
                System.out.println(execute.getString("passenger_no"));
                System.out.println(execute.getBigDecimal("cost"));
                System.out.println("----");
            }
        }
    }
}
