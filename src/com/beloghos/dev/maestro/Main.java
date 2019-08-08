package com.beloghos.dev.maestro;

import com.beloghos.dev.maestro.Job.Worker;

import com.beloghos.dev.maestro.ui.model.Memory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final Worker WORKER = new Worker();
    public static final Memory MEMORY = new Memory();

    @Override
    public void start(Stage primaryStage) {

//        Parent root = FXMLLoader.load(getClass().getResource("Old/MainScene.fxml"));
//        MainSceneController.setRootDirectories(FXCollections.observableArrayList(rootDirItem));
//        rootDirItem = ResourceItem.createObservedPath(Paths.get("E:\\Beloghos"));

//        ArrayList<String> dummyList = new ArrayList<>();
//        int i  = 3;
//        while(i >= 0){
//            dummyList.add("martin");
//            dummyList.add("martina");
//            dummyList.add("marlin");
//            dummyList.add("marina");
//            i--;
//        }
//
//        FoldersTreeView folderTreeView = new FoldersTreeView(URI_PATH);
//        ExplorerToolsView explorerTools = new ExplorerToolsView(URI_PATH);
//        FoldersView stackPane = new FoldersView(URI_PATH);
//        TracksView trackListView = new TracksView(URI_PATH);
//
//
////        SplitPane splitPane = new SplitPane(stackPane,new ListView<>(FXCollections.observableList(dummyList)));
//        SplitPane splitPane = new SplitPane(stackPane,trackListView);
//        splitPane.setOrientation(Orientation.VERTICAL);
//        splitPane.getStylesheets().add(this.getClass().getResource("CSS/SplitPane.css").toExternalForm());
//
//        BorderPane borderPane = new BorderPane();
//        borderPane.setLeft(folderTreeView);
//        borderPane.setTop(explorerTools);
//        borderPane.setCenter(splitPane);
//        borderPane.addEventHandler(FolderSelectedEvent.FOLDER_SELECTED,(openFolderEvent)->{
//            switch (openFolderEvent.getEventType().getName()){
//                case "PREVIOUS_FOLDER_SELECTED": explorerTools.setPreviousUriPath(openFolderEvent.getFolderPath());break;
//                case "NEXT_FOLDER_SELECTED": explorerTools.setNextUriPath(openFolderEvent.getFolderPath());break;
//                case "PARENT_FOLDER_SELECTED": explorerTools.setParentFolder(openFolderEvent.getFolderPath());break;
//                default:
//                    if(!explorerTools.getUriText().equals(openFolderEvent.getFolderPath())){
//                        stackPane.setFiles(openFolderEvent.getFolderPath());
//                        trackListView.showDirectoryContent(openFolderEvent.getFolderPath());
//                        explorerTools.setNewText(openFolderEvent.getFolderPath());
//                        return;
//                    }
//            }
//            stackPane.setFiles(explorerTools.getUriText());
//            trackListView.showDirectoryContent(explorerTools.getUriText());
//        });
//
//        Scene scene = new Scene(borderPane, 1000, 700);
//
//        primaryStage.setTitle("Maestro");
//        primaryStage.setMaximized(true);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/view/MainScene.fxml"));
            BorderPane main  = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(main);
            primaryStage.setTitle("Maestro");
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            WORKER.shutdown();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        WORKER.shutdown();
    }

}
