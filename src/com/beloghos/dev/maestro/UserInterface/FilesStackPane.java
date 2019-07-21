package com.beloghos.dev.maestro.UserInterface;

import com.beloghos.dev.maestro.Job.FolderOpener;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;

public class FilesStackPane extends StackPane {

    private TilePane tilePane;

    public FilesStackPane(String pathname){
        super();

        tilePane = new TilePane();
        tilePane.setOrientation(Orientation.HORIZONTAL);
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setTileAlignment(Pos.TOP_LEFT);
        tilePane.setPadding(new Insets(20,20,20,20));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(tilePane);

        setAlignment(Pos.TOP_LEFT);
        getChildren().add(scrollPane);
        setFiles(pathname);
    }

    public void setFiles(String pathName){

        ProgressBar fileLoadingProgressBar = new ProgressBar();
        fileLoadingProgressBar.setPrefHeight(2);
        fileLoadingProgressBar.setPadding(Insets.EMPTY);
        fileLoadingProgressBar.getStylesheets().add("com/beloghos/dev/maestro/CSS/ProgressBar.css");
        fileLoadingProgressBar.prefWidthProperty().bind(this.widthProperty());

        getChildren().add(fileLoadingProgressBar);

        if(!tilePane.getChildren().isEmpty())
            tilePane.getChildren().clear();

        FolderOpener folderOpener = new FolderOpener();
        folderOpener.setTodo(() -> {
            File uriPath = new File(pathName);
            File[] files = uriPath.listFiles();
            if (files != null){
                int now=1 , all=files.length;
                for (File file : files) {
                    folderOpener.updateProgress(now, all);
                    if (isNotTrack(file)) {
                        FileControl fileControl = new FileControl(file);
                        Platform.runLater(() -> tilePane.getChildren().add(fileControl));
                    }
                    now++;
                }
            }
            Platform.runLater(()->{
                fileLoadingProgressBar.progressProperty().unbind();
                getChildren().remove(fileLoadingProgressBar);
            });
        });
        fileLoadingProgressBar.progressProperty().bind(folderOpener.progressProperty());

        Thread exe = new Thread(folderOpener);
        exe.start();
//        new Thread(()-> {
//            File uriPath = new File(pathName);
//            File[] files = uriPath.listFiles();
//            if(files != null)
//                for (File file : files)
//                    if(isNotTrack(file)){
//                        FileControl fileControl = new FileControl(file);
//                        Platform.runLater(()-> getChildren().add(fileControl));
//                    }
//        }).start();

    }

    private boolean isNotTrack(File file){
        if(file.isFile()){
            String path = file.getPath();int dotIndex = path.lastIndexOf(".");
            if(dotIndex == -1)
                return true;
            else
                return !(path.substring(dotIndex).equals(".mp3") || path.substring(dotIndex).equals(".wav"));
        }
        return true;
    }

}
