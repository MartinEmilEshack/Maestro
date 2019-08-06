package com.beloghos.dev.maestro.userInterface;

import com.beloghos.dev.maestro.Main;
import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import me.marnic.jiconextract.extractor.IconSize;
import me.marnic.jiconextract.extractor.JIconExtractor;

import java.io.File;

public class FileControl extends VBox {

    private File file;

    public FileControl(File file){
        super(10);

        Background focusBackground = new Background( new BackgroundFill( Color.SKYBLUE,new CornerRadii(2), Insets.EMPTY ) );
        Background unfocusedBackground = new Background( new BackgroundFill( null, CornerRadii.EMPTY, Insets.EMPTY ) );

        setAlignment(Pos.CENTER);
        setPrefWidth(95);
        setPadding(new Insets(5,5,5,5));
        backgroundProperty().bind(
                Bindings.when(focusedProperty())
                        .then(focusBackground)
                        .otherwise(unfocusedBackground));
        setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2 && (!getFile().isFile()))
                Main.MEMORY.getURI().setPath(getFile().getPath());
//                this.fireEvent(new FolderSelectedEvent(this,this,FolderSelectedEvent.FOLDER_SELECTED,getFile().getPath()));
            else
                this.requestFocus();
        });

//        ImageView icon = new ImageView();
//        Platform.runLater(()-> icon.setImage(getIconOfFile(file)));
        ImageView icon = new ImageView(getIconOfFile(file));
        icon.setPreserveRatio(true);
        icon.setFitWidth(40);

        Label name = new Label(file.getName());
        name.setTextAlignment(TextAlignment.CENTER);
        name.minWidth(FileControl.USE_COMPUTED_SIZE);
        name.maxWidth(FileControl.USE_COMPUTED_SIZE);
        name.prefWidth(FileControl.USE_COMPUTED_SIZE);
        name.setTextOverrun(OverrunStyle.ELLIPSIS);
        name.setEllipsisString("..." + getFileExtension(file.getName()));

        getChildren().addAll(icon,name);
        this.file = file;
    }

    private Image getIconOfFile(File file){
        java.awt.image.BufferedImage bufferedImage = JIconExtractor.getJIconExtractor().extractIconFromFile(file.getPath(), IconSize.EXTRALARGE);
        return SwingFXUtils.toFXImage(bufferedImage, null);
//        java.awt.Graphics2D graphics = bufferedImage.createGraphics();
//        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics.drawImage(awtImage, 0, 0,176,176, null);
//        graphics.dispose();

//        javax.swing.Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
//        javax.swing.ImageIcon imageIcon = (javax.swing.ImageIcon) icon;
//        java.awt.Image awtImage = imageIcon.getImage();
//
//        java.awt.image.BufferedImage bufferedImage ;
//        if (awtImage instanceof java.awt.image.BufferedImage)
//            bufferedImage = (java.awt.image.BufferedImage) awtImage ;
//        else {
//            bufferedImage = new java.awt.image.BufferedImage(
//                    awtImage.getWidth(null),
//                    awtImage.getHeight(null),
//                    java.awt.image.BufferedImage.TYPE_INT_ARGB);
//            java.awt.Graphics2D graphics = bufferedImage.createGraphics();
//            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//            graphics.drawImage(awtImage, 0, 0,176,176, null);
//            graphics.dispose();
//        }
    }

    private File getFile() {
        return file;
    }

    private String getFileExtension(String name){
        int dotIndex = name.lastIndexOf(".");
        if(dotIndex == -1)
            return "";
        else
            return name.substring(dotIndex);
    }

}
