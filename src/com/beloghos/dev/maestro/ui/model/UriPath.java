package com.beloghos.dev.maestro.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UriPath {

    private final String rootFolder = "E:\\Beloghos";
    private StringProperty path;

    public UriPath(){
        this.path = new SimpleStringProperty(rootFolder);
    }

    public String getPath() {
        return path.get();
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    public StringProperty getPathProperty(){
        return path;
    }

    public void setPathProperty(StringProperty path){
        this.path = path;
    }

    public String getRootFolder() {
        return rootFolder;
    }

}
