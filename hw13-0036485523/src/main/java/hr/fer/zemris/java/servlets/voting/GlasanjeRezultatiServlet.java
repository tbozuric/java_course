package hr.fer.zemris.java.servlets.voting;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static hr.fer.zemris.java.servlets.voting.UtilVoting.Vote;

/**
 * This class is a servlet that provides insight into the voting results.
 * over the parameters, the sorted voices are sent to the <code>glasanjeRez.jsp</code>
 * page where we can see the number of votes for a particular band,
 * the circular diagram, we have the ability to download the XLS file with
 * the voting results, and we have links to  some of the tracks played by certain bands.
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 6511705095819066275L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        List<Vote> votes = UtilVoting.getSortedVotes(context);
        req.setAttribute("votes", votes);

        int longest = votes.stream()
                .mapToInt(Vote::getNumberOfVotes)
                .max()
                .orElse(-1);

        req.setAttribute("winners", votes.stream().filter(v->v.getNumberOfVotes()==longest)
                .collect(Collectors.toList()));
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
