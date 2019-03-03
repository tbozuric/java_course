package hr.fer.zemris.java.blog.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Represents a redirect servlet, each query type /index.jsp(or /) redirects to the polls ({@link MainServlet}) page.
 */
@WebServlet(urlPatterns = {"/", "/index.jsp"})
public class IndexRedirectServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 601505750858907757L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }
}
