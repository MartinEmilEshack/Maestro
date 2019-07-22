package com.beloghos.dev.maestro.UserInterface;

import com.beloghos.dev.maestro.ImageSources.ImageURIs;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ExplorerTools extends HBox {

    private UriField uri;
    private Button backButton;
    private Button forwardButton;
    private Button upOneFolderButton;
    private ArrayList<String> stack;

    public ExplorerTools(String uriPath){

        uri = new UriField(uriPath);
        uri.prefWidthProperty().bind(widthProperty());
        stack = new ArrayList<>();
        stack.add("#"+uriPath);

        backButton = makeButton();
        switchBackButton(false);

        forwardButton = makeButton();
        switchForwardButton(false);

        upOneFolderButton = makeButton();
        switchUpOneFolderButton(false);

        setSpacing(5);
        setPadding(new Insets(0,0,0,3));
        getChildren().addAll(backButton,forwardButton,upOneFolderButton,uri);

    }

    private Button makeButton(){
        Button button = new Button();
        button.setPadding(new Insets(0,0,0,0));
        button.setMaxHeight(Double.MAX_VALUE);
        button.getStylesheets().add("com/beloghos/dev/maestro/CSS/ExplorerButton.css");
        button.setUserData(true);
        return button;
    }

    private ImageView changeButtonImage(String imageURI){
        ImageView imageView = new ImageView(new Image(imageURI));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);
        return imageView;
    }

    private void switchBackButton(boolean on){
        if(backButton!=null) {
            if (on & !(boolean)backButton.getUserData()) {
                backButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.PREVIOUS_FOLDER_SELECTED, this.uri.getText())));
                backButton.setGraphic(changeButtonImage(ImageURIs.backIconBlack));
                backButton.setUserData(true);
            } else if(!on & (boolean)backButton.getUserData()){
                backButton.setOnAction(null);
                backButton.setGraphic(changeButtonImage(ImageURIs.backIconGray));
                backButton.setUserData(false);
            }
        }
    }

    private void switchForwardButton(boolean on){
        if(forwardButton!=null) {
            if (on & !(boolean)forwardButton.getUserData()) {
                forwardButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.NEXT_FOLDER_SELECTED, this.uri.getText())));
                forwardButton.setGraphic(changeButtonImage(ImageURIs.forwardIconBlack));
                forwardButton.setUserData(true);
            } else if (!on & (boolean)forwardButton.getUserData()){
                forwardButton.setOnAction(null);
                forwardButton.setGraphic(changeButtonImage(ImageURIs.forwardIconGray));
                forwardButton.setUserData(false);
            }
        }
    }

    private void switchUpOneFolderButton(boolean on){
        if(upOneFolderButton!=null) {
            if (on & !(boolean)upOneFolderButton.getUserData()) {
                upOneFolderButton.setOnAction(actionEvent -> this.fireEvent(new FolderSelectedEvent(this, this, FolderSelectedEvent.PARENT_FOLDER_SELECTED, this.uri.getText())));
                upOneFolderButton.setGraphic(changeButtonImage(ImageURIs.upOneFolderIcon));
                upOneFolderButton.setUserData(true);
            } else if (!on & (boolean)upOneFolderButton.getUserData()){
                upOneFolderButton.setOnAction(null);
                upOneFolderButton.setGraphic(changeButtonImage(ImageURIs.upOneFolderIcon));
                upOneFolderButton.setUserData(false);
            }
        }
    }


    public String getUriText(){
        return uri.getText();
    }

    public void setPreviousUriPath(String uri){
        System.out.println(uri+"@setPreviousUriPath");
        if(this.uri!=null) {
            int indexOfNow = stack.indexOf("#" + uri);
            String newURI = stack.get(indexOfNow-1);
            switchBackButton(indexOfNow-1 > 0);
            switchUpOneFolderButton(!newURI.equals("E:\\Beloghos"));
            switchForwardButton(true);
            stack.set(indexOfNow, uri);
            stack.set(indexOfNow - 1, "#"+newURI);
            this.uri.setText(newURI);
        }
    }

    public void setNextUriPath(String uri){
        System.out.println(uri+"@setNextUriPath");
        if(this.uri!=null) {
            int indexOfNow = stack.indexOf("#" + uri);
            String newURI = stack.get(indexOfNow+1);
            switchForwardButton(indexOfNow+1 < stack.size()-1);
            switchUpOneFolderButton(!newURI.equals("E:\\Beloghos"));
            switchBackButton(true);
            stack.set(indexOfNow, uri);
            stack.set(indexOfNow + 1, "#"+newURI);
            this.uri.setText(newURI);
        }
    }

    public void setParentFolder(String uri){
        System.out.println(uri+"@setParentFolder");
        if(this.uri!=null ) {
            String newURI = (new java.io.File(uri)).getParent();
            switchUpOneFolderButton(!newURI.equals("E:\\Beloghos"));
            switchBackButton(true);
            switchForwardButton(false);
            int indexOfNow = stack.indexOf("#" + uri);
//            System.out.println(newURI+"@#"+indexOfNow);
            if (indexOfNow < stack.size() - 1)
                stack = new ArrayList<>(stack.subList(0, indexOfNow+1));
            stack.set(indexOfNow, uri);
            stack.add("#" + newURI);
            this.uri.setText(newURI);
        }
    }

    public void setNewText(String newURI){
        System.out.println(newURI +"@setNewText");
        if(this.uri!=null){
            if(newURI.equals("E:\\Beloghos")){
                if((boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(false);
            } else if(!(boolean)upOneFolderButton.getUserData()) switchUpOneFolderButton(true);

            if(!(boolean)backButton.getUserData()) switchBackButton(true);
            if((boolean)forwardButton.getUserData()) switchForwardButton(false);

            int indexOfNow = stack.indexOf("#"+this.uri.getText());
            if(indexOfNow < stack.size()-1)
                stack = new ArrayList<>(stack.subList(0,indexOfNow+1));
            stack.set(indexOfNow,this.uri.getText());
            stack.add("#"+ newURI);
            this.uri.setText(newURI);

        }
    }

}