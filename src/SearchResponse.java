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

public class SearchResponse extends HttpServlet{
    private String message;
    Library myLibrary;

    public void init() throws ServletException {
        // Do required initialization
        message = "Search Result";
        myLibrary = new Library();
        try {
            myLibrary.readFromFile("src/data.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
            return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<h2>" + message + "</h2>" + "<br>" );
        String inputParameter = request.getParameter("action");
        StringBuilder resp = new StringBuilder();
        ArrayList<Album> albums = myLibrary.albums;
        ArrayList<Song> songs = myLibrary.songs;
        ArrayList result = new ArrayList();
        boolean check = false;
        if (inputParameter.equals("Album Search")) {
            for (Album a : albums) {
                if (a.name.toLowerCase().equals(request.getParameter("input").toLowerCase())) {
                    check = true;
                    resp.append("<p> <b> Album's name: " + a.name + "</p><b>" + "<p> <b> Album's artist: "
                            + a.artist.getName() + "</p><b>" + "<p> <b>" + "list of album's songs: "
                            + "</p> </b>" + "<form action='/playlist' method=\"Post\">");
                    for (Song s : a.getSongs()) {
                        String songValue = s.name + a.getArtist().name;
                        resp.append("<input type=\"checkbox\""
                                + "name=\"selection\"" + "value=\"" + songValue + "\">"
                                + "<label for=" + s.name + ">" + s.name
                                + "</label><br>");
                    }
                }
            }
            if (check) {
                resp.append("<br><input type=\"submit\" value=\"Add to Playlist\">\n" + "</form> ");
            } else {
                out.println("<p> The album name <strong><i>" + request.getParameter("input").toLowerCase() + " </strong></i>does not exist.<p>");
            }
        } else if (inputParameter.equals("Song Search")) {

            for (Song a : songs) {
                String songValue = a.name+a.getArtist().getName();
                if (a.name.toLowerCase().equals(request.getParameter("input").toLowerCase())) {
                    if (!check) {
                        resp.append( "<p><b> Song name: " + request.getParameter("input") + "</p><b>" +
                                "<p> <b>" + "list of songs with the entered name: "+ "</p> </b>"
                                + "<form action='/playlist' method=\"Post\">");
                    }
                    check = true;
                    resp.append("<input type=\"checkbox\""
                            + "name=\"selection\"" + "value=\"" + songValue  +  "\">"
                            + "<label for=" + a.name + ">" + a.name + ",  " + a.getArtist().getName()
                            + "</label><br>");
                }
            }
            if (check) {
                resp.append("<br><input type=\"submit\" value=\"Add to Playlist\">\n" + "</form> ");

            } else {
                out.println("<p> The song name <strong><i>" + request.getParameter("input").toLowerCase() + " </strong></i>does not exist.<p>");
            }
        }
        response.setContentType("text/html");
        out.println(resp.toString());
    }

    public void destroy() {
        // do nothing.
    }
}


