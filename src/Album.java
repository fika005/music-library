package src;

import java.util.ArrayList;

public class Album extends Entity {
    ArrayList<Song> songs;
    Artist artist;
    String totalLength;

    /* you complete this */
    public Album() {
        songs = new ArrayList<Song>();
        artist = new Artist();
        totalLength = "";
    }

    /* you complete this */
    public Album(String n, ArrayList<Song> songs, Artist artist, String totalLength) {
        super(n);
        this.songs = songs;
        this.artist = artist;
        this.totalLength = totalLength;
    }

    /* you complete this */
    public Album(String n) {
        super(n);
        songs = new ArrayList<Song>();
        artist = new Artist();
        totalLength = "";

    }

    /* add setters and getters here */

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

    /* you complete this */
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

    public String toSQL() {
        return "INSERT INTO albums values (" + "\"" + getName()  + ")";
    }

    /* you complete this. Assume that two albums are equal if they have the same name and the same artist. */
    public boolean equals(Album otherAlbum) {
        if (this.name.equals(otherAlbum.getName()) && this.artist.name.equals(otherAlbum.artist.getName()) &&
        this.songs.containsAll(otherAlbum.getSongs()) && otherAlbum.getSongs().containsAll(this.songs)) {
            return true;
        } else {
            return false;
        }

    }


}
