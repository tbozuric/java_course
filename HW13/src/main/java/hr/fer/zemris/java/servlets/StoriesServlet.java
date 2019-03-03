//package hr.fer.zemris.java.servlets;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Random;
//
///**
// * Represents a page that contains some not too long but funny story. The color of the font
// * used for stories text must be each time randomly chosen (you can randomly pick some color from
// * predefined selection of colors). This action is accessible on local URL /stories.
// */
//@WebServlet("/stories")
//public class StoriesServlet extends HttpServlet {
//
//    /**
//     * The default serial version UID.
//     */
//    private static final long serialVersionUID = -6066629233634943723L;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String[] textColors = {"Tomato", "Orange", "DodgerBlue", "Gray", "Violet", "MediumSeaGreen"};
//        int random = new Random().nextInt(textColors.length);
//        String textColor = textColors[random];
//        req.setAttribute("color", textColor);
//        req.getRequestDispatcher("/WEB-INF/pages/stories/funny.jsp").forward(req, resp);
//    }
//}
