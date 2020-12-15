package src;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * The servlet to handle showallsongs page
 */
public class ShowAllSongsServlet extends HttpServlet {


    private String message;
    private IOManager db;

    public ShowAllSongsServlet(IOManager db) {
        super();
        this.db = db;
    }

    public void init() throws ServletException {
        // Do required initialization
        message = "Show All Songs";
    }

    public String getContent() {
        String result = "";
        try {
            Scanner sc = new Scanner(new File("src/songs.html"));

            while (sc.hasNextLine()) {
                result += sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result;
    }
    /**
     * The main function to handle the get requests to this servlet. Query the database for all relevant info
     * on songs and show the results.
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        PrintWriter out = response.getWriter();
        String cookieUserName = "";
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    cookieUserName = c.getValue();
                }
            }
        }
        if (cookieUserName.equals("")) {
            response.sendRedirect("/login");
        } else {
            String query = "SELECT songs.name as song, artists.name as artist, albums.name as album," +
                    "songs.id as songID, artists.id as artistID, albums.id as albumID FROM songs " +
                    "LEFT JOIN artists on songs.artist = artists.id " +
                    "LEFT JOIN albums on songs.album = albums.id";
            ArrayList<Song> songs = db.fetchSongs(query, false);
            response.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            for (Song s: songs) {
                sb.append(s.toHTML("allSongs"));
            }
            out.println(getContent() + sb.toString());
        }
    }

    public void destroy() {
        // do nothing.
    }
}
