package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Represents server request and its context , ie it represents the server response to the client.
 */
public class RequestContext {

    /**
     * The output stream.
     */
    private OutputStream outputStream;

    /**
     * The used charset.
     */
    private Charset charset;

    /**
     * The used encoding.
     */
    private String encoding = "UTF-8";

    /**
     * The status code.
     *
     * @see <a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes">Status code</a>
     */
    private int statusCode = 200;

    /**
     * The status text.
     */
    private String statusText = "OK";

    /**
     * The mime type.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types">Mime types</a>
     */
    private String mimeType = "text/html";

    /**
     * The content length.
     */
    private Long contentLength = null;

    /**
     * The parameters.
     */
    private Map<String, String> parameters;

    /**
     * The temporary parameters.
     */
    private Map<String, String> temporaryParameters;

    /**
     * The persistent  parameters.
     */
    private Map<String, String> persistentParameters;

    /**
     * The list od output cookies.
     */
    private List<RCCookie> outputCookies;

    /**
     * The flag indicating whether the  header is generated.
     */
    private boolean headerGenerated = false;

    /**
     * The request dispatcher.
     */
    private IDispatcher dispatcher;

    /**
     * Creates an instance of {@link RequestContext}.
     *
     * @param outputStream         the output stream.
     * @param parameters           the parameters.
     * @param persistentParameters the persistent parameters.
     * @param outputCookies        the output cookies.
     * @param temporaryParameters  the temporary parameters.
     * @param dispatcher           the request dispatcher.
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies,
                          Map<String, String> temporaryParameters, IDispatcher dispatcher) {
        this(outputStream, parameters, persistentParameters, outputCookies);
        this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
        this.dispatcher = dispatcher;
    }


    /**
     * Creates an instance of {@link RequestContext}.
     *
     * @param outputStream         the output stream.
     * @param parameters           the parameters.
     * @param persistentParameters the persistent parameters.
     * @param outputCookies        the output cookies.
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
                          Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
        this.outputStream = Objects.requireNonNull(outputStream, "Output stream must not be null!");
        this.parameters = parameters == null ? new HashMap<>() : parameters;
        this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
        this.temporaryParameters = new HashMap<>();
        this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
    }

    /**
     * Represents server request context cookie.
     *
     * @see <a href="https://en.wikipedia.org/wiki/HTTP_cookie">Cookie</a>
     */
    public static class RCCookie {

        /**
         * The name.
         */
        private String name;
        /**
         * The value.
         */
        private String value;

        /**
         * The domain. Specifies those hosts to which the cookie will be sent.
         */
        private String domain;

        /**
         * The path. Indicates a URL path that must exist in the requested resource before sending the Cookie header.
         */
        private String path;

        /**
         * The time in seconds for when a cookie will be deleted.
         */
        private Integer maxAge;

        /**
         * The  flag indicating whether it is http-only cookie.
         */
        private boolean httpOnly;


        /**
         * Creates an instance of {@link RCCookie}.
         *
         * @param name     the name.
         * @param value    the value.
         * @param maxAge   the time in seconds for when a cookie will be deleted.
         * @param domain   the domain.
         * @param path     the path.
         * @param httpOnly the flag indicating whether it is http-only cookie.
         * @throws NullPointerException if the name or value is a null reference.
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
            this.name = Objects.requireNonNull(name);
            this.value = Objects.requireNonNull(value);
            this.domain = domain;
            this.path = path;
            this.maxAge = maxAge;
            this.httpOnly = httpOnly;
        }

        /**
         * Returns the name.
         *
         * @return the name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the value.
         *
         * @return the value.
         */
        public String getValue() {
            return value;
        }

        /**
         * Returns the domain.
         *
         * @return the domain.
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Returns true if this is http-only cookie.
         *
         * @return true if this is http-only cookie.
         */
        public boolean isHttpOnly() {
            return httpOnly;
        }

        /**
         * Returns the path.
         *
         * @return the path.
         */
        public String getPath() {
            return path;
        }

        /**
         * Returns the max age.
         *
         * @return the max age.
         */
        public Integer getMaxAge() {
            return maxAge;
        }
    }

    /**
     * Returns the content length.
     *
     * @param contentLength the content length.
     * @throws RuntimeException if the header is already generated.
     */
    public void setContentLength(long contentLength) {
        checkHeader();
        this.contentLength = contentLength;
    }

    /**
     * Returns the encoding.
     *
     * @param encoding the encoding.
     * @throws RuntimeException if the header is already generated.
     */
    public void setEncoding(String encoding) {
        checkHeader();
        this.encoding = encoding;
    }

    /**
     * Returns the status code.
     *
     * @param statusCode the status code.
     * @throws RuntimeException if the header is already generated.
     */
    public void setStatusCode(int statusCode) {
        checkHeader();
        this.statusCode = statusCode;
    }

    /**
     * Returns the status text.
     *
     * @param statusText the status text.
     * @throws RuntimeException if the header is already generated.
     */
    public void setStatusText(String statusText) {
        checkHeader();
        this.statusText = statusText;
    }

    /**
     * Returns the mime type.
     *
     * @param mimeType the mime type.
     * @throws RuntimeException if the header is already generated.
     */
    public void setMimeType(String mimeType) {
        checkHeader();
        this.mimeType = mimeType;
    }

    /**
     * Checks if the header is already generated.
     *
     * @throws RuntimeException if the header is already generated.
     */
    private void checkHeader() {
        if (headerGenerated) {
            throw new RuntimeException("The header is already generated.");
        }
    }

    /**
     * Returns the request dispatcher.
     *
     * @return the request dispatcher.
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Returns the parameters.
     *
     * @return the parameters.
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Returns the temporary parameters.
     *
     * @return the temporary parameters.
     */
    public Map<String, String> getTemporaryParameters() {
        return temporaryParameters;
    }

    /**
     * Sets the temporary parameters.
     *
     * @param temporaryParameters the temporary parameters.
     */
    public void setTemporaryParameters(Map<String, String> temporaryParameters) {
        this.temporaryParameters = temporaryParameters;
    }

    /**
     * Returns the persistent parameters.
     *
     * @return the persistent parameters.
     */
    public Map<String, String> getPersistentParameters() {
        return persistentParameters;
    }

    /**
     * Sets the persistent parameters.
     *
     * @param persistentParameters the persistent parameters.
     */
    public void setPersistentParameters(Map<String, String> persistentParameters) {
        this.persistentParameters = persistentParameters;
    }


    /**
     * Retrieves names of all parameters in parameters map.
     *
     * @return names of all parameters in parameters map.
     */
    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

    /**
     * Retrieves value from persistent parameters map (or null if no association exists).
     *
     * @param name the name of parameter.
     * @return the parameter  associated with the given name or null.
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }

    /**
     * Retrieves value from parameters map (or null if no association exists).
     *
     * @param name the name of parameter.
     * @return the parameter  associated with the given name or null.
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Retrieves names of all parameters in persistent parameters map.
     *
     * @return names of all parameters in persistent parameters map.
     */
    public Set<String> getPersistentParameterNames() {
        return Collections.unmodifiableSet(persistentParameters.keySet());
    }

    /**
     * Stores a value to persistent parameters map.
     *
     * @param name  the name.
     * @param value the value.
     */
    public void setPersistentParameter(String name, String value) {
        Objects.requireNonNull(name, "Name must not be null!");
        persistentParameters.put(name, value);
    }

    /**
     * Removes a value from persistent parameters map.
     *
     * @param name the name of parameter.
     * @throws NullPointerException if the name is a null reference.
     */
    public void removePersistentParameter(String name) {
        Objects.requireNonNull(name, "Name must not be  null.");
        persistentParameters.remove(name);
    }

    /**
     * Retrieves value from temporary parameters map (or null if no association exists)
     *
     * @param name the name.
     * @return values from temporary parameters map or null if no association exists.
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }

    /**
     * Retrieves names of all parameters in temporary parameters map.
     *
     * @return names of all parameters in temporary parameters map.
     */
    public Set<String> getTemporaryParameterNames() {
        return Collections.unmodifiableSet(temporaryParameters.keySet());
    }

    /**
     * Stores a value to temporary parameters map.
     *
     * @param name  the name.
     * @param value the value.
     * @throws NullPointerException if the name is a null reference.
     */
    public void setTemporaryParameter(String name, String value) {
        Objects.requireNonNull(name, "Name must not be null reference");
        temporaryParameters.put(name, value);
    }

    /**
     * Removes a value from temporary parameters map.
     *
     * @param name the name of parameter.
     * @throws NullPointerException if the name is a null reference.
     */
    public void removeTemporaryParameter(String name) {
        Objects.requireNonNull(name, "Name must not be null.");
        temporaryParameters.remove(name);
    }

    /**
     * Adds a new cookie to the list of all output cookies
     *
     * @param rcCookie the request context cookie.
     * @throws NullPointerException if the given cookie is a null reference.
     */
    public void addRCCookie(RCCookie rcCookie) {
        Objects.requireNonNull(rcCookie, "Cookie must not be null");
        outputCookies.add(rcCookie);
    }

    /**
     * Writes data to the output stream.
     *
     * @param data the data in bytes.
     * @return this reference.
     * @throws IOException if an error occurred while writing.
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(data);
        return this;
    }

    /**
     * Writes data to the output stream.
     *
     * @param text the data.
     * @return this reference.
     * @throws IOException if an error occurred while writing.
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(text.getBytes(encoding));
        return this;
    }

    /**
     * Writes data to the output stream.
     *
     * @param data   the data in bytes.
     * @param offset the offset.
     * @param len    the length of data.
     * @return this reference.
     * @throws IOException if an error occurred while writing.
     */
    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Generates the message header.
     *
     * @throws IOException if an error occurred while writing.
     */
    private void generateHeader() throws IOException {
        charset = Charset.forName(encoding);
        String sb = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                "Content-Type: " + mimeType + resolveMime() + "\r\n" +
                generateCookieRecords();
        if (contentLength != null) {
            sb += "Content-Length: " + contentLength + "\r\n";
        }
        sb += "\r\n";
        outputStream.write(sb.getBytes(charset));
        headerGenerated = true;

    }

    /**
     * Generates cookie records.
     *
     * @return cookie records.
     */
    private String generateCookieRecords() {
        if (outputCookies.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (RCCookie cookie : outputCookies) {
            sb.append("Set-Cookie: ");
            if (cookie.getValue() != null) {
                sb.append(cookie.getName()).append("=\"").append(cookie.getValue()).append("\"; ");
            }
            if (cookie.getDomain() != null) {
                sb.append("Domain=").append(cookie.getDomain()).append("; ");
            }
            if (cookie.getPath() != null) {
                sb.append("Path=").append(cookie.getPath()).append("; ");
            }
            if (cookie.getMaxAge() != null) {
                sb.append("Max-Age=").append(cookie.getMaxAge()).append("; ");
            }
            sb.replace(sb.toString().length() - 2, sb.toString().length(), "");

            if (cookie.isHttpOnly()) {
                sb.append("; HttpOnly");
            }
            sb.append("\r\n");

        }
        return sb.toString();
    }

    /**
     * Resolves mime type.
     *
     * @return the resolved mime type.
     */
    private String resolveMime() {
        if (mimeType.startsWith("text/")) {
            return "; charset=" + encoding;
        }
        return "";
    }
}
