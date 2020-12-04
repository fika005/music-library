package src;/* This class represents a Song */

public class Song extends Entity {
    String filename;
    Artist artist;
    int timesPlayed;
    String runningTime;

    /* you complete this */
    public Song() {
        filename = "";
        artist = new Artist();
        timesPlayed = 0;
        runningTime = "";
    }

    /* you complete this */
    public Song(String title, String filename, Artist artist, int timesPlayed, String runningTime) {
        super(title);
        this.filename = filename;
        this.artist = artist;
        this.timesPlayed = timesPlayed;
        this.runningTime = runningTime;
    }

    /* add setters and getters */

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

    /* you complete this */
    public String toString() {
        String songOutput =
                "Song name: " + name + "; Artist: " + artist.getName() + "; Time played: " +
                        timesPlayed + "; Running time: " + runningTime;
        return songOutput;
    }
    public String toHTML() {
        return "<tr><td> " + name + "    </td><td> " + artist.getName() + "    </td><td> " +
                        timesPlayed + "    </td><td> " + runningTime  + "<td><input type=\"button\" " +
                "value=\"Delete\" onclick=\"deleteRow(this)\"></td>\n" +   "</td></tr>";
    }

    /* you complete this. Assume that two songs are equal if they have the same name and the same artist. */
    public boolean equals(Song otherSong) {
        if (this.name.equals(otherSong.name) && this.artist.equals(otherSong.artist)) {
            return true;
        } else {
            return false;
        }
    }

}
