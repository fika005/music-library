package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    ArrayList<Song> songs;
    ArrayList<Album> albums;
    ArrayList<Artist> artists;
    ArrayList<Playlist> playlists;

    public Library() {
        songs = new ArrayList<Song>();
        albums = new ArrayList<Album>();
        artists = new ArrayList<Artist>();
        playlists = new ArrayList<Playlist>();
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

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    /* you complete this. Return an empty Album if the search fails. If there is more than one match, return the first */
    public Album findAlbum(String name) {
        for (Album currentAlbum : albums){
            if (currentAlbum.getName().equals(name)){
                return currentAlbum;
            }
        }
        return new Album();
    }

    /* you complete this. Return an empty Artist if the search fails. If there is more than one match, return the first*/
    public Artist findArtist(String name) {
        for (Artist currentArtist : artists){
            if (currentArtist.getName().equals(name)){
                return currentArtist;
            }
        }
        return new Artist();

    }

    /* you complete this. Return an empty Song if the search fails. If there is more than one match, return the first*/
    public Song findSong(String name, Artist a) {
        for (Song currentSong : songs){
            if (currentSong.getName().equals(name) && currentSong.getArtist().equals(a)) {
                return currentSong;
            }
        }
        return new Song();
    }

    /* you complete this. */
    public void add(Entity e) {
        if (e instanceof Song) {
            songs.add((Song) e);
        }
        else if (e instanceof Artist) {
            artists.add((Artist) e);
        }
        else if (e instanceof Album){
            albums.add((Album) e);
        }
        else if (e instanceof Playlist){
            playlists.add((Playlist) e);
        }
    }
    /* you complete this */
    public void delete(Entity e) {
        if (e instanceof Song) {
            songs.remove((Song) e);
        }
        else if (e instanceof Artist) {
            artists.remove((Artist) e);
        }
        else if (e instanceof Album){
            albums.remove((Album) e);
        }
        else if (e instanceof Playlist){
            playlists.remove((Playlist) e);
        }
    }

    /* you complete this. Print out the library in a pretty, user-friendly way. */
    public void display() {
        StringBuilder sb = new StringBuilder();
        for (Song s : songs){
            sb.append(s.toString() + "\n");
        }
        for (Album a : albums){
            sb.append(a.toString() + "\n");
        }
        for (Artist a : artists){
            sb.append(a.toString() + "\n");
        }
        for (Playlist p : playlists){
            sb.append(p.toString() + "\n");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb.toString());

    }

    /* you complete this. Return the first match, using the equals() method to determine if something is a duplicate.
        Return an empty Entity if no match is found.
    */

    public Entity findDuplicate(Entity e) {
        if (e instanceof Song) {
            for (Song s : songs){
                if (e.equals(s)){
                    return s;
                }
            }
        }
        else if (e instanceof Artist) {
            for (Artist a : artists){
                if (e.equals(a)){
                    return a;
                }
            }
        }
        else if (e instanceof Album){
            for (Album a : albums){
                if (e.equals(a)){
                    return a;
                }
            }
        }
        else if (e instanceof Playlist){
            for (Playlist p : playlists){
                if (e.equals(p)){
                    return p;
                }
            }
        }
        return new Entity();
    }

    /* you complete this. Read from a file that has a CSV format like:
    Here Comes the Sun, The Beatles, Abbey Road, 3:22
    Tomorrow Never Knows, The Beatles, Revolver, 2:56

     */
    public void readFromFile(String f) throws FileNotFoundException {
        Scanner s;
        File infile = new File(f);
        s = new Scanner(infile);
        String buffer;
        while (s.hasNextLine()) {
            buffer = s.nextLine();
            if (buffer.startsWith("Song name: ")){
                String[] contents = buffer.split("; ");
                Song newSong = new Song();
                newSong.setName(contents[0].split(": ")[1]);
                Artist foundArtist = this.findArtist(contents[1].split(": ")[1]);
                if (!foundArtist.getName().equals("")) {
                    newSong.setArtist(foundArtist);
                } else {
                    Artist artist = new Artist(contents[1].split(": ")[1]);
                    newSong.setArtist(artist);
                }
                newSong.setTimesPlayed(Integer.parseInt(contents[2].split(": ")[1]));
                newSong.setRunningTime(contents[3].split(": ")[1]);
                this.songs.add(newSong);
            }
            else if (buffer.startsWith("Album: ")){
                String[] contents = buffer.split("; ");
                Album newAlbum = new Album(contents[0].split(": ")[1]);
                Artist foundArtist = this.findArtist(contents[1].split(": ")[1]);
                if (!foundArtist.getName().equals("")) {
                    newAlbum.setArtist(foundArtist);
                } else {
                    Artist artist = new Artist(contents[1].split(": ")[1]);
                    newAlbum.setArtist(artist);
                }
                ArrayList<Song> songz = new ArrayList<Song>();
                for (String songName : contents[2].split(": ")[1].split(", ")) {
                    Song song = new Song();
                    song.setName(songName);
                    songz.add(song);
                }
                newAlbum.setSongs(songz);
                this.albums.add(newAlbum);
            }
            else if (buffer.startsWith("Artist: ")){
                String[] contents = buffer.split("; ");
                Artist newArtist = new Artist(contents[0].split(": ")[1]);
                ArrayList<Song> songz = new ArrayList<Song>();
                for (String songName : contents[1].split(": ")[1].split(", ")) {
                    Song song = new Song();
                    song.setName(songName);
                    songz.add(song);
                }
                ArrayList<Album> albumz = new ArrayList<Album>();
                for (String albumName : contents[2].split(": ")[1].split(", ")) {
                    Album album = new Album();
                    album.setName(albumName);
                    albumz.add(album);
                }
                newArtist.setSongs(songz);
                newArtist.setAlbums(albumz);
                this.artists.add(newArtist);
            }
        }

    }

    /* write the data out to a file in the exact same format. */
    public void writeToFile(String fname) {
        PrintWriter outfile;
        try {
            outfile = new PrintWriter(fname);
            for (Song s : songs){
                outfile.println(s.toString());
            }
            for (Album a : albums){
                outfile.println(a.toString());
            }
            for (Artist a : artists){
                outfile.println(a.toString());
            }
            for (Playlist p : playlists){
                outfile.println(p.toString());
            }
            outfile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file!");
            return;
        }

    }
}
