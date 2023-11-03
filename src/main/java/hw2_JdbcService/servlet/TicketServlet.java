package hw2_JdbcService.servlet;

import hw2_JdbcService.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {

    private final TicketService service = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long flightId = Long.valueOf(req.getParameter("flightId"));

        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<ul>");
            service.findAllByFlightId(flightId).forEach(ticketDto -> writer.write("""
                    <li>
                    %s
                    </li>
                    """.formatted(ticketDto.getFlightID())));

            writer.write("</ul>");
        }
    }

}
