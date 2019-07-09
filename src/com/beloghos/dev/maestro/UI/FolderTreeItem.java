package com.beloghos.dev.maestro.UI;

import javafx.scene.control.TreeItem;

import java.io.File;

public class FolderTreeItem extends TreeItem<String> {

    private File file;

    FolderTreeItem(String type, File file){
        super(type);
        this.file = file;
    }

    File getFile() {
        return file;
    }

}
