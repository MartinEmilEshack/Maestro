package com.beloghos.dev.maestro.ui.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class Track {

    private SimpleStringProperty name;
    private SimpleStringProperty artist;
    private SimpleIntegerProperty rate;
    private Color color;

    public Track(File file){

        AudioFile track;
        try {
            track = AudioFileIO.read(file);
            Tag tag = track.getTag();
            this.name = new SimpleStringProperty(file.getName());
            this.rate = new SimpleIntegerProperty(8);
            this.color = Color.BLACK;
            this.artist = new SimpleStringProperty((tag != null) ? tag.getFirst(FieldKey.ARTIST) : "");

        } catch (CannotReadException e) {
            this.name = new SimpleStringProperty(file.getName());
            this.rate = new SimpleIntegerProperty(8);
            this.artist = new SimpleStringProperty("");
            this.color = Color.RED;
//            System.err.println(" File: " + file.getPath());
//            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(" File: " + file.getPath());
            e.printStackTrace();
        } catch (TagException e) {
            System.err.println(" File: " + file.getPath());
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            System.err.println(" File: " + file.getPath());
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            System.err.println(" File: " + file.getPath());
            e.printStackTrace();
            this.artist = new SimpleStringProperty("");
            this.color = Color.RED;
        }

    }

    public SimpleStringProperty getTrackName() {
        return name;
    }

    public SimpleStringProperty getArtist() {
        return artist;
    }

    public SimpleIntegerProperty getRate() {
        return rate;
    }

    public Color getColor() {
        return color;
    }
}
