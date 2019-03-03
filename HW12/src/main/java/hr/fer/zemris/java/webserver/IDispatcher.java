package hr.fer.zemris.java.webserver;

/**
 * Represents dispatcher who delegates requests.
 */
public interface IDispatcher {

    /**
     * Dispatches request.
     *
     * @param urlPath the url path.
     * @throws Exception if some error occurred while proccessing request.
     */
    void dispatchRequest(String urlPath) throws Exception;
}
