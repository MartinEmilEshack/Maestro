package com.beloghos.dev.maestro.ui.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.File;

public class TrackControl {

//    TrackControl(double spacing, String name,EventHandler<ActionEvent> buttonAction){
//        super(spacing);
//        Text fileName = new Text(name);
//        Button addToPlayList = new Button("#");
//        addToPlayList.setOnAction(buttonAction);
//        getChildren().addAll(fileName,addToPlayList);
//    }

    private StringProperty trackName;

    TrackControl(File file){
//        super(50);
//        ImageView icon = new ImageView(getIconOfFile(file));
//        Text name = new Text(getFileNameWithExtension(file));
//        Button addToPlayList = new Button("#");
//        addToPlayList.setOnAction((actionEvent)-> {
//            System.out.println("Added to Playlist");
//        });
        trackName = new SimpleStringProperty(file.getName());
//        getChildren().addAll(icon,name,addToPlayList);
    }

    private Image getIconOfFile(File file){

        javax.swing.JFileChooser JFileChooser = new javax.swing.JFileChooser();
        javax.swing.Icon icon = JFileChooser.getUI().getFileView(JFileChooser).getIcon(file);
        javax.swing.ImageIcon swingIcon = (javax.swing.ImageIcon) icon;
        java.awt.Image awtImage = swingIcon.getImage();

        java.awt.image.BufferedImage bufferedImage ;
        if (awtImage instanceof java.awt.image.BufferedImage)
            bufferedImage = (java.awt.image.BufferedImage) awtImage ;
        else {
            bufferedImage = new java.awt.image.BufferedImage(
                    awtImage.getWidth(null),
                    awtImage.getHeight(null),
                    java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(awtImage, 0, 0, null);
            graphics.dispose();
        }

        return SwingFXUtils.toFXImage(bufferedImage, null);

    }

    private String getFileNameWithExtension(File file){
        int dotIndex = file.getPath().lastIndexOf(".");
        if(dotIndex == -1)
            return file.getName();
        else
            return file.getPath().substring(dotIndex);
    }

    public StringProperty getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName.set(trackName);
    }

}
