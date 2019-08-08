package com.beloghos.dev.maestro.userInterface.view;

import com.beloghos.dev.maestro.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExplorerTools extends HBox implements Initializable {

    @FXML private TextField uriTextField;
    @FXML private Button backButton;
    @FXML private Button forwardButton;
    @FXML private Button upOneFolderButton;
    private ArrayList<String> stack;

    public ExplorerTools(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ExplorerToolsView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

//    public ExplorerTools(String uriPath){
//
//        uriTextField = new TextField(uriPath);
//        uriTextField.prefWidthProperty().bind(widthProperty());
//        stack = new ArrayList<>();
//        stack.add("#"+uriPath);
//
//        backButton = makeButton();
//        switchBackButton(false);
//
//        forwardButton = makeButton();
//        switchForwardButton(false);
//
//        upOneFolderButton = makeButton();
//        switchUpOneFolderButton(false);
//
//        setSpacing(5);
//        setPadding(new Insets(0,0,0,3));
//        getChildren().addAll(backButton,forwardButton,upOneFolderButton,uriTextField);
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        stack = new ArrayList<>();
        stack.add("#"+Main.MEMORY.getURI().getPath());

        backButton.setUserData(false);
        backButton.setOnAction(actionEvent -> setPreviousUriPath(uriTextField.getText()));
        forwardButton.setUserData(false);
        forwardButton.setOnAction(actionEvent -> setNextUriPath(uriTextField.getText()));
        upOneFolderButton.setUserData(false);
        upOneFolderButton.setOnAction(actionEvent -> setParentFolder(uriTextField.getText()));

        uriTextField.textProperty().bind(Main.MEMORY.getURI().getPathProperty());

        Main.MEMORY.getURI().getPathProperty().addListener((observableValue, oldValue, newValue) -> {
            if(stack.indexOf("#"+newValue)<0)
                setNewText(oldValue,newValue);
        });

    }

    private void switchBackButton(boolean on){
        if (on & !(boolean)backButton.getUserData()) {
            backButton.setDisable(false);
            backButton.setUserData(true);
        } else if(!on & (boolean)backButton.getUserData()){
            backButton.setDisable(true);
            backButton.setUserData(false);
        }
    }

    private void switchForwardButton(boolean on){
    if (on & !(boolean)forwardButton.getUserData()) {
            forwardButton.setDisable(false);
            forwardButton.setUserData(true);
        } else if (!on & (boolean)forwardButton.getUserData()){
            forwardButton.setDisable(true);
            forwardButton.setUserData(false);
        }
    }

    private void switchUpOneFolderButton(boolean on){
        if (on & !(boolean)upOneFolderButton.getUserData()) {
            upOneFolderButton.setDisable(false);
            upOneFolderButton.setUserData(true);
        } else if (!on & (boolean)upOneFolderButton.getUserData()){
            upOneFolderButton.setDisable(true);
            upOneFolderButton.setUserData(false);
        }
    }

    private void setPreviousUriPath(String uri){
//        System.out.println(uri+"@setPreviousUriPath");
        int indexOfNow = stack.indexOf("#" + uri);
        String newURI = stack.get(indexOfNow-1);
        switchBackButton(indexOfNow-1 > 0);
        switchUpOneFolderButton(!newURI.equals(Main.MEMORY.getURI().getRootFolder()));
        switchForwardButton(true);
        stack.set(indexOfNow, uri);
        stack.set(indexOfNow - 1, "#"+newURI);
        Main.MEMORY.getURI().setPath(newURI);
    }

    private void setNextUriPath(String uri){
//        System.out.println(uri+"@setNextUriPath");
        int indexOfNow = stack.indexOf("#" + uri);
        String newURI = stack.get(indexOfNow+1);
        switchForwardButton(indexOfNow+1 < stack.size()-1);
        switchUpOneFolderButton(!newURI.equals(Main.MEMORY.getURI().getRootFolder()));
        switchBackButton(true);
        stack.set(indexOfNow, uri);
        stack.set(indexOfNow + 1, "#"+newURI);
        Main.MEMORY.getURI().setPath(newURI);
    }

    private void setParentFolder(String uri){
//        System.out.println(uri+"@setParentFolder");
        String newURI = (new java.io.File(uri)).getParent();
        switchUpOneFolderButton(!newURI.equals(Main.MEMORY.getURI().getRootFolder()));
        switchBackButton(true);
        switchForwardButton(false);
        int indexOfNow = stack.indexOf("#" + uri);
//      System.out.println(newURI+"@#"+indexOfNow);
        if (indexOfNow < stack.size() - 1)
            stack = new ArrayList<>(stack.subList(0, indexOfNow+1));
        stack.set(indexOfNow, uri);
        stack.add("#" + newURI);
        Main.MEMORY.getURI().setPath(newURI);
    }

    private void setNewText(String oldURI,String newURI){
//        System.out.println(newURI +"@setNewText");
        if(newURI.equals(Main.MEMORY.getURI().getRootFolder())){
            if((boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(false);
        } else if(!(boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(true);

        if(!(boolean)backButton.getUserData()) switchBackButton(true);
        if((boolean)forwardButton.getUserData()) switchForwardButton(false);

        int indexOfNow = stack.indexOf("#"+oldURI);
        if(indexOfNow < stack.size()-1)
            stack = new ArrayList<>(stack.subList(0,indexOfNow+1));
        stack.set(indexOfNow,oldURI);
        stack.add("#"+ newURI);
    }

//    private Button makeButton(){
//        Button button = new Button();
//        button.setPadding(new Insets(0,0,0,0));
//        button.setMaxHeight(Double.MAX_VALUE);
//        button.getStylesheets().add("com/beloghos/dev/maestro/CSS/ExplorerButton.css");
//        button.setUserData(true);
//        return button;
//    }
//
//    private void switchBackButton(boolean on){
//        if(backButton!=null) {
//            if (on & !(boolean)backButton.getUserData()) {
//                backButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.PREVIOUS_FOLDER_SELECTED, this.uriTextField.getText())));
//                backButton.setGraphic(changeButtonImage(ImageURIs.backIconBlack));
//                backButton.setUserData(true);
//            } else if(!on & (boolean)backButton.getUserData()){
//                backButton.setOnAction(null);
//                backButton.setGraphic(changeButtonImage(ImageURIs.backIconGray));
//                backButton.setUserData(false);
//            }
//        }
//    }
//
//    private void switchForwardButton(boolean on){
//        if(forwardButton!=null) {
//            if (on & !(boolean)forwardButton.getUserData()) {
//                forwardButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.NEXT_FOLDER_SELECTED, this.uriTextField.getText())));
//                forwardButton.setGraphic(changeButtonImage(ImageURIs.forwardIconBlack));
//                forwardButton.setUserData(true);
//            } else if (!on & (boolean)forwardButton.getUserData()){
//                forwardButton.setOnAction(null);
//                forwardButton.setGraphic(changeButtonImage(ImageURIs.forwardIconGray));
//                forwardButton.setUserData(false);
//            }
//        }
//    }
//
//    private void switchUpOneFolderButton(boolean on){
//        if(upOneFolderButton!=null) {
//            if (on & !(boolean)upOneFolderButton.getUserData()) {
//                upOneFolderButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.PARENT_FOLDER_SELECTED, this.uriTextField.getText())));
//                upOneFolderButton.setGraphic(changeButtonImage(ImageURIs.upOneFolderIcon));
//                upOneFolderButton.setUserData(true);
//            } else if (!on & (boolean)upOneFolderButton.getUserData()){
//                upOneFolderButton.setOnAction(null);
//                upOneFolderButton.setGraphic(changeButtonImage(ImageURIs.upOneFolderIcon));
//                upOneFolderButton.setUserData(false);
//            }
//        }
//    }
//
//    private ImageView changeButtonImage(String imageURI){
//        ImageView imageView = new ImageView(new Image(imageURI));
//        imageView.setPreserveRatio(true);
//        imageView.setFitWidth(20);
//        return imageView;
//    }
//
//    public String getUriText(){
//        return uriTextField.getText();
//    }
//
//    public void setPreviousUriPath(String uriTextField){
//        System.out.println(uriTextField+"@setPreviousUriPath");
//        if(this.uriTextField!=null) {
//            int indexOfNow = stack.indexOf("#" + uriTextField);
//            String newURI = stack.get(indexOfNow-1);
//            switchBackButton(indexOfNow-1 > 0);
//            switchUpOneFolderButton(!newURI.equals(Main.URI_PATH));
//            switchForwardButton(true);
//            stack.set(indexOfNow, uriTextField);
//            stack.set(indexOfNow - 1, "#"+newURI);
//            this.uriTextField.setText(newURI);
//        }
//    }
//
//    public void setNextUriPath(String uriTextField){
//        System.out.println(uriTextField+"@setNextUriPath");
//        if(this.uriTextField!=null) {
//            int indexOfNow = stack.indexOf("#" + uriTextField);
//            String newURI = stack.get(indexOfNow+1);
//            switchForwardButton(indexOfNow+1 < stack.size()-1);
//            switchUpOneFolderButton(!newURI.equals(Main.URI_PATH));
//            switchBackButton(true);
//            stack.set(indexOfNow, uriTextField);
//            stack.set(indexOfNow + 1, "#"+newURI);
//            this.uriTextField.setText(newURI);
//        }
//    }
//
//    public void setParentFolder(String uriTextField){
//        System.out.println(uriTextField+"@setParentFolder");
//        if(this.uriTextField!=null ) {
//            String newURI = (new java.io.File(uriTextField)).getParent();
//            switchUpOneFolderButton(!newURI.equals(Main.URI_PATH));
//            switchBackButton(true);
//            switchForwardButton(false);
//            int indexOfNow = stack.indexOf("#" + uriTextField);
////            System.out.println(newURI+"@#"+indexOfNow);
//            if (indexOfNow < stack.size() - 1)
//                stack = new ArrayList<>(stack.subList(0, indexOfNow+1));
//            stack.set(indexOfNow, uriTextField);
//            stack.add("#" + newURI);
//            this.uriTextField.setText(newURI);
//        }
//    }
//
//    public void setNewText(String newURI){
//        System.out.println(newURI +"@setNewText");
//        if(this.uriTextField!=null){
//            if(newURI.equals(Main.URI_PATH)){
//                if((boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(false);
//            } else if(!(boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(true);
//
//            if(!(boolean)backButton.getUserData()) switchBackButton(true);
//            if((boolean)forwardButton.getUserData()) switchForwardButton(false);
//
//            int indexOfNow = stack.indexOf("#"+this.uriTextField.getText());
//            if(indexOfNow < stack.size()-1)
//                stack = new ArrayList<>(stack.subList(0,indexOfNow+1));
//            stack.set(indexOfNow,this.uriTextField.getText());
//            stack.add("#"+ newURI);
//            this.uriTextField.setText(newURI);
//
//        }
//    }

}