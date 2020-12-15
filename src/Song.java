package src;
/**
 * The class that holds songs information
 */
public class Song extends Entity {
    String filename;
    Artist artist;
    int timesPlayed;
    String runningTime;
    Album album;
    int id;
    String description;

    public Song() {
        filename = "";
        artist = new Artist();
        timesPlayed = 0;
        runningTime = "";
        album = new Album();
    }

    public Song(String title, String filename, Artist artist, Album album, int timesPlayed, String runningTime) {
        super(title);
        this.filename = filename;
        this.artist = artist;
        this.album = album;
        this.timesPlayed = timesPlayed;
        this.runningTime = runningTime;
    }

    public Song(String title, Artist artist, Album album, int id, String description) {
        super(title);
        this.artist = artist;
        this.album = album;
        this.id = id;
        this.description = description;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(int timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * represent the song info in string format
     * @return song info in string
     */
    public String toString() {
        String songOutput =
                "Song name: " + name + "; Artist: " + artist.getName() + "; Time played: " +
                        timesPlayed + "; Running time: " + runningTime;
        return songOutput;
    }

    /**
     * depending on where we want to show a song info, we turn a song into HTML format
     * @param page which page the song will be shown
     * @return
     */
    public String toHTML(String page) {
        String htm = "<tr><td> " + name + "    </td><td> " + artist.getName() +
                "    </td><td> " + album.getName() + "    </td><td> ";
        if (page =="description") {
            htm += description + "</td>";
        } else {
            htm += "<form action=\"/description\" method=\"POST\"><button name=\"descSongID\" value=\"" + id +
                    "\" type=\"submit\">Show Description</button></form></td>\n";
        }
        if (page.equals("playlist")) {
            htm += "<td><form action=\"/playlist\" method=\"POST\"><button name=\"DeleteSongID\" value=\"" + id +
                    "\" type=\"submit\">Delete</button></form></td>\n";
        } else if (page.equals("allSongs")) {
            htm += "<td><form action=\"/playlist\" method=\"POST\"><button name=\"AddSongID\" value=\"" + id +
                    "\" type=\"submit\">Add</button></form></td>\n";
        }
        htm += "</tr>";
        return htm;
    }

    /**
     * turns a song into an insert query into playlist table
     * @param user for which user this song is added to playlist
     * @return query
     */
    public String toSQLPlaylist(int user) {
        return "INSERT INTO playlists (user, song) values (" + user + ", " + this.id  + ")";
    }

    public boolean equals(Song otherSong) {
        if (this.name.equals(otherSong.name) && this.artist.equals(otherSong.artist)) {
            return true;
        } else {
            return false;
        }
    }
}
