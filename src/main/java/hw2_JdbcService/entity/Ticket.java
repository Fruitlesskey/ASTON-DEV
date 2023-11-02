package hw2_JdbcService.entity;

import java.math.BigDecimal;

public class Ticket {
    private Long id;

    private String passengerN;
    private String passengerName;
    private Flight flight;
    private String seatNo;

    private BigDecimal cost;

    public Ticket(Long id, String passengerN, String passengerName, Flight flight, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passengerN = passengerN;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassengerN() {
        return passengerN;
    }

    public void setPassengerN(String passengerN) {
        this.passengerN = passengerN;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", passengerN='" + passengerN + '\'' +
               ", passengerName='" + passengerName + '\'' +
               ", flight=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", cost=" + cost +
               '}';
    }

}
