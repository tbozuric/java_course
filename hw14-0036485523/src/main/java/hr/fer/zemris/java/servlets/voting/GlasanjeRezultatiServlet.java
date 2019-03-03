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
import java.util.stream.Collectors;

/**
 * This class is a servlet that provides insight into the voting results.
 * over the parameters, the sorted voices are sent to the <code>glasanjeRez.jsp</code>
 * page where we can see the number of votes for a particular poll option,
 * the circular diagram, we have the ability to download the XLS file with
 * the voting results, and we have a link to some of the wonders about the winners.
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 6511705095819066275L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long pollID = Long.parseLong(req.getParameter("pollID"));
            List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, true);

            //invalid poll id
            if (pollOptions.size() == 0) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            req.setAttribute("votes", pollOptions);
            req.setAttribute("pollID", pollID);

            List<PollOption> winners = getWinnersOfPoll(pollOptions);

            req.setAttribute("winners", winners);
            req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Returns the list of winners of the poll
     *
     * @param pollOptions the poll options
     * @return the winners of the poll
     */
    private List<PollOption> getWinnersOfPoll(List<PollOption> pollOptions) {
        long longest = pollOptions.stream()
                .mapToLong(PollOption::getVotesCount)
                .max()
                .orElse(-1);
        return pollOptions.stream().filter(v -> v.getVotesCount() == longest)
                .collect(Collectors.toList());
    }
}
