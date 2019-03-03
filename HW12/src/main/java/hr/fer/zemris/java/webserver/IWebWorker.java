package hr.fer.zemris.java.webserver;

/**
 * Represents an web worker.
 */
public interface IWebWorker {

    /**
     * Processes the requests.
     *
     * @param context the request context.
     * @throws Exception if an error occurred while processing request.
     */
    void processRequest(RequestContext context) throws Exception;
}
