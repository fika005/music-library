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

public class Stage2 extends HttpServlet {


    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "This is the first page";
    }

    public String getContent() {
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/input.html"));

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
        String cookieVal = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("Firoozeh")) {
                cookieVal = c.getValue();
            }
        }
        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String content = getContent();
        out.println(content);
    }

    public void destroy() {
        // do nothing.
    }
}