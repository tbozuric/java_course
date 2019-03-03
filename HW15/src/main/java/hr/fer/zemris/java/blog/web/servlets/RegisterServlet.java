package hr.fer.zemris.java.blog.web.servlets;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.servlets.forms.Form;
import hr.fer.zemris.java.blog.web.servlets.forms.RegistrationForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents a servlet used to register new blog users {@link BlogUser}.
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -6111149461093850042L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Form registrationForm = new RegistrationForm();
        registrationForm.populateFromRequest(req);
        registrationForm.validate();

        if (!registrationForm.isEmpty()) {
            req.setAttribute("record", registrationForm);
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;
        }
        BlogUser user = ((RegistrationForm) registrationForm).getUser();
        DAOProvider.getDAO().createUser(user);
        resp.sendRedirect(req.getContextPath() + "/servleti/main");
    }
}
