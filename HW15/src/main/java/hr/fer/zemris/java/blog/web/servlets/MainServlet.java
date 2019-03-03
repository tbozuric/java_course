package hr.fer.zemris.java.blog.web.servlets;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.servlets.forms.Form;
import hr.fer.zemris.java.blog.web.servlets.forms.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class represents the main servlet. It offers user login, reviewing all existing user blogs and registering
 * new user.s
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -337865347737107419L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", DAOProvider.getDAO().getUsers());
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Form loginForm = new LoginForm();
        loginForm.populateFromRequest(req);
        loginForm.validate();

        if (!loginForm.isEmpty()) {
            req.setAttribute("record", loginForm);
            req.setAttribute("users", DAOProvider.getDAO().getUsers());
            req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
            return;
        }

        storeToSession(req, ((LoginForm) loginForm).getBlogUser());
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + ((LoginForm) loginForm).getUsername());
    }

    /**
     * Stores information about the currently logged in user in the session.
     *
     * @param req      the {@link HttpServletRequest}.
     * @param blogUser the currently logged in  blog user.
     */
    private void storeToSession(HttpServletRequest req, BlogUser blogUser) {
        HttpSession session = req.getSession();
        session.setAttribute("current.user.id", blogUser.getId());
        session.setAttribute("current.user.fn", blogUser.getFirstName());
        session.setAttribute("current.user.ln", blogUser.getLastName());
        session.setAttribute("current.user.nick", blogUser.getNick());
    }
}
