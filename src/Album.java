package src;
import java.util.ArrayList;
/**
The class to represent an Album entity
 */
public class Album extends Entity {
    ArrayList<Song> songs;
    Artist artist;
    String totalLength;

    public Album() {
        songs = new ArrayList<>();
        artist = new Artist();
        totalLength = "";
    }

    public Album(String n, ArrayList<Song> songs, Artist artist, String totalLength) {
        super(n);
        this.songs = songs;
        this.artist = artist;
        this.totalLength = totalLength;
    }


    public Album(String n) {
        super(n);
        songs = new ArrayList<Song>();
        artist = new Artist();
        totalLength = "";

    }


    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    /**
     * Turns the Album calss into a string representation
     * @return a string version
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Album: ");
        sb.append(getName());
        sb.append("; Artist: " + artist.getName() + "; Songs: ");
        for (Song s : songs){
            sb.append(s.name + ", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    /**
     * Represent the Album class in html format.
     * @return string of html representation
     */
    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr><td>" + getName() + "</td>");
        sb.append("<td>" + artist.getName() + "</td>");
        sb.append("<td><ul>");
        for (Song s : songs){
            sb.append("<li>" + s.name + "</li>");
        }
        sb.append("</ul></td></tr>");
        //sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    /**
     * turns the album into a sql insert query representation
     * @return string of sql query
     */
    public String toSQL() {
        return "INSERT INTO albums values (" + "\"" + getName()  + ")";
    }

    /**
     * checks whether the other album is equivalent to the current object
     * @param otherAlbum other album
     * @return true if they are equivalent
     */
    public boolean equals(Album otherAlbum) {
        if (this.name.equals(otherAlbum.getName()) && this.artist.name.equals(otherAlbum.artist.getName()) &&
        this.songs.containsAll(otherAlbum.getSongs()) && otherAlbum.getSongs().containsAll(this.songs)) {
            return true;
        } else {
            return false;
        }

    }


}
