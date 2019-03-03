package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class that changes the background color to the given color(if it is in  the correct hex format).
 * In addition, the rendered page offers a link to {@link Home}.
 */
public class BgColorWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String color = context.getParameter("bgcolor");
        context.setMimeType("text/html");
        context.write("<html><body>");
        if (color != null && color.toUpperCase().matches("[A-F0-9]{6}")) {
            context.setPersistentParameter("bgcolor", color.toUpperCase());
            context.write("<h2>Color is updated!</h2>");
            context.write("<a href=\"/index2.html\">index2.html</a>");
        } else {
            context.write("<h2>Color is  not updated!</h2>");
            context.write("<a href=\"/index2.html\">index2.html</a>");
        }
        context.write("</body></html>");
    }
}
