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

public class HomePage extends HttpServlet {


    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Home Page";
    }

    public String getContent() {
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/homePage.html"));

            while (sc.hasNextLine()) {
                result += sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String un = request.getParameter("username");
        String pw = request.getParameter("password");
        String cookieVal = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("c1")) {
                cookieVal = c.getValue();
            }
        }
        PrintWriter out = response.getWriter();
        if (cookieVal.equals("fika") && pw.equals("1234")) {
            response.setContentType("text/html");
            String content = getContent();
            out.println(content);
        } else {
            out.println("<html><h2>Login Error<br></h2><p>The username or the password is invalid!</p></html>");
        }
    }

    public void destroy() {
        // do nothing.
    }
}
