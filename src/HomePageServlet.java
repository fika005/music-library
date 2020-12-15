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

public class HomePageServlet extends HttpServlet {


    private String message;
    private IOManager db;

    public HomePageServlet(IOManager db) {
        super();
        this.db = db;
    }

    public void init() throws ServletException {
        // Do required initialization
        message = "Home Page";
    }

    public String getContent(String path) {
        String result = "";
        try {
            Scanner sc = new Scanner(new File(path));

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
        response.setContentType("text/html");
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
                    String message = "Your account was created, Please log in!";
                    out.println("<h2>" + message + "</h2> <br>");
                    out.println(getContent("src/login.html"));
                } else {
                    String message = "Username already exists, Please log in!";
                    out.println("<h2>" + message + "</h2> <br>");
                    out.println(getContent("src/login.html"));
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
                String content = getContent("src/homePage.html");
                out.println(content);
            } else {
                String message = "Username or password is incorrect, please try again!";
                out.println("<h2>" + message + "</h2> <br>");
                out.println(getContent("src/login.html"));
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String cookieUserName = "";
        for (Cookie c : cookies) {
            if (c.getName().equals("username")) {
                cookieUserName = c.getValue();
            }
        }
        if (cookieUserName.equals("")) {
            String message = "You are not logged in, Please log in!";
            out.println("<h2>" + message + "</h2> <br>");
            out.println(getContent("src/login.html"));
        } else {
            String content = getContent("src/homePage.html");
            out.println(content);
        }
    }

    public void destroy() {
        // do nothing.
    }
}
