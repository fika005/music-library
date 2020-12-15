package src;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class to handle all IO communication for our server
 */
public class IOManager {
    Connection connection = null;

    /**
     * makes the connection to the database
     * @throws SQLException
     */
    public IOManager() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/music.db");
    }

    /**
     * Given a query as string, queries the database
     * @param queryStr query in string format
     * @return result set of the query
     * @throws SQLException
     */
    public ResultSet query(String queryStr) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        return statement.executeQuery(queryStr);
    }

    /**
     * given a string query performs updates on the table
     * @param queryStr query as string
     * @throws SQLException
     */
    public void update(String queryStr) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);
        statement.executeUpdate(queryStr);
    }

    /**
     * calls the REST API to fetch a song description based on its name and artist name.
     * @param songName name of the song
     * @param artistName name of the artist
     * @return the description of the song if found otherwise null
     * @throws IOException
     * @throws ParseException
     */
    public String fetchDescription(String songName, String artistName) throws IOException, ParseException {
        String baseURL = "https://theaudiodb.com/api/v1/json/1/searchtrack.php?s=";
        String urlStr = baseURL + artistName.replace(" ", "%20") + "&t=" + songName.replace(" ", "%20");
        URL url = new URL(urlStr);
        String desc = "";
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {

            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(inline.toString());
            JSONArray arr = (JSONArray) data_obj.get("track");
            JSONObject js = (JSONObject) arr.get(0);
            desc = (String) js.get("strDescriptionEN");
        }
        return desc;
    }

    /**
     * Given a query to fetch songs, query the appropriate tables and return an arraylist of song objects.
     * @param queryStr query to use
     * @param desc whether to fetch description.
     * @return array list of found songs in the database with album and artist info.
     */
    public ArrayList<Song> fetchSongs(String queryStr, boolean desc) {
        String songName = "";
        String artistName = "";
        String albumName = "";
        String description = "";
        int artistID = 0;
        int albumID = 0;
        int songID = 0;
        HashMap<Integer, Artist> artists = new HashMap<>();
        HashMap<Integer, Album> albums = new HashMap<>();
        ArrayList<Song> songs = new ArrayList<>();
        try {
            ResultSet rs = this.query(queryStr);
            while (rs.next()) {
                songName = rs.getString("song");
                artistName = rs.getString("artist");
                albumName = rs.getString("album");
                songID = rs.getInt("songID");
                artistID = rs.getInt("artistID");
                albumID = rs.getInt("albumID");
                Album album;
                Artist artist;
                if (artists.containsKey(artistID)) {
                    artist = artists.get(artistID);
                } else {
                    artist = new Artist(artistName);
                    artists.put(artistID, artist);
                }
                if (albums.containsKey(albumID)) {
                    album = albums.get(albumID);
                } else {
                    album = new Album(albumName);
                    albums.put(albumID, album);
                }
                if (desc) {
                    description = fetchDescription(songName, artistName);
                }
                Song song = new Song(songName, artist, album, songID, description);
                songs.add(song);
            }
        } catch (SQLException | ParseException | IOException throwables) {
            throwables.printStackTrace();
        }
        return songs;
    }
}
