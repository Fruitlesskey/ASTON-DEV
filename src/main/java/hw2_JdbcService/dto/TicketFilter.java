package hw2_JdbcService.dto;

public record TicketFilter(int limit, int offset, String passengerName, String seatNo ) {

}
