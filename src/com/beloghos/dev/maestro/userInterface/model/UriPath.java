package com.beloghos.dev.maestro.UserInterface.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibraryFolder {

    private StringProperty path;

    public LibraryFolder(String path){
        this.path = new SimpleStringProperty(path);
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

}
