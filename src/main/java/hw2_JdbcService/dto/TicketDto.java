package hw2_JdbcService.dto;

import java.util.Objects;

public class TicketDto {
    private final Long id;

    private final Long flightID;

    private final String seatNo;

    public TicketDto(Long id, Long flightID, String seatNo) {
        this.id = id;
        this.flightID = flightID;
        this.seatNo = seatNo;
    }

    public Long getId() {
        return id;
    }

    public Long getFlightID() {
        return flightID;
    }

    public String getSeatNo() {
        return seatNo;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TicketDto ticketDto = (TicketDto) object;
        return Objects.equals(id, ticketDto.id) && Objects.equals(flightID, ticketDto.flightID) && Objects.equals(seatNo, ticketDto.seatNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightID, seatNo);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
               "id=" + id +
               ", flightID=" + flightID +
               ", seatNo='" + seatNo + '\'' +
               '}';
    }
}
