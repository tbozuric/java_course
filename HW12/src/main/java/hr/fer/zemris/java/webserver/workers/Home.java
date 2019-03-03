package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Map;

/**
 * The class that sets current background color for rendering HTML files.
 */
public class Home implements IWebWorker {
    @Override
    public void processRequest(RequestContext context) throws Exception {

        Map<String, String> persistentMap = context.getPersistentParameters();

        if (persistentMap.get("bgcolor") != null) {
            context.setTemporaryParameter("background", persistentMap.get("bgcolor"));
        } else {
            context.setTemporaryParameter("background", "7F7F7F");
        }
        context.getDispatcher().dispatchRequest("/private/home.smscr");
    }
}
