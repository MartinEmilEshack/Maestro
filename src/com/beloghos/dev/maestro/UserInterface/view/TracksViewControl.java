package com.beloghos.dev.maestro.userInterface.view;

import com.beloghos.dev.maestro.Job.Task;
import com.beloghos.dev.maestro.Main;
import com.beloghos.dev.maestro.userInterface.model.Track;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TracksViewControl extends StackPane implements Initializable {

    @FXML private TableView<Track> tracksTable;
    @FXML private TableColumn<Track,String> trackNameColumn;
    @FXML private TableColumn<Track,String> trackArtistColumn;
    @FXML private TableColumn<Track,Integer> trackRateColumn;

//    private ObservableList<TrackControl> trackList;
//    private TableView<TrackControl> trackListView;
//    private boolean showAllTracks = false;
//
//    public TracksViewControl(String pathName){
//        super();
//
//        trackList = FXCollections.observableList(new ArrayList<>());
//
//        TableColumn<TrackControl, String> trackColumn = new TableColumn<>("TRACK");
//        trackColumn.setCellValueFactory(cellData -> cellData.getValue().getTrackName());
//
//        trackListView = new TableView<>();
//        trackListView.setItems(trackList);
//        trackListView.getColumns().add(trackColumn);
//
//        setAlignment(Pos.TOP_LEFT);
//        getChildren().add(trackListView);
//        showDirectoryContent(pathName);
//    }

    public TracksViewControl(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TracksView.fxml"));
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



        trackNameColumn.setCellValueFactory(trackData -> trackData.getValue().getTrackName());
        trackArtistColumn.setCellValueFactory(trackData -> trackData.getValue().getArtist());
        trackRateColumn.setCellValueFactory(trackData -> trackData.getValue().getRate().asObject());

        tracksTable.setRowFactory(trackTableView -> new TableRow<>(){
            @Override
            protected void updateItem(Track track, boolean empty) {
                super.updateItem(track, empty);
                if(track == null || empty)
                    setStyle("");
                else{
                    if (track.getColor().equals(Color.RED)) {
                        for(Node tableCell : getChildren())
                            ((Labeled)tableCell).setTextFill(Color.RED);
                    } else {
                        if(getTableView().getSelectionModel().getSelectedItems().contains(track)){
                            for(Node tableCell : getChildren())
                                ((Labeled)tableCell).setTextFill(Color.WHITE);
                        }
                        else{
                            for(Node tableCell : getChildren())
                                ((Labeled)tableCell).setTextFill(Color.BLACK);
                        }
                    }
                }
//                System.out.println("coloring the rows track -> "+track);
            }
        });
        tracksTable.setItems(Main.MEMORY.getTrackList());

        Main.MEMORY.getURI().getPathProperty().addListener((observableValue, oldValue, newValue) -> setTracks(newValue));

        setTracks(Main.MEMORY.getURI().getPath());

    }

    private void setTracks(String folderPath){

        ProgressBar fileLoadingProgressBar = new ProgressBar();
        fileLoadingProgressBar.setPrefHeight(2);
        fileLoadingProgressBar.setPadding(Insets.EMPTY);
        fileLoadingProgressBar.getStylesheets().add("com/beloghos/dev/maestro/CSS/ProgressBar.css");
        fileLoadingProgressBar.prefWidthProperty().bind(this.widthProperty());

        getChildren().add(fileLoadingProgressBar);

        if(!Main.MEMORY.getTrackList().isEmpty())
            Main.MEMORY.getTrackList().clear();

        Task task = new Task(Task.Priority.MEDIUM);

        task.is(()->{
            File uriPath = new File(folderPath);
            File[] files = uriPath.listFiles();
            if (files != null){
                int now=1 , all=files.length;
                for (File file : files) {
                    task.updateProgress(now, all);
//                    System.out.println(file.getPath());
                    if (isTrack(file)){
                        Track track = new Track(file);
                        Platform.runLater(() -> Main.MEMORY.getTrackList().add(track));
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

        Main.WORKER.execute(task);

    }

//
//    public void showDirectoryContent(String folderPath) {
//
//
//        ProgressBar fileLoadingProgressBar = new ProgressBar();
//        fileLoadingProgressBar.setPrefHeight(2);
//        fileLoadingProgressBar.setPadding(Insets.EMPTY);
//        fileLoadingProgressBar.getStylesheets().add("com/beloghos/dev/maestro/CSS/ProgressBar.css");
//        fileLoadingProgressBar.prefWidthProperty().bind(this.widthProperty());
//
//        getChildren().add(fileLoadingProgressBar);
//
//        if(trackList!=null)
//            trackList.clear();
//        ArrayList<File> files = new ArrayList<>();
//
//        Task task = new Task(Task.Priority.MEDIUM);
//
//        fileLoadingProgressBar.progressProperty().bind(task.progressProperty());
//
//        task.is(()-> {
//
//            SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
//
//                @Override
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
////                    if(isTrack(file.toString())) {
////
////                    }
//                    System.out.println(file);
//                    files.add(new File(file.toString()));
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
//                    if(attrs.isDirectory())
//                        System.out.println(dir);
//                    return FileVisitResult.CONTINUE;
//                }
//
//                @Override
//                public FileVisitResult visitFileFailed(Path file, IOException exc) {
//                    if(exc!=null)
//                        System.out.println("error when accessing Directory: "+file+" exception -> "+exc);
//                    return FileVisitResult.CONTINUE;
//                }
//
//            };
//            try {
//                Files.walkFileTree(Paths.get(folderPath),EnumSet.noneOf(FileVisitOption.class),Integer.MAX_VALUE,fileVisitor);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if(!files.isEmpty()){
//                int all = files.size();
//                int now = 1;
//                for (File file:files){
//                    task.updateProgress(now, all);
//                    TrackControl trackControl = new TrackControl(file);
//                    Platform.runLater(() -> trackList.add(trackControl));
//                    now++;
//                }
//            }
//            Platform.runLater(()->{
//                fileLoadingProgressBar.prefWidthProperty().unbind();
//                getChildren().remove(fileLoadingProgressBar);
//            });
//        });
//
//        Main.WORKER.execute(task);
//
//    }

    private boolean isTrack(File file){
        if(file.isFile()){
            String path = file.getPath();
            int dotIndex = path.lastIndexOf(".");
            return (dotIndex != -1) &&
                    (path.substring(dotIndex).equals(".mp3") ||
                     path.substring(dotIndex).equals(".wav") ||
                     path.substring(dotIndex).equals(".m4a"));
        }
        return false;
    }

}