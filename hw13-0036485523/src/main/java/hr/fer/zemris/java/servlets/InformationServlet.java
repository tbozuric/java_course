package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Represents a servlet context listener that adds information when it was called into servlet's context's attributes.
 * Used to display(appinfo.jsp) how long is this web application running.
 */
@WebListener
public class InformationServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.setAttribute("time", System.currentTimeMillis());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute("time");
    }
}
