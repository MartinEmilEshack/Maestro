package com.beloghos.dev.maestro.userInterface.view;

import com.beloghos.dev.maestro.Job.Task;
import com.beloghos.dev.maestro.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FoldersView extends StackPane implements Initializable {

    @FXML private TilePane foldersTilePane;

//    public FoldersView(String pathname){
//        super();
//
//        tilePane = new TilePane();
//        tilePane.setOrientation(Orientation.HORIZONTAL);
//        tilePane.setVgap(10);
//        tilePane.setHgap(10);
//        tilePane.setTileAlignment(Pos.TOP_LEFT);
//        tilePane.setPadding(new Insets(20,20,20,20));
//
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setFitToHeight(true);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setContent(tilePane);
//
//        setAlignment(Pos.TOP_LEFT);
//        getChildren().add(scrollPane);
//        setFiles(pathname);
//    }

    public FoldersView(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FoldersView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFiles(Main.MEMORY.getURI().getPath());
        Main.MEMORY.getURI().getPathProperty().addListener((observableValue, oldValue, newValue) -> setFiles(newValue));
    }

    private void setFiles(String pathName){

        ProgressBar fileLoadingProgressBar = new ProgressBar();
        fileLoadingProgressBar.setPrefHeight(2);
        fileLoadingProgressBar.setPadding(Insets.EMPTY);
        fileLoadingProgressBar.getStylesheets().add("com/beloghos/dev/maestro/CSS/ProgressBar.css");
        fileLoadingProgressBar.prefWidthProperty().bind(this.widthProperty());

        getChildren().add(fileLoadingProgressBar);

        if(!foldersTilePane.getChildren().isEmpty())
            foldersTilePane.getChildren().clear();

        Task task = new Task(Task.Priority.MAX);
        task.is(() -> {
            File uriPath = new File(pathName);
            File[] files = uriPath.listFiles();
            if (files != null){
                int now=1 , all=files.length;
                for (File file : files) {
                    task.updateProgress(now, all);
                    if (isNotTrack(file)) {
                        FileControl fileControl = new FileControl(file);
                        Platform.runLater(() -> foldersTilePane.getChildren().add(fileControl));
                    }
                    now++;
                }
            }
            Platform.runLater(()->{
                fileLoadingProgressBar.prefWidthProperty().unbind();
                getChildren().remove(fileLoadingProgressBar);
            });
        });
        fileLoadingProgressBar.progressProperty().bind(task.progressProperty());

//        Thread exe = new Thread(task);
//        exe.start();

        Main.WORKER.execute(task);

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
            return (dotIndex == -1) ||
                    !(path.substring(dotIndex).equals(".mp3") ||
                      path.substring(dotIndex).equals(".wav") ||
                      path.substring(dotIndex).equals(".m4a"));
        }
        return true;
    }

}
