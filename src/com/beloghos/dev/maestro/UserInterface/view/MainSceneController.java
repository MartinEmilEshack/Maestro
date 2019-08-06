package com.beloghos.dev.maestro.userInterface.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainSceneController {

    @FXML private BorderPane mainBorderPain;

    // BorderPane -> TOP ------------------------------------------------------------
    @FXML private StackPane topStack;
        @FXML private MenuBar topMenuBar;
            @FXML private Menu fileMenu;
                @FXML private MenuItem closeMenuItem;
            @FXML private Menu editMenu;
                @FXML private MenuItem deleteMenuItem;
            @FXML private Menu helpMenu;
                @FXML private MenuItem aboutMenuItem;
        @FXML private HBox radioButtonHBox;
            @FXML private RadioButton explorerViewRadioButtton;
            @FXML private RadioButton templateViewRadioButtton;
            @FXML private RadioButton statisticsViewRadioButtton;
        @FXML private HBox tracksEditingLabelHBox;
            @FXML private Label tracksDoneProgressPercent;
            @FXML private Label tracksTodoProgressPercent;
        @FXML private ProgressBar tracksEditingProgressBar;
    // BorderPane -> CENTER ---------------------------------------------------------
    @FXML private VBox centerVBox;
        @FXML private HBox explorerToolsHBox;
            @FXML private Button backButton;
                @FXML private ImageView backImage;
            @FXML private Button forwardButton;
                @FXML private ImageView forwardImage;
            @FXML private Button upOneFolderButton;
                @FXML private ImageView upOneFolderImage;
            @FXML private Label uriLabel;
            @FXML private TextField uriTextField;
            @FXML private Label searchLabel;
            @FXML private TextField searchTextField;
            @FXML private Button searchButton;
            @FXML private Button moreSearchOptions;
        @FXML private SplitPane mainCenterSplitPane;
            @FXML private SplitPane folderAndTrackSplitPane; // Center
                @FXML private StackPane tracksStackPane;
                    @FXML private TableView tracksTable;
                        @FXML private TableColumn trackNameColumn;
                        @FXML private TableColumn trackDataColumn;
                    // ProgressBar added and removed dynamically
            @FXML private TabPane previewTabPane; // Far Right
                @FXML private Tab infoTab;
                    @FXML private VBox infoVBox;
                @FXML private Tab playlistTab;

}