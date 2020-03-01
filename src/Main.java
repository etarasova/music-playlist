import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {

        //create List to store songQueues from the folder "data"
        List<SongQueue> songQueues = readSongQueues(new File("data"));

        //create one playlist with songs from all songQueues
        PlaylistQueue playlist = mergeSongQueues(songQueues);

        //remove duplicates from the playlist
        playlist = removeDuplicateSongs(playlist);

        //sort playlist in ascending order
        playlist.sort(new SongComparator());

        //write sorted playlist in file
        writeCSVFile("output/Playlist.csv", playlist);

        //create playlistHistory to track the recently listened to tracks
        PlaylistHistory playlistHistory = new PlaylistHistory();

        //playing songs in playlist
        while (!playlist.isEmpty()) {
            Song song = playlist.listenToSong();
            playlistHistory.addSong(song);
        }

        //print out the last song
        System.out.println("Last song listened to: " + playlistHistory.lastListened());
    }

    private static List<SongQueue> readSongQueues(File folder) throws Exception {
        File[] folderFiles = folder.listFiles();
        List<SongQueue> songQueues = new LinkedList<>();
        if (folderFiles != null && folderFiles.length > 0) {
            for (File file : folderFiles) {
                List<String[]> rows = readCSVFile(file);
                SongQueue songQueue = new SongQueue(rows);
                songQueues.add(songQueue);
            }
        }
        return songQueues;
    }

    //read csv file using openCSV library
    private static List<String[]> readCSVFile(File file) throws IOException, CsvException {
        //Start reading from line number 2 (line numbers start from zero)
        CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(2)
                .build();
        //Read all rows at once
        return reader.readAll();
    }

    private static PlaylistQueue mergeSongQueues(List<SongQueue> songQueues) {
        PlaylistQueue playlist = new PlaylistQueue();
        for (SongQueue songQueue : songQueues) {
            playlist.addAll(songQueue);
        }
        return playlist;
    }

    private static PlaylistQueue removeDuplicateSongs(PlaylistQueue playlist) {
        Set<Song> songSet = new HashSet<>(playlist);
        return new PlaylistQueue(songSet);
    }

    //write all songs in playlist to file
    private static void writeCSVFile(String filename, PlaylistQueue playlist) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename))) {
            writer.writeAll(playlist.toRows());
        }
    }

}

class SongQueue extends LinkedList<Song> {

    SongQueue(List<String[]> rows) {
        addSongs(rows);
    }

    void addSongs(List<String[]> rows) {
        for (String[] row : rows) {
            addSong(row);
        }
    }

    void addSong(String[] row) {
        addSong(new Song(row[1]));
    }

    void addSong(Song song) {
        this.add(song);
    }
}

class PlaylistQueue extends LinkedList<Song> {

    PlaylistQueue() {
    }

    PlaylistQueue(Collection<Song> songs) {
        super(songs);
    }

    Song listenToSong() {
        Song song = this.poll();
        if (song != null) {
            System.out.println("Playing song: " + song);
        }
        return song;
    }

    //convert songs in playlist to rows
    List<String[]> toRows() {
        List<String[]> rows = new ArrayList<>(this.size());
        for (Song song : this) {
            rows.add(song.toRow());
        }
        return rows;
    }
}

class PlaylistHistory extends Stack<Song> {

    void addSong(Song song) {
        this.push(song);
    }

    Song lastListened() {
        return this.isEmpty() ? null : this.peek();
    }
}

class Song {
    private String track;

    Song(String track) {
        this.track = track.trim();
    }

    String getTrack() {
        return track;
    }

    public String toString() {
        return getTrack();
    }

    String[] toRow() {
        String[] row = new String[1];
        row[0] = getTrack();
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Song)) {
            return false;
        }
        return this.getTrack().equals(((Song) obj).getTrack());
    }

    @Override
    public int hashCode() {
        return getTrack().hashCode();
    }
}

class SongComparator implements Comparator<Song> {
    @Override
    public int compare(Song song1, Song song2) {
        return song1.getTrack().compareToIgnoreCase(song2.getTrack());
    }
}


