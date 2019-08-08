package com.beloghos.dev.maestro.userInterface.view;

import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;

public class FolderTreeItem extends TreeItem<String> {

    private File file;

    FolderTreeItem(String name, File file){
        super(name);
        this.file = file;
    }

    FolderTreeItem(String name,File file, ImageView image){
        super(name,image);
        this.file = file;
    }

    File getFile() {
        return file;
    }

}