package src;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.security.*;

public class HomePage extends HttpServlet {


    private String message;
    private IOManager db;

    public HomePage(IOManager db) {
        super();
        this.db = db;
    }

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
    private static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
    private String hash(String pw) {
        byte[] bytesOfPW = pw.getBytes(StandardCharsets.UTF_8);
        byte[] pwHashedBytes = {};
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            pwHashedBytes = md.digest(bytesOfPW);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return byteArrayToHex(pwHashedBytes);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String un = request.getParameter("username");
        String pw = request.getParameter("password");
        String name = request.getParameter("name");
        if (name != null) {
            try {
                int count = -1;
                ResultSet rs = db.query("SELECT COUNT(*) AS C FROM users WHERE username='" + un + "'");
                while (rs.next()) {
                    count = rs.getInt("C");
                }
                if (count == 0) {
                    String pwHashed = hash(pw);
                    db.update("INSERT INTO USERS (username, password) values ('" + un + "', '" + pwHashed + "')");
                    response.sendRedirect("/login");
                } else {
                    out.println("<html><h2>Login Error<br></h2><p>The username already exists!</p></html>");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Cookie[] cookies = request.getCookies();
            String cookieVal = "";
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    cookieVal = c.getValue();
                }
            }
            String actualPassword = "";
            boolean userExists = false;
            int userID = 0;
            try {
                ResultSet rs = db.query("SELECT * FROM users where username='" + un + "'");
                while (rs.next()) {
                    actualPassword = rs.getString("password");
                    userID = rs.getInt("id");
                    userExists = true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            if (userExists && hash(pw).equals(actualPassword)) {
                Cookie cookie1 = new Cookie("username", un);
                Cookie cookie2 = new Cookie("userID", Integer.toString(userID));
                cookie1.setMaxAge(600);
                cookie2.setMaxAge(600);
                response.addCookie(cookie1);
                response.addCookie(cookie2);
                response.setContentType("text/html");
                String content = getContent();
                out.println(content);
            } else {
                response.sendRedirect("/signup");
            }
        }
//        String un = request.getParameter("username");
//        String pw = request.getParameter("password");
//        String cookieVal = "";
//        Cookie[] cookies = request.getCookies();
//        for (Cookie c : cookies) {
//            if (c.getName().equals("c1")) {
//                cookieVal = c.getValue();
//            }
//        }
//        PrintWriter out = response.getWriter();
//        if (cookieVal.equals("fika") && pw.equals("1234")) {
//            response.setContentType("text/html");
//            String content = getContent();
//            out.println(content);
//        } else {
//            out.print("Sorry UserName or Password Error!");
////            request.getSession(true).setAttribute("name", "Hello world");
//            response.sendRedirect("/login");
////            RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher("/login");
////            dispatcher.forward(request, response);
////            RequestDispatcher rd=request.getRequestDispatcher("login");
////            rd.forward(request, response);
////            out.println("<html><h2>Login Error<br></h2><p>The username or the password is invalid!</p></html>");
//        }
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
        if (cookieUserName.equals("")) {
            response.sendRedirect("/login");
        } else {
            response.setContentType("text/html");
            String content = getContent();
            out.println(content);
        }
    }

    public void destroy() {
        // do nothing.
    }
}
