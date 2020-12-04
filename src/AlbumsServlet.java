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
import java.util.ArrayList;
import java.util.Scanner;

public class AlbumsServlet extends HttpServlet {
    private String message;
    Library myLibrary;

    public void init() throws ServletException {
        message = "Albums";
        myLibrary = new Library();
        try {
            myLibrary.readFromFile("src/lib.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
            return;
        }
    }

    public String getContent() {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File("src/album.html"));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Album> albums = myLibrary.getAlbums();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        out.println("<h2>" + message + "</h2>" + "<br>" );
        for (Album a : albums) {
            sb.append(a.toHTML());
        }
        out.println(getContent() + sb.toString());
    }

    public void destroy() {
        // do nothing.
    }
}
