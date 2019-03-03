package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Represents a home page with available polls.
 * The polls offered will be those stored in the database.
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -7231166481398056287L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Poll> polls = DAOProvider.getDao().getPolls();
        req.setAttribute("polls", polls);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }
}
