package com.beloghos.dev.maestro.UI;

import javafx.scene.control.TextField;

import java.io.File;
import java.util.ArrayList;

public class UriField extends TextField {

    private String uri;

    public UriField(String uri){
        super(uri);
        this.uri = uri;
        setText(uri);
        textProperty().addListener((observableValue,s,t1) -> {
            System.out.println("textfield changed from " + s + " to " + t1);
        });
        addEventFilter(FolderSelectedEvent.FOLDER_SELECTED_TYPE, (folderSelectedEvent)->{
            setText(folderSelectedEvent.getFolderPath());
        });
    }

    public File getFolder(){
        return new File(uri);
    }

    public File[] listFolders(){

        File folder = new File(uri);
        File[] files = folder.listFiles();
        ArrayList<File> folders = new ArrayList<>();
        if(files!=null)
            for(File file : files){
                if(!isTrack(file))
                    folders.add(file);
            }
        return (File[]) folders.toArray();

    }

    public File[] listFiles(){

        File folder = new File(uri);
        File[] files = folder.listFiles();
        ArrayList<File> tracks = new ArrayList<>();
        if(files!=null)
            for(File file : files){
                if(isTrack(file))
                    tracks.add(file);
            }
        return (File[]) tracks.toArray();

    }

    private boolean isTrack(File file){
        if(!file.isFile())
            return false;
        else{
            String path = file.getPath();
            int dotIndex = path.lastIndexOf(".");
            if(dotIndex == -1)
                return false;
            else
                return path.substring(dotIndex).equals(".mp3") || path.substring(dotIndex).equals(".wav");
        }
    }

    public void upOneFolder(){
        int backslashIndex = uri.lastIndexOf("\\");
        if(backslashIndex != -1)
            uri = uri.subSequence(0,backslashIndex).toString();
    }

    private void setUri(String uri){
        this.uri = uri;
    }

    public void openFolder(Folder folder){
        folder.edit();
    }

    public interface Folder {
        void edit();
    }

}
