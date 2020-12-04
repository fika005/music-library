package src;

import javax.print.DocFlavor;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Stage3 extends HttpServlet {


    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "This is the first page";
    }

    public String getContent() {
        return "this is a POST example";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String artist = request.getParameter("artist");
        String album = request.getParameter("album");
        response.setContentType("text/html");
        String resp = "<b>" + artist + "<br /><b>" + album + "</b>";
        PrintWriter out = response.getWriter();
        out.println(resp);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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