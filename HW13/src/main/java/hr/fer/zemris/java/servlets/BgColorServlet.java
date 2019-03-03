package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is a servlet that remembers the selected color as a user session and that color
 * is used as a background color on all pages while a valuable session.
 * Color can be set via <code>colors.jsp</code> page
 * This action is accessible on local URL /setcolor.
 */
@WebServlet("/setcolor")
public class BgColorServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -7215829584806000510L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("pickedBgColor", req.getParameter("bgColor"));
        resp.sendRedirect("/webapp2/index.jsp");
    }
}
