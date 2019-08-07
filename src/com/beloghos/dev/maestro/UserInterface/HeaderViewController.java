package com.beloghos.dev.maestro.userInterface;

import com.beloghos.dev.maestro.Main;
import com.beloghos.dev.maestro.userInterface.controller.LibraryReader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderViewController extends StackPane implements Initializable {

    @FXML private ProgressBar fileReadingProgressBar;
    @FXML private Label tracksDoneProgressPercent;

    public HeaderViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HeaderView.fxml"));
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
        LibraryReader reader = new LibraryReader();
        fileReadingProgressBar.progressProperty().bind(reader.readProgress());
        tracksDoneProgressPercent.textProperty().bind(reader.filesFoundProgress());
        reader.whenFinishedDo(() -> {
            System.out.println("Finished reading directory");
            Platform.runLater(() -> {
                fileReadingProgressBar.setDisable(true);
                fileReadingProgressBar.progressProperty().unbind();
            });
        });
        reader.startReading(Main.MEMORY.getURI().getRootFolder());
    }

}
