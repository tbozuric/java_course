package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.charts.PieChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static hr.fer.zemris.java.servlets.voting.UtilVoting.Vote;

/**
 * This class is a servlet that allows to display the circular graph based on the data obtained by voting.
 * This action is accessible on local URL /glasanje-grafika.
 */
@WebServlet("/glasanje-grafika")
public class VotingGraphicsServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -2217496507394602706L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PieDataset dataset = populateData(req.getServletContext());
        PieChart chart = new PieChart(dataset, "Rezultati glasanja");

        resp.setContentType("image/png");
        resp.getOutputStream().write(chart.getAsBytes());
    }

    /**
     * Loads data for drawing a pie chart.
     *
     * @param context the servlet context.
     * @return loaded data for drawing a pie chart.
     * @throws IOException if an error occurs while reading the data.
     */
    private PieDataset populateData(ServletContext context) throws IOException {
        List<Vote> votes = UtilVoting.getSortedVotes(context);
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Vote vote : votes) {
            if (vote.getNumberOfVotes() != 0) {
                dataset.setValue(vote.getBand().getNameOfMusicalBand(), vote.getNumberOfVotes());
            }
        }
        return dataset;
    }
}
