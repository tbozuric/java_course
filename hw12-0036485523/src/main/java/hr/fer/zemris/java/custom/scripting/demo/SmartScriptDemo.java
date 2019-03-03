package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple demonstration program for testing  {@link SmartScriptEngine}.
 */
public class SmartScriptDemo {

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments.  The method expects one parameter, the path to the file we want to parse.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter the path of the file as an argument.");
            return;
        }
        String documentBody = null;
        try {
            documentBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Unable to read file.");
            System.exit(-1);
        }

        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        // create engine and execute it
        //parameters.put("a", "4");
        //parameters.put("b", "2");
        new SmartScriptEngine(
                new SmartScriptParser(documentBody).getDocumentNode(),
                new RequestContext(System.out, parameters, persistentParameters, cookies)
        ).execute();
    }
}