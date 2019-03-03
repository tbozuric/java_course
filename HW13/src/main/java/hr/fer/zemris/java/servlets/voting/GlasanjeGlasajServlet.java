package hr.fer.zemris.java.servlets.voting;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static hr.fer.zemris.java.servlets.voting.UtilVoting.Band;

/**
 * This class is a servlet that allows you to vote on your favorite band and store results in the "database".
 * After voting, you can see the global results on the page <code>/glasanje-rezultati</code> you will be redirected to.
 * This action is accessible on local URL /glasanje-glasaj.
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 1177821670749063098L;

    @Override
    protected synchronized void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();
        String fileName = context.getRealPath("/WEB-INF/glasanje-rezultati.txt");
        Path path = Paths.get(fileName);
        String id = req.getParameter("id");

        if (!path.toFile().exists() || path.toFile().isDirectory()) {
            createDatabaseFile(context, path);
        }

        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
            return;
        }

        updateDatabase(path, id);
        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }

    /**
     * Updates the number of votes for band with the given id.
     *
     * @param path the path to the database.
     * @param id   the id of musical band
     * @throws IOException if an error occurs while updating the database.
     */
    private synchronized void updateDatabase(Path path, String id) throws IOException {
        List<String> fileContent = Files.readAllLines(path, StandardCharsets.UTF_8);

        int length = fileContent.size();
        int index = -1;
        String line = "";
        for (int i = 0; i < length; i++) {
            line = fileContent.get(i);
            if (line.startsWith(id)) {
                index = i;
                break;
            }
        }

        String numberOfVotes = line.split("\\t")[1];
        int newNumberOfVotes = Integer.parseInt(numberOfVotes) + 1;
        fileContent.set(index, id + "\t" + newNumberOfVotes);

        Files.write(path, fileContent, StandardCharsets.UTF_8);
    }

    /**
     * Creates a file that represents the database if it does not exist.
     *
     * @param context the servlet context.
     * @param path    the path to the file.
     * @throws IOException if an error occurs while creating a new file.
     */
    private synchronized void createDatabaseFile(ServletContext context, Path path) throws IOException {
        Map<Integer, Band> bands = new TreeMap<>(UtilVoting.getBands(context));
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        for (Map.Entry<Integer, Band> pair : bands.entrySet()) {
            writer.write(pair.getKey() + "\t" + "0\n");
        }
        writer.close();
    }
}
