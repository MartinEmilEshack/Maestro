package com.beloghos.dev.maestro;

import com.beloghos.dev.maestro.UserInterface.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Mainss extends Application {

    private static final String ROOT_PATH = "E:\\Beloghos";

    @Override
    public void start(Stage primaryStage) {

//        Parent root = FXMLLoader.load(getClass().getResource("Controllers/MainScene.fxml"));
//        MainSceneController.setRootDirectories(FXCollections.observableArrayList(rootDirItem));
//        rootDirItem = ResourceItem.createObservedPath(Paths.get("E:\\Beloghos"));

        ArrayList<String> dummyList = new ArrayList<>();
        int i  = 3;
        while(i >= 0){
            dummyList.add("martin");
            dummyList.add("martina");
            dummyList.add("marlin");
            dummyList.add("marina");
            i--;
        }

        FoldersTreeView folderTreeView = new FoldersTreeView(ROOT_PATH);
        ExplorerTools explorerTools = new ExplorerTools(ROOT_PATH);
        FilesStackPane stackPane = new FilesStackPane(ROOT_PATH);

        SplitPane splitPane = new SplitPane(stackPane,new ListView<>(FXCollections.observableList(dummyList)));
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getStylesheets().add(this.getClass().getResource("CSS/SplitPane.css").toExternalForm());

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(folderTreeView);
        borderPane.setTop(explorerTools);
        borderPane.setCenter(splitPane);
        borderPane.addEventHandler(FolderSelectedEvent.FOLDER_SELECTED,(openFolderEvent)->{
            switch (openFolderEvent.getEventType().getName()){
                case "PREVIOUS_FOLDER_SELECTED": explorerTools.setPreviousUriPath(openFolderEvent.getFolderPath());break;
                case "NEXT_FOLDER_SELECTED": explorerTools.setNextUriPath(openFolderEvent.getFolderPath());break;
                case "PARENT_FOLDER_SELECTED": explorerTools.setParentFolder(openFolderEvent.getFolderPath());break;
                default:
                    if(!explorerTools.getUriText().equals(openFolderEvent.getFolderPath())){
                        stackPane.setFiles(openFolderEvent.getFolderPath());
                        explorerTools.setNewText(openFolderEvent.getFolderPath());
                        return;
                    }
            }
            stackPane.setFiles(explorerTools.getUriText());
        });

        Scene scene = new Scene(borderPane, 1000, 700);

        primaryStage.setTitle("Maestro");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
