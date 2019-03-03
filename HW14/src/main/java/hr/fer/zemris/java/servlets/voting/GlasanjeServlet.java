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
import java.util.List;


/**
 * This class is a servlet that is used to load possible options for voting.
 * This action is accessible on local URL /servleti/glasanje.
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 7118222799386093250L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long pollID = Long.parseLong(req.getParameter("pollID"));
            List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, false);

            //invalid poll id
            if (pollOptions.size() == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            req.setAttribute("options", pollOptions);
            req.setAttribute("poll", pollOptions.get(0).getPoll());

            req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
