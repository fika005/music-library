package src;

import java.util.ArrayList;

public class Artist extends Entity {
    ArrayList<Song> songs;
    ArrayList<Album> albums;

    /* you complete this */
    public Artist() {
        songs = new ArrayList<Song>();
        albums = new ArrayList<Album>();
    }

    /* you complete this */
    public Artist(String name) {
        super(name);
        songs = new ArrayList<Song>();
        albums = new ArrayList<Album>();
    }

    /* add setters and getters */

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

    public String toHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr><td>" + getName() + "</td>");
        sb.append("<td><ul>");
        for (Song s : songs){
            sb.append("<li>" + s.name + "</li>");
        }
        sb.append("</ul></td>");

//        sb.setLength(sb.length() - 2);
        sb.append("<td><ul>");
        for (Album a : albums){
            sb.append("<li>" + a.name + "</li>");
        }
        sb.append("</ul></td></tr>");
//        sb.setLength(sb.length() - 2);
        return sb.toString();
    }

    public String toSQL() {
        return "INSERT INTO songs values (" + "\"" + getName() + "\")";
    }
    /* you complete this. Assume that two artists are equal if they have the same name. */
    public boolean equals(Artist otherArtist) {
        if (this.name.equals(otherArtist.getName()) && this.albums.containsAll(otherArtist.getAlbums()) &&
            otherArtist.getAlbums().containsAll(this.albums)) {
            return true;
        } else {
            return false;
        }
    }


}
