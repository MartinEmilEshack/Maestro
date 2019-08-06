package com.beloghos.dev.maestro;

import com.beloghos.dev.maestro.Database.DatabaseManager;
import com.beloghos.dev.maestro.Database.TracksTable;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTXXX;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainTest {

    private static final String TRACK = "E:\\Beloghos\\1-Captain America.mp3";

    public static void main(String[] args){

        try {
            DatabaseManager dbManager = new DatabaseManager();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//
//            File file = new File(TRACK);
//            AudioFile track = AudioFileIO.read(file);
//            if (setCustomTag(track, "decryption bla bla", "text bla bla")) {
//                System.out.println("added tag");
//            } else {
//                System.out.println("tag failed");
//            }
//            Tag tag = track.getTag();
//            AudioHeader header = track.getAudioHeader();
//
//            TracksTable tracksTable = new TracksTable();
//            tracksTable.name = file.getName();
//            tracksTable.artist = tag.getFirst(FieldKey.ALBUM_ARTIST);
//            tracksTable.album = tag.getFirst(FieldKey.ALBUM);
//            tracksTable.event = tag.getFirst(FieldKey.GENRE);
//            tracksTable.timeLength = header.getTrackLength()+"";
//            tracksTable.soundQuality = tag.getFirst(FieldKey.RATING);
//            tracksTable.mood = tag.getFirst(FieldKey.MOOD);
//            tracksTable.memorySize = file.getTotalSpace()+"";
//            tracksTable.captiveWords = tag.getFirst(FieldKey.TITLE);
//
//
//            System.out.println( tracksTable.name + " / " +
//                    tracksTable.artist + " / " +
//                    tracksTable.album + " / " +
//                    tracksTable.event + " / " +
//                    tracksTable.timeLength + " / " +
//                    tracksTable.soundQuality + " / " +
//                    tracksTable.mood + " / " +
//                    tracksTable.memorySize + " / " +
//                    tracksTable.captiveWords);
//
//
//        } catch (CannotReadException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TagException e) {
//            e.printStackTrace();
//        } catch (ReadOnlyFileException e) {
//            e.printStackTrace();
//        } catch (InvalidAudioFrameException e) {
//            e.printStackTrace();
//        }

    }

    public static boolean setCustomTag(AudioFile audioFile, String description, String text){
        FrameBodyTXXX txxxBody = new FrameBodyTXXX();
        txxxBody.setDescription(description);
        txxxBody.setText(text);

        // Get the tag from the audio file
        // If there is no ID3Tag create an ID3v2.3 tag
        Tag tag = audioFile.getTagOrCreateAndSetDefault();
        // If there is only a ID3v1 tag, copy data into new ID3v2.3 tag
        if(!(tag instanceof ID3v23Tag || tag instanceof ID3v24Tag)){
            Tag newTagV23 = null;
            if(tag instanceof ID3v1Tag){
                newTagV23 = new ID3v23Tag((ID3v1Tag)audioFile.getTag()); // Copy old tag data
            }
            if(tag instanceof ID3v22Tag){
                newTagV23 = new ID3v23Tag((ID3v11Tag)audioFile.getTag()); // Copy old tag data
            }
            audioFile.setTag(newTagV23);
        }

        AbstractID3v2Frame frame = null;
        if(tag instanceof ID3v23Tag){
            frame = new ID3v23Frame("TXXX");
        }
        else if(tag instanceof ID3v24Tag){
            frame = new ID3v24Frame("TXXX");
        }

        if(frame != null) {
            frame.setBody(txxxBody);

            try {
                tag.addField(frame);
            } catch (FieldDataInvalidException e) {
                System.out.println("FieldDataInvalidException");
                e.printStackTrace();
                return false;
            }
        }

        try {
            audioFile.commit();
        } catch (CannotWriteException e) {
            System.out.println("CannotWriteException");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //        Path file = Paths.get("E:\\Beloghos\\bla bla bla.txt");
//        UserDefinedFileAttributeView view = Files.getFileAttributeView(file,UserDefinedFileAttributeView.class);
//
//        String name = "user.mimetype";
//        String value = null;
//        ByteBuffer buf = null;
//        try {
//            buf = ByteBuffer.allocate(view.size(name));
//            view.read(name, buf);
//            buf.flip();
//            value = Charset.defaultCharset().decode(buf).toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(value!=null)
//            System.out.println(value);


}
