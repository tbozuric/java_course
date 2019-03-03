package hr.fer.zemris.java.blog.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is used to log out of the system.
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * The default serial version UID:
     */
    private static final long serialVersionUID = -3642679140455221811L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }
}
