package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.Map;

/**
 *The class that prints the submitted parameters.
 */
public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("text/html");

        Map<String, String > params =context.getParameters();

        try {
            context.write("<html><body>");
            context.write("<h1>Hello!</h1>");
            context.write("<table  border=\"1\">");
            context.write("<tr>");
            context.write("<th> key </th>");
            context.write("<th> value </th>");
            context.write("</tr>");
            for(Map.Entry<String,String> entry : params.entrySet()){
                context.write("<tr>");
                context.write("<td>" + entry.getKey()  + "</td>");
                context.write("<td>" + entry.getValue()  + "</td>");
                context.write("</tr>");
            }
            context.write("</body></html>");
        } catch(IOException ex) {
            // Log exception to servers log...
            ex.printStackTrace();
        }
    }
}
