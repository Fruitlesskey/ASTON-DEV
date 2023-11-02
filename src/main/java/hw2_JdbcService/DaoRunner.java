package hw2_JdbcService;

import hw2_JdbcService.dao.TicketDao;
import hw2_JdbcService.dto.TicketFilter;
import hw2_JdbcService.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
        Optional<Ticket> ticket = TicketDao.getInstance().findById(5L);
        System.out.println(ticket);

    }

    private static void filterTest() {
        TicketFilter ticketFilter = new TicketFilter(3, 0, "Андрей Желудь", "A1");
        List<Ticket> tickets = TicketDao.getInstance().findAll(ticketFilter);
        System.out.println(tickets.size());
        System.out.println(tickets);
    }

    private static void getByIdAndUpdateTest() {
        TicketDao ticketDao = TicketDao.getInstance();
        Optional<Ticket> ticket = ticketDao.findById(2L);
        System.out.println(ticket);

        ticket.ifPresent(ticket1 -> {
            ticket1.setCost(BigDecimal.valueOf(199.99));
            ticketDao.update(ticket1);
            System.out.println(ticket1);
        });
    }

    private static void deleteTest() {
        TicketDao ticketDao = TicketDao.getInstance();
        boolean delete = ticketDao.delete(56L);
        System.out.println(delete);
    }

    private static void saveTest() {
        TicketDao ticketDao = TicketDao.getInstance();
        Ticket ticket = new Ticket();
        ticket.setPassengerN("2442424");
//        ticket.setFlight(3L);
        ticket.setCost(BigDecimal.TEN);
        ticket.setPassengerName("Albert");
        ticket.setSeatNo("B3");

        ticketDao.save(ticket);
        System.out.println(ticket);
    }
}
