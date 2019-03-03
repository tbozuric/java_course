package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * This class represents a simple HTTP server.
 */
public class SmartHttpServer {

    /**
     * The address of the server.
     */
    private String address;

    /**
     * The domain name.
     */
    private String domainName;

    /**
     * The port on which the server is listening to the requests.
     */
    private int port;

    /**
     * The number of worker threads.
     */
    private int workerThreads;

    /**
     * The session timeout.
     */
    private int sessionTimeout;

    /**
     * The map of mime types.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types">Mime types</a>
     */
    private Map<String, String> mimeTypes = new HashMap<>();

    /**
     * The map of web workers.
     */
    private Map<String, IWebWorker> workersMap = new HashMap<>();

    /**
     * The server thread.
     */
    private ServerThread serverThread;

    /**
     * The thread pool for processing requests.
     */
    private ExecutorService threadPool;

    /**
     * The path to the root.
     */
    private Path documentRoot;

    /**
     * The map of sessions. Each session lasts for 10 minutes and is then removed(
     * if there was no activity at that time).
     */
    private Map<String, SessionMapEntry> sessions = new HashMap<>();

    /**
     * The session identifier generator.
     */
    private Random sessionRandom = new Random();


    /**
     * Creates an instance of {@link SmartHttpServer}.
     * The configuration is made with the data recorded in the file.
     *
     * @param configFileName the name of configuration file.
     */
    public SmartHttpServer(String configFileName) {
        init(configFileName);
        initTimerTask();
    }

    /**
     * Represents a thread(timer task) that cleans sessions every 5 minutes to prevent accumulating
     * unnecessary data in the memory.
     */
    private void initTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                sessions = sessions.entrySet().stream().filter(s -> s.getValue().validUntil > getCurrentTime())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        };
        Timer t = new Timer(true);
        t.scheduleAtFixedRate(timerTask, 0, 300_000);
    }

    /**
     * Returns the current time in seconds.
     *
     * @return the current time in seconds,
     */
    private long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Initiates server settings ie :sets the server address, domain name, port , number of worker threads,
     * session timeout, document root, server thread ,  loads available mime types...
     *
     * @param configFileName the name of configuration file.
     */
    private void init(String configFileName) {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Paths.get("config", configFileName)));
            this.address = properties.getProperty("server.address");
            this.domainName = properties.getProperty("server.domainName");
            this.port = Integer.valueOf(properties.getProperty("server.port"));
            this.workerThreads = Integer.valueOf(properties.getProperty("server.workerThreads"));
            this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
            this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
            String mime = properties.getProperty("server.mimeConfig");
            String workersPath = properties.getProperty("server.workers");
            parseWorkers(workersPath);
            properties.clear();
            properties.load(Files.newInputStream(Paths.get(mime)));
            properties.forEach((k, v) -> mimeTypes.put((String) k, (String) v));
            this.serverThread = new ServerThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads workers to the map of all available workers.
     *
     * @param workersPath the path to file with workers.
     * @see hr.fer.zemris.java.webserver.workers.Home
     * @see hr.fer.zemris.java.webserver.workers.BgColorWorker
     * @see hr.fer.zemris.java.webserver.workers.EchoParams
     * @see hr.fer.zemris.java.webserver.workers.HelloWorker
     * @see hr.fer.zemris.java.webserver.workers.SumWorker
     */
    private void parseWorkers(String workersPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(workersPath).toString()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                String path = parts[0].trim();
                String fqcn = parts[1].trim();
                if (workersMap.get(path) != null) {
                    throw new IllegalStateException("Worker was previously defined.");
                }
                IWebWorker iww = getIWebWorker(fqcn);
                workersMap.put(path, iww);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException
                | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and creates a new instance of workers.
     *
     * @param fqcn the fully qualified class name
     * @return a new instance of {@link IWebWorker}
     * @throws ClassNotFoundException if an error occurred while loading the class.
     * @throws InstantiationException if an error occurred while instantiation.
     * @throws IllegalAccessException if an error occurred while accessing.
     */
    private IWebWorker getIWebWorker(String fqcn) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
        Object newObject = referenceToClass.newInstance();
        return (IWebWorker) newObject;
    }

    /**
     * Creates a thread pool listening to client requests and starts a server thread.
     */
    protected synchronized void start() {
        threadPool = Executors.newFixedThreadPool(workerThreads, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        if (!serverThread.isAlive()) {
            serverThread.run();
        }
    }

    /**
     * Stops the server.
     */
    protected synchronized void stop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                threadPool.shutdown();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The thread which starts when server is opened.
     */
    protected class ServerThread extends Thread {

        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress((InetAddress) null, port));
                while (true) {
                    Socket client = serverSocket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Represents a job that must be done when a client submits a request to the server.
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * The client socket.
         */
        private Socket csocket;

        /**
         * The input stream.
         */
        private PushbackInputStream istream;

        /**
         * The output stream.
         */
        private OutputStream ostream;

        /**
         * The HTTP version.
         */
        private String version;

        /**
         * The method. The currently supported method is GET(for now).
         *
         * @see <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html">Methods</a>
         */
        private String method;

        /**
         * The host.
         */
        private String host;

        /**
         * The map of parameters.
         */
        private Map<String, String> params = new HashMap<>();

        /**
         * The map of temporary parameters.
         */
        private Map<String, String> tempParams = new HashMap<>();

        /**
         * The map of permanent parameters.
         */
        private Map<String, String> permParams = new HashMap<>();

        /**
         * The list of output cookies.
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();

        /**
         * The session identifier(20 characters long string).
         */
        private String SID;

        /**
         * The request context.
         */
        private RequestContext context = null;

        /**
         * The current time in seconds.
         */
        private long currentTime;

        /**
         * Creates an instance of {@link ClientWorker}.
         *
         * @param csocket the client socket.
         */
        public ClientWorker(Socket csocket) {
            this.csocket = csocket;
        }

        @Override
        public void run() {
            try {
                istream = new PushbackInputStream(csocket.getInputStream());
                ostream = new BufferedOutputStream(csocket.getOutputStream());

                List<String> request = readRequest();
                if (request == null || request.size() == 0) {
                    sendError(400, "Bad request");
                    return;
                }

                List<String> headers = extractHeaders(request);

                String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
                if (firstLine == null || firstLine.length != 3) {
                    sendError(400, "Bad request");
                    return;
                }

                method = firstLine[0].toUpperCase();
                if (!method.equals("GET")) {
                    sendError(405, "Method Not Allowed");
                    return;
                }

                version = firstLine[2].toUpperCase();
                if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
                    sendError(505, "HTTP Version Not Supported");
                    return;
                }

                String requestedPath = firstLine[1];

                Optional<String> host = headers.stream().filter(s -> s.toUpperCase().startsWith("HOST:")).findAny();
                if (host.isPresent()) {
                    String hostStr = host.get();
                    hostStr = hostStr.substring(hostStr.indexOf(":") + 1);
                    if (hostStr.contains(":")) {
                        this.host = hostStr.substring(0, hostStr.indexOf(":"));
                    } else {
                        this.host = hostStr;
                    }
                } else {
                    this.host = domainName;
                }

                String path;
                String paramString = null;

                checkSession(headers);

                String[] parts = requestedPath.split("\\?");
                if (parts.length == 1) {
                    path = requestedPath;
                } else {
                    path = parts[0];
                    paramString = parts[1];
                }
                if (paramString != null) {
                    parseParameters(paramString);
                }

                internalDispatchRequest(path, true);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    csocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        /**
         * Checks if there is a valid cookie in the request and if it does not exist
         * , it creates a new session and otherwise restores the current.
         *
         * @param headers the list of headers.
         */
        private synchronized void checkSession(List<String> headers) {
            String sidCandidate = null;

            for (String header : headers) {
                if (header.startsWith("Cookie: ")) {
                    String temp = header.substring(header.indexOf(":") + 1).trim();
                    String items[] = temp.split(";");
                    for (String keyValue : items) {
                        String[] parts = keyValue.split("=");
                        if (parts[0].equals("sid")) {
                            sidCandidate = parts[1].substring(1, parts[1].length() - 1);
                            break;
                        }
                    }
                }
            }
            if (sidCandidate == null) {
                createSession();

            } else {
                SessionMapEntry entry = sessions.get(sidCandidate);
                if (entry == null || !entry.host.equals(this.host)) {
                    createSession();
                    return;
                } else if (entry.validUntil < getCurrentTime()) {
                    sessions.remove(sidCandidate);
                    createSession();
                    return;
                } else {
                    currentTime = getCurrentTime();
                    entry.validUntil = currentTime + sessionTimeout;
                }
                permParams = entry.map;
                SID = sidCandidate;
            }
        }

        /**
         * Creates a new session.
         */
        private synchronized void createSession() {
            this.SID = generateSessionId();
            SessionMapEntry entry = new SessionMapEntry(SID, host, getCurrentTime() + sessionTimeout,
                    new ConcurrentHashMap<>());

            sessions.put(SID, entry);

            RequestContext.RCCookie cookie = new RequestContext.RCCookie("sid", this.SID,
                    null, host, "/", true);
            outputCookies.add(cookie);
            permParams = entry.map;
        }

        /**
         * Generates session ID as a 20-character long string of capital letters.
         *
         * @return the generated session id.
         */
        private synchronized String generateSessionId() {
            String alphabet = "QWERTZUIOPLKJHGFDSAYXCVBNM";
            StringBuilder sid = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                sid.append(alphabet.charAt(sessionRandom.nextInt(alphabet.length())));
            }
            return sid.toString();
        }

        /**
         * Determines the mime type from the given name.
         * If it is not possible to determine the type of method returns "application/octet-stream".
         *
         * @param name the name of type content.
         * @return the mime type from the given name.
         */
        private String determineMime(String name) {
            name = name.substring(name.lastIndexOf(".") + 1);
            return mimeTypes.get(name) != null ? mimeTypes.get(name) : "application/octet-stream";
        }

        /**
         * Parses the query string.
         *
         * @param paramString the query string.
         */
        private void parseParameters(String paramString) {
            String[] keyValues = paramString.split("&");
            for (String keyValue : keyValues) {
                if (keyValue.endsWith("=")) {
                    params.put(keyValue.substring(0, keyValue.indexOf("=")), "");
                    continue;
                }
                String[] parameters = keyValue.split("=");
                if (parameters.length == 1) {
                    params.put(parameters[0], null);
                } else if (parameters.length == 2) {
                    params.put(parameters[0], parameters[1]);
                }
            }
        }

        /**
         * Reads the request from the client and returns it as a list of strings.
         *
         * @return the request from the client.
         * @throws IOException if an error occurred while reading client request.
         */
        private List<String> readRequest() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = istream.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10) {
                            state = 4;
                        }
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else {
                            state = 0;
                        }
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else {
                            state = 0;
                        }
                        break;
                    case 3:
                        if (b == 10) {
                            break l;
                        } else {
                            state = 0;
                        }
                        break;
                    case 4:
                        if (b == 10) {
                            break l;
                        } else {
                            state = 0;
                        }
                        break;
                }
            }
            String[] rows = new String(bos.toByteArray(), StandardCharsets.US_ASCII).split("\\n");
            return new ArrayList<>(Arrays.asList(rows));
        }

        /**
         * Sends a response to the client that a certain error has occurred.
         *
         * @param statusCode the status code.
         * @param statusText the status text.
         * @throws IOException if an error occurred while writing response.
         */
        private void sendError(
                int statusCode, String statusText) throws IOException {
            ostream.write(
                    ("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
                            "Server: simple java server\r\n" +
                            "Content-Type: text/plain;charset=UTF-8\r\n" +
                            "Content-Length: 0\r\n" +
                            "Connection: close\r\n" +
                            "\r\n").getBytes(StandardCharsets.US_ASCII)
            );
            ostream.flush();
        }

        /**
         * Extracts headers from one client request.
         *
         * @param request client request.
         * @return the extracted headers as a list of strings
         */
        private List<String> extractHeaders(List<String> request) {
            List<String> headers = new ArrayList<>();
            StringBuilder currentLine = new StringBuilder();
            for (String s : request) {
                if (s.isEmpty()) break;
                char c = s.charAt(0);
                if (c == 9 || c == 32) {
                    currentLine.append(s);
                } else {
                    if (currentLine.length() > 0) {
                        headers.add(currentLine.toString());
                    }
                    currentLine.delete(0, currentLine.toString().length());
                    currentLine.append(s);
                }
            }
            if (currentLine.length() > 0) {
                headers.add(currentLine.toString());
            }
            return headers;
        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        /**
         * The method processes client requests(represents "the heart" of our server).
         *
         * @param urlPath    the url path.
         * @param directCall flag indicating whether this is a direct call.
         * @throws Exception if an error occurred while processing client request.
         */
        private void internalDispatchRequest(String urlPath, boolean directCall)
                throws Exception {

            if (context == null) {
                context = new RequestContext(ostream, params, permParams,
                        outputCookies, tempParams, this);
            }

            if (urlPath.startsWith("/private") && directCall) {
                sendError(404, "File not found.");
                return;
            }


            if (urlPath.startsWith("/ext/")) {
                String worker = urlPath.substring(urlPath.lastIndexOf("/") + 1);
                try {
                    getIWebWorker("hr.fer.zemris.java.webserver.workers." + worker).processRequest(context);
                } catch (Exception e) {
                    sendError(404, "File not found");
                }
                ostream.flush();
                return;

            }

            if (workersMap.containsKey(urlPath)) {
                workersMap.get(urlPath).processRequest(context);
                ostream.flush();
                return;
            }

            Path requestedFile = documentRoot.resolve(urlPath.substring(1));

            if (!requestedFile.startsWith(documentRoot)) {
                sendError(404, "File not found");
                return;
            }

            if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
                sendError(404, "File not found");
                return;
            }

            if (urlPath.endsWith(".smscr")) {
                String documentBody;
                documentBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
                new SmartScriptEngine(
                        new SmartScriptParser(documentBody).getDocumentNode(),
                        context
                ).execute();
                ostream.flush();

            } else {
                String mimeType = determineMime(requestedFile.getFileName().toString());
                context.setMimeType(mimeType);
                context.setStatusCode(200);
                if (mimeTypes.values().contains(mimeType)) {
                    context.setContentLength(Files.size(requestedFile));
                }
                try (InputStream fis = Files.newInputStream(requestedFile)) {
                    byte[] buf = new byte[1024];
                    while (true) {
                        int r = fis.read(buf);
                        if (r < 1) {
                            break;
                        }
                        context.write(buf, 0, r);
                    }
                }
                ostream.flush();
            }
        }
    }

    /**
     * Represents an session map entry.
     */
    private static class SessionMapEntry {

        /**
         * The session identifier.
         */
        private String sid;

        /**
         * The host.
         */
        private String host;

        /**
         * Time in seconds indicating how long the session lasts.
         */
        private long validUntil;

        /**
         * The map of client data.
         */
        private Map<String, String> map;

        /**
         * Creates a new instance of {@link SessionMapEntry}.
         *
         * @param sid        the session identifier.
         * @param host       the host.
         * @param validUntil time in seconds indicating how long the session lasts.
         * @param map        the client data.
         */
        public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
            this.sid = sid;
            this.host = host;
            this.validUntil = validUntil;
            this.map = map;
        }
    }

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        new SmartHttpServer("server.properties").start();
    }
}
