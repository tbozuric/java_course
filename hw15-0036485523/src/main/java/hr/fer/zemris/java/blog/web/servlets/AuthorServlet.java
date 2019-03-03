package hr.fer.zemris.java.blog.web.servlets;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.web.servlets.forms.BlogEntryForm;
import hr.fer.zemris.java.blog.web.servlets.forms.CommentEntryForm;
import hr.fer.zemris.java.blog.web.servlets.forms.Form;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This servlet allows you to browse all blogs from a user whose name is in the URL, allowing the owner
 * to create new blogs and edit existing ones. Other users can comment on individual blogs and view these blogs.
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 5505609130481023969L;

    /**
     * The pattern for identifying a URL in the forma : /servleti/author/NICK
     */
    private static final Pattern NICK_REGEX =
            Pattern.compile("^[A-Z0-9]+([_ -]?[A-Z0-9])*$", Pattern.CASE_INSENSITIVE);

    /**
     * The pattern for identifying a URL in the format: /servleti/author/NICK/new
     */
    private static final Pattern NICK_REGEX_NEW =
            Pattern.compile("^[A-Z0-9]+([_ -]?[A-Z0-9])*/new$", Pattern.CASE_INSENSITIVE);

    /**
     * The pattern for identifying a URL in the format: /servleti/author/NICK/edit
     */
    private static final Pattern NICK_REGEX_EDIT =
            Pattern.compile("^[A-Z0-9]+([_ -]?[A-Z0-9])*/edit$", Pattern.CASE_INSENSITIVE);

    /**
     * The pattern for identifying a URL in the format: /servleti/author/NICK/EID
     */
    private static final Pattern NICK_REGEX_EID =
            Pattern.compile("^[A-Z0-9]+([_ -]?[A-Z0-9])*/[0-9]+$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo();

        if (info != null) {
            info = info.substring(1);
            String storedNick = (String) req.getSession().getAttribute("current.user.nick");

            if (NICK_REGEX.matcher(info).matches()) {
                List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(info);
                req.setAttribute("entries", entries);
                req.setAttribute("nick", info);
                req.setAttribute("currentUser", storedNick != null && storedNick.equals(info));
                req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
                return;
            }

            if (NICK_REGEX_NEW.matcher(info).matches()) {
                String nick = info.substring(0, info.indexOf("/"));

                if (generateErrorMessage(req, resp, storedNick, nick)) {
                    return;
                }

                Form newEntry = new BlogEntryForm();
                newEntry.populateFromRequest(req);

                req.setAttribute("record", newEntry);
                req.setAttribute("nick", nick);
                req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
                return;
            }
            if (NICK_REGEX_EDIT.matcher(info).matches()) {

                String eid = req.getParameter("id");
                Long id = getBlogEntryId(req, resp, eid);
                if (id == null) {
                    return;
                }
                String nick = info.substring(0, info.indexOf("/"));
                if (generateErrorMessage(req, resp, storedNick, nick)) {
                    return;
                }

                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
                if (entry == null) {
                    req.setAttribute("error", "The requested record does not exist.");
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                    return;
                }

                if (!entry.getCreator().getNick().equals(nick)) {
                    req.setAttribute("error", "You can not edit this blog entry!");
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                    return;
                }

                Form blogEntryForm = new BlogEntryForm();
                ((BlogEntryForm) blogEntryForm).populateFromBlogEntry(entry);

                req.setAttribute("entryId", id);
                req.setAttribute("nick", nick);
                req.setAttribute("record", blogEntryForm);
                req.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(req, resp);
                return;
            }

            if (NICK_REGEX_EID.matcher(info).matches()) {
                String nick = info.substring(0, info.indexOf("/"));
                String eid = info.substring(info.indexOf("/") + 1);

                Long id = getBlogEntryId(req, resp, eid);
                if (id == null) return;

                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

                if (entry == null || !entry.getCreator().getNick().equals(nick)) {
                    req.setAttribute("error", "Incorrect parameters were received.");
                    req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
                    return;
                }
                req.setAttribute("blogEntry", entry);
                req.setAttribute("currentUser", storedNick != null && storedNick.equals(nick));
                req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
                return;
            }
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getPathInfo();

        if (info != null) {
            info = info.substring(1);
            String storedNick = (String) req.getSession().getAttribute("current.user.nick");

            if (NICK_REGEX_EID.matcher(info).matches()) {
                Form commentEntryForm = new CommentEntryForm();
                commentEntryForm.populateFromRequest(req);
                String nick = info.substring(0, info.indexOf("/"));

                String eid = info.substring(info.indexOf("/") + 1);
                Long id = getBlogEntryId(req, resp, eid);
                if (id == null) {
                    return;
                }
                BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);

                if (storedNick != null && storedNick.equals(entry.getCreator().getNick())) {
                    ((CommentEntryForm) commentEntryForm).setEmail(entry.getCreator().getEmail());
                }
                commentEntryForm.validate();

                if (!commentEntryForm.isEmpty()) {
                    req.setAttribute("blogEntry", entry);
                    req.setAttribute("currentUser", storedNick != null && storedNick.equals(nick));
                    req.setAttribute("record", commentEntryForm);
                    req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
                    return;
                }

                BlogComment comment = new BlogComment();
                comment.setUserEMail(((CommentEntryForm) commentEntryForm).getEmail());
                comment.setPostedOn(new Date());
                comment.setMessage(((CommentEntryForm) commentEntryForm).getComment());

                DAOProvider.getDAO().addComment(id, comment);
                resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick + "/" + eid);
            }
        }
    }

    /**
     * Returns the identifier of blog entry.
     *
     * @param req  the {@link HttpServletRequest}.
     * @param resp the {@link HttpServletResponse}.
     * @param eid  the entry identifier.
     * @return the identifier of blog entry.
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs.
     */
    private Long getBlogEntryId(HttpServletRequest req, HttpServletResponse resp, String eid) throws ServletException, IOException {
        Long id;
        try {
            id = Long.valueOf(eid);
        } catch (Exception ex) {
            req.setAttribute("error", "Incorrect parameters were received.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return null;
        }
        return id;
    }

    /**
     * Returns true if user must first log into the system to perform the operation.
     *
     * @param req        the {@link HttpServletRequest}.
     * @param resp       the {@link HttpServletResponse}.
     * @param storedNick the username of current logged in user.
     * @param nick       the nick.
     * @return true if user must first log into the system to perform the operation.
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an input/output error occurs.
     */
    private boolean generateErrorMessage(HttpServletRequest req, HttpServletResponse resp, String storedNick, String nick) throws ServletException, IOException {
        if (storedNick == null || !storedNick.equals(nick)) {
            req.setAttribute("error", "You must first log into the system.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return true;
        }
        return false;
    }
}
