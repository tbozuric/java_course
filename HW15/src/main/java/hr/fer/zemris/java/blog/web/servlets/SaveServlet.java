package hr.fer.zemris.java.blog.web.servlets;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.web.servlets.forms.BlogEntryForm;
import hr.fer.zemris.java.blog.web.servlets.forms.Form;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents a servlet used to store changes and create new blog entries{@link BlogEntry}.
 */
@WebServlet("/servleti/save")
public class SaveServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -7231166481398056287L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String storedNick = (String) req.getSession().getAttribute("current.user.nick");
        String nick = req.getParameter("nick");

        if (storedNick == null || !storedNick.equals(nick)) {
            req.setAttribute("error", "Unsupported operation. Please log in and try again.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        Form newEntry = new BlogEntryForm();
        newEntry.populateFromRequest(req);
        newEntry.validate();

        if (!newEntry.isEmpty()) {
            req.setAttribute("record", newEntry);
            req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
            return;
        }

        BlogEntry entry = ((BlogEntryForm) newEntry).getBlogEntry();

        String id = req.getParameter("id");

        if (id != null && !id.isEmpty()) {
            entry.setId(Long.parseLong(id));
            DAOProvider.getDAO().updateBlogEntry(entry);
        } else {
            entry.setCreator(DAOProvider.getDAO().getBlogUser(nick));
            DAOProvider.getDAO().createBlogEntry(entry);
        }
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + storedNick);
    }
}
