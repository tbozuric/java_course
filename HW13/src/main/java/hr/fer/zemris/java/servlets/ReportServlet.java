package hr.fer.zemris.java.servlets;

import hr.fer.zemris.java.charts.PieChart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * Represents a servlet used to dynamically create a  pie chart. The request is forwarded to
 * report.jsp that create a page that contains a heading “OS usage”, a paragraph “Here are the results of OS
 * usage in survey that we completed.” and  a dynamically created image showing Pie Chart.
 * This action is accessible on local URL /reportImage.
 */
@WebServlet("/reportImage")
public class ReportServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -9146912702508672301L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PieChart chart = new PieChart("OS usage");
        resp.setContentType("image/png");

        byte[] imageBytes = Base64.getEncoder().encode(chart.getAsBytes());
        String imageEncoded = new String(imageBytes, "UTF-8");
        req.setAttribute("image", imageEncoded);
        req.getRequestDispatcher("/WEB-INF/pages/report.jsp").forward(req, resp);
    }
}
