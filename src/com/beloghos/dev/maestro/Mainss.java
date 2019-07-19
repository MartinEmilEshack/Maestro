package com.beloghos.dev.maestro;

import com.beloghos.dev.maestro.UI.FolderSelectedEvent;
import com.beloghos.dev.maestro.UI.FoldersTreeView;
import com.beloghos.dev.maestro.UI.UriField;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Mainss extends Application {

//    private static RootDirItem rootDirItem;

    @Override
    public void start(Stage primaryStage) {

//        Parent root = FXMLLoader.load(getClass().getResource("Controllers/MainScene.fxml"));
//        MainSceneController.setRootDirectories(FXCollections.observableArrayList(rootDirItem));
//        rootDirItem = ResourceItem.createObservedPath(Paths.get("E:\\Beloghos"));

        FoldersTreeView folderTreeView = new FoldersTreeView("E:\\Beloghos");
        UriField uri = new UriField("E:\\Beloghos");

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(folderTreeView);
        borderPane.setTop(uri);
        borderPane.addEventHandler(FolderSelectedEvent.FOLDER_SELECTED_TYPE,(openFolderEvent)->{
            uri.setText(openFolderEvent.getFolderPath());
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
