package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a servlet that allows voting for one of the offered poll options and stores results into the database.
 * After voting, you can see the global results on the page <code>/glasanje-rezultati</code> you will be redirected to.
 * This action is accessible on local URL /servleti/glasanje-glasaj.
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1177821670749063098L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pollOptionId = req.getParameter("id");

        PollOption pollOption;
        try {
            pollOption = DAOProvider.getDao().getPollOption(Long.parseLong(pollOptionId));
            if (pollOption == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            DAOProvider.getDao().incrementVotesCount(Long.parseLong(pollOptionId));
            resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollOption.getPoll().getId());
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
