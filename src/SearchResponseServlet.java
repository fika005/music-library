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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * The servlet to handle searchresponse page
 */
public class SearchResponseServlet extends HttpServlet{
    private String message;
    private IOManager db;

    public SearchResponseServlet(IOManager db) {
        super();
        this.db = db;
    }

    public void init() throws ServletException {
        message = "Search Result";
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
    /**
     * The main function to handle the post requests to this servlet, based on album or artist search, query the
     * database and show the result
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
            String inputParameter = request.getParameter("action");
            StringBuilder resp = new StringBuilder();
            ArrayList result = new ArrayList();
            boolean check = false;
            if (inputParameter.equals("Album Search")) {
                String albumName = request.getParameter("input");
                ArrayList<String> songNames = new ArrayList<>();
                ArrayList<String> artistNames = new ArrayList<>();
                String query = "SELECT artists.name as artistName, songs.name as songName  FROM albums " +
                        "LEFT JOIN artists on artists.id = albums.artist " +
                        "LEFT JOIN songs on songs.album = albums.id " +
                        "WHERE albums.name='" + albumName + "'";
                ResultSet rs = null;
                try {
                    rs = db.query(query);
                    while (rs.next()) {
                        check = true;
                        artistNames.add(rs.getString("artistName"));
                        songNames.add(rs.getString("songName"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (check) {
                    StringBuilder sb = new StringBuilder();
                    for (int i=0; i < songNames.size(); i ++) {
                        sb.append("<tr><td>").append(albumName).append("</td><td>").append(artistNames.get(i)).append(
                                "</td><td>").append(songNames.get(i)).append("</tr>");
                    }
                    out.println(getContent("src/album.html") + sb.toString());
                    out.println("<h3> Album <strong><i>" + albumName + " </strong></i> is found: " );
                } else {
                    out.println("<p> The album name <strong><i>" + albumName + " </strong></i>does not exist in the database.<p>");
                }
            } else if (inputParameter.equals("Artist Search")) {
                String artistName = request.getParameter("input");
                ArrayList<String> songNames = new ArrayList<>();
                ArrayList<String> albumNames = new ArrayList<>();
                String query = "SELECT albums.name as albumName, songs.name as songName FROM artists " + "LEFT JOIN albums on albums.artist = artists.id " + "LEFT JOIN songs on songs.album = albums.id " + "WHERE artists.name='" + artistName + "'";
                ResultSet rs = null;
                try {
                    rs = db.query(query);
                    while (rs.next()) {
                        check = true;
                        albumNames.add(rs.getString("albumName"));
                        songNames.add(rs.getString("songName"));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                if (check) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < songNames.size(); i++) {
                        sb.append("<tr><td>").append(artistName).append("</td><td>").append(albumNames.get(i)).append("</td><td>").append(songNames.get(i)).append("</tr>");
                    }
                    out.println(getContent("src/artists.html") + sb.toString());
                    out.println("<h3> Artist <strong><i>" + artistName + " </strong></i> is found:");
                } else {
                    out.println("<p> The artist name <strong><i>" + artistName + " </strong></i>does not exist in the database.<p>");
                }
            }
        }
    }

    public void destroy() {
        // do nothing.
    }
}


