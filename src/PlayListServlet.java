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
    Library myLibrary;
    Library allData;
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

    public String fetchContent(String cookieUserName) {
        String query = "SELECT songs.name as song, artists.name as artist, albums.name as album," +
                "songs.id as songID, artists.id as artistID, albums.id as albumID FROM songs " +
                "LEFT JOIN artists on songs.artist = artists.id " +
                "LEFT JOIN albums on songs.album = albums.id " +
                "JOIN playlists on playlists.song = songs.id " +
                "JOIN users on playlists.user = users.id " +
                "WHERE username='" + cookieUserName + "'";
        ArrayList<Song> songs = db.fetchSongs(query);

        StringBuilder sb = new StringBuilder();
        for (Song s: songs) {
            sb.append(s.toHTML(true));
        }
        return sb.toString();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        ArrayList<Song> allSongs = myLibrary.getSongs();
//        ArrayList<Song> dataSongs = allData.getSongs();
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
            String songIDStr = request.getParameter("songID");
            int songID = Integer.parseInt(songIDStr);
            String findQuery = "SELECT COUNT(*) as c, users.id as userID FROM playlists " +
                    "JOIN users on users.id = playlists.user " +
                    "WHERE username ='" + cookieUserName +
                    "' AND song=" + songID;
            boolean songExists = false;
            int userID = 0;
            try {
                ResultSet rs = db.query(findQuery);
                while (rs.next()) {
                    userID = rs.getInt("userID");
                    out.println(userID);
                    if (rs.getInt("c") > 0) {
                        songExists = true;
                    }
                }
                if (!songExists) {
                    String insertQuery = "INSERT INTO playlists (user, song) values (" + userID + ", " + songID + ")";
                    db.update(insertQuery);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            response.setContentType("text/html");
            out.println(getContent("src/playlist.html") + fetchContent(cookieUserName));
        }

//        List<String> list = Arrays.asList(names);
//        if (list.size() != 0) {
//            for (String song : list) {
//                for (Song s : dataSongs) {
//                    if (song.equals(s.getName()+s.getArtist().getName())) {
//                        if (!allSongs.contains(s)) {
//                            myLibrary.writeToFile(s.toString());
//                            allSongs.add(s);
//                        }
//                    }
//                }
//            }
//        }
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        StringBuilder sb = new StringBuilder();
//        out.println("<h2>" + message + "</h2>" + "<br>" );
//
//        for (Song s : allSongs) {
//
//            sb.append(s.toHTML(true));
//        }
//
//        out.println(getContent("src/playlist.html") + sb.toString()
//                + getContent("src/deleteRow.html"));
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
            out.println(getContent("src/playlist.html") + fetchContent(cookieUserName));
        }
    }

    public void destroy() {
        // do nothing.
    }
}
