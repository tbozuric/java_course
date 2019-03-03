package hr.fer.zemris.java.servlets.voting;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static hr.fer.zemris.java.servlets.voting.UtilVoting.Band;

/**
 * This class is a servlet that is used to load possible bands for voting.
 * This action is accessible on local URL /glasanje.
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 7118222799386093250L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        Map<Integer, Band> bands = UtilVoting.getBands(context);
        req.setAttribute("bands", bands);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
