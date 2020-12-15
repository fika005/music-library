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
/**
 * The servlet to handle search page
 */
public class SearchServlet extends HttpServlet {
    private String message;

    public void init() throws ServletException {
        // Do required initialization
        message = "Search Page";
    }

    public String getContent() {
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/search.html"));
            while (sc.hasNextLine()) {
                result += sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result;
    }
    /**
     * The main function to handle the get requests to this servlet
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        String cookieUserID = "";
        for (Cookie c : cookies) {
            if (c.getName().equals("userID")) {
                cookieUserID = c.getValue();
            }
        }
        if (cookieUserID.equals("")) {
            response.sendRedirect("/login");
        } else {
            response.setContentType("text/html");
            out.println("<h2>" + message + "</h2>" + "<br>");
            String content = getContent();
            out.println(content);
        }
    }

    public void destroy() {
        // do nothing.
    }
}

