package src;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LoginServlet extends HttpServlet {
    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Login Page";
    }

    public String getContent() {
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/login.html"));
            while (sc.hasNextLine()) {
                result += sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        String cookieUserName = "";
        for (Cookie c : cookies) {
            if (c.getName().equals("username")) {
                cookieUserName = c.getValue();
            }
        }
        if (!cookieUserName.equals("")) {
            response.sendRedirect("/homePage");
        } else {
            response.setContentType("text/html");
            String content = getContent();
            //out.println("<h2>" + message + "</h2> <br>"); don't need this anymore
            out.println(content);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
//        String message = (String) request.getSession().getAttribute("name");
        PrintWriter out = response.getWriter();
        out.println("<h2>" + "wrong" + "</h2> <br>");
        String content = getContent();
        out.println(content);
    }

    public void destroy() {
        // do nothing.
    }
}
