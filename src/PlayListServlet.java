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
import java.util.*;

public class PlayListServlet extends HttpServlet {
    private String message;
    private IOManager db;

    public PlayListServlet(IOManager db) {
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

    public String fetchContent(String cookieUserID) {
        String query = "SELECT songs.name as song, artists.name as artist, albums.name as album," +
                "songs.id as songID, artists.id as artistID, albums.id as albumID FROM songs " +
                "LEFT JOIN artists on songs.artist = artists.id " +
                "LEFT JOIN albums on songs.album = albums.id " +
                "JOIN playlists on playlists.song = songs.id " +
                "WHERE user=" + cookieUserID;
        ArrayList<Song> songs = db.fetchSongs(query, false);

        StringBuilder sb = new StringBuilder();
        for (Song s: songs) {
            sb.append(s.toHTML("playlist"));
        }
        return sb.toString();
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
            String songIDStr = request.getParameter("AddSongID");
            if (songIDStr != null) {
                int songID = Integer.parseInt(songIDStr);
                String findQuery = "SELECT COUNT(*) as c FROM playlists " +
                        "WHERE user =" + cookieUserID + " AND song=" + songID;
                boolean songExists = false;
                try {
                    ResultSet rs = db.query(findQuery);
                    while (rs.next()) {
                        if (rs.getInt("c") > 0) {
                            songExists = true;
                        }
                    }
                    if (!songExists) {
                        String insertQuery = "INSERT INTO playlists (user, song) values (" + cookieUserID + ", " + songID + ")";
                        db.update(insertQuery);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                songIDStr = request.getParameter("DeleteSongID");
                int songID = Integer.parseInt(songIDStr);
                try {
                    String deleteQuery = "DELETE FROM playlists WHERE user=" + cookieUserID + " AND song=" + songID;
                    db.update(deleteQuery);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            response.setContentType("text/html");
            out.println(getContent("src/playlist.html") + fetchContent(cookieUserID));
        }

    }

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
            out.println(getContent("src/playlist.html") + fetchContent(cookieUserID));
        }
    }

    public void destroy() {
        // do nothing.
    }
}
