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

public class DescriptionServlet extends HttpServlet {
    private String message;
    private IOManager db;

    public DescriptionServlet(IOManager db) {
        super();
        this.db = db;
    }

    public void init() throws ServletException {
        message = "Playlist";
    }

    public String getContent(String pathName) {
        StringBuilder result = new StringBuilder();
        try {
            Scanner sc = new Scanner(new File(pathName));

            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return result.toString();
    }

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
            String songIDStr = request.getParameter("descSongID");
            if (songIDStr != null) {
                String query = "SELECT songs.name as song, artists.name as artist, albums.name as album," +
                        "songs.id as songID, artists.id as artistID, albums.id as albumID FROM songs " +
                        "LEFT JOIN artists on songs.artist = artists.id " +
                        "LEFT JOIN albums on songs.album = albums.id " +
                        "WHERE songs.id='" + songIDStr + "'";
                ArrayList<Song> songs = db.fetchSongs(query, true);
                response.setContentType("text/html");
                StringBuilder sb = new StringBuilder();
                for (Song s: songs) {
                    sb.append(s.toHTML("description"));
                }
                out.println(getContent("src/description.html") + sb.toString());
            } else {
                out.println("<h2> Illegal access to this page</h2>");
            }
        }
    }

    public void destroy() {
        // do nothing.
    }
}
