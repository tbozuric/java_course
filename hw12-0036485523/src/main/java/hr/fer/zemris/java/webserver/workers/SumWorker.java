package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;

/**
 * The class that sums up the numbers obtained as arguments and prints their sum.
 */
public class SumWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {


        try {
            String a = context.getParameter("a");
            String b = context.getParameter("b");
            int num1 = getNumber(a, 1);
            int num2 = getNumber(b, 2);
            String sum = String.valueOf(num1 + num2);
            context.setTemporaryParameter("zbroj", sum);
            context.setTemporaryParameter("a", String.valueOf(num1));
            context.setTemporaryParameter("b", String.valueOf(num2));
            context.getDispatcher().dispatchRequest("/private/calc.smscr");
        } catch (IOException ex) {
            // Log exception to servers log...
            ex.printStackTrace();
        }
    }

    private int getNumber(String a, int defaultValue) {
        try {
            defaultValue = Integer.parseInt(a);
        } catch (NumberFormatException ignorable) {
        }
        return defaultValue;
    }
}
