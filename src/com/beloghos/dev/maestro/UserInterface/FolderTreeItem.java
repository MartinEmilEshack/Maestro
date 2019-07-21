package com.beloghos.dev.maestro.UserInterface;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;

public class FolderTreeItem extends TreeItem<String> {

    private File file;

    FolderTreeItem(String type, File file){
        super(type);
        this.file = file;
    }

    FolderTreeItem(String type,File file, ImageView image){
        super(type,image);
        this.file = file;
    }

    File getFile() {
        return file;
    }

}