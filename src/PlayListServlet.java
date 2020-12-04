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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlayListServlet extends HttpServlet {
    private String message;
    Library myLibrary;
    Library allData;

    public void init() throws ServletException {
        // Do required initialization
        message = "Playlist";
        myLibrary = new Library();
        try {
            myLibrary.readFromFile("src/lib.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
            return;
        }
        allData = new Library();
        try {
            allData.readFromFile("src/data.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
            return;
        }

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
        ArrayList<Song> allSongs = myLibrary.getSongs();
        ArrayList<Song> dataSongs = allData.getSongs();

        String[] names = request.getParameterValues("selection");

        List<String> list = Arrays.asList(names);
        if (list.size() != 0) {
            for (String song : list) {
                for (Song s : dataSongs) {
                    if (song.equals(s.getName()+s.getArtist().getName())) {
                        if (!allSongs.contains(s)) {
                            myLibrary.writeToFile(s.toString());
                            allSongs.add(s);
                        }
                    }
                }
            }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        out.println("<h2>" + message + "</h2>" + "<br>" );

        for (Song s : allSongs) {

            sb.append(s.toHTML());
        }

        out.println(getContent("src/playlist.html") + sb.toString()
                + getContent("src/deleteRow.html"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Song> allSongs = myLibrary.getSongs();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        out.println("<h2>" + message + "</h2>" + "<br>" );
        for (Song s : allSongs) {

            sb.append(s.toHTML());
        }
        out.println(getContent("src/playlist.html") + sb.toString()
                + getContent("src/deleteRow.html"));
    }

    public void destroy() {
        // do nothing.
    }
}
