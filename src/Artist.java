package src;
import java.util.ArrayList;

/**
 * The class to represent an artist
 */
public class Artist extends Entity {
    ArrayList<Song> songs;
    ArrayList<Album> albums;

    public Artist() {
        songs = new ArrayList<>();
        albums = new ArrayList<>();
    }

    public Artist(String name) {
        super(name);
        songs = new ArrayList<>();
        albums = new ArrayList<>();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * string representation of an artist entity
     * @return string of the representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Artist: ");
        sb.append(getName());
        sb.append("; Songs: ");
        for (Song s : songs){
            sb.append(s.name + ", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("; Albums: ");
        for (Album a : albums){
            sb.append(a.name + ", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    /**
     * html representation of artist
     * @return string of html representation
     */
    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr><td>" + getName() + "</td>");
        sb.append("<td><ul>");
        for (Song s : songs){
            sb.append("<li>" + s.name + "</li>");
        }
        sb.append("</ul></td>");
        sb.append("<td><ul>");
        for (Album a : albums){
            sb.append("<li>" + a.name + "</li>");
        }
        sb.append("</ul></td></tr>");
        return sb.toString();
    }

    /**
     * Turns the artist info into a sql insert query
     * @return string version of insert query
     */
    public String toSQL() {
        return "INSERT INTO artists values (" + "\"" + getName() + "\")";
    }

    /**
     * checks whether the current object is equal another artist object
     * @param otherArtist other
     * @return true if they are equivalent
     */
    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.getName()) && this.albums.containsAll(otherArtist.getAlbums()) &&
            otherArtist.getAlbums().containsAll(this.albums)) {
            return true;
        } else {
            return false;
        }
    }


}
