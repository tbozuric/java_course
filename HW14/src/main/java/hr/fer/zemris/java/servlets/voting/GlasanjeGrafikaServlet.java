package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.charts.PieChart;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class is a servlet that allows to display the circular graph based on the data obtained by voting.
 * This action is accessible on local URL /servleti/glasanje-grafika.
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -2217496507394602706L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PieDataset dataset = populateData(Long.parseLong(req.getParameter("pollID")));
            if (dataset == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            PieChart chart = new PieChart(dataset, "Rezultati glasanja");
            resp.setContentType("image/png");
            resp.getOutputStream().write(chart.getAsBytes());
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Loads data for drawing a pie chart.
     *
     * @param pollID the poll id.
     * @return loaded data for drawing a pie chart.
     */
    private PieDataset populateData(long pollID) {
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, true);

        //invalid poll id
        if (pollOptions.size() == 0) {
            return null;
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (PollOption pollOption : pollOptions) {
            if (pollOption.getVotesCount() > 0) {
                dataset.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount());
            }
        }
        return dataset;
    }
}
