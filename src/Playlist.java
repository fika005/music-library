package src;

import java.util.ArrayList;
import java.util.Collections;

public class Playlist extends Entity {
    ArrayList<Song> songs;

    public Playlist() {
        super();
        this.songs = new ArrayList<Song>();
    }

    public Playlist(String n) {
        super(n);
        this.songs = new ArrayList<Song>();
    }

    /* add setters and getters here */

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /* add a new song.  */
    public void add(Song newSong) {
        songs.add(newSong);
    }

    /* remove Song s from the playlist */
    public void remove(Song s) {
        if (songs.contains(s)) {
            songs.remove(s);
        } else {
            System.out.println("The playlist does not contain this song.");
        }
    }

    /* shuffle - randomly reorder the playlist in place. */
    public void shuffle() {
        Collections.shuffle(songs);
    }

}
