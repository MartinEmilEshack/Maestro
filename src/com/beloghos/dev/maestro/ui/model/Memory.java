package com.beloghos.dev.maestro.ui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Memory {

    private final UriPath URI = new UriPath();
    private final ObservableList<Track> trackList = FXCollections.observableArrayList();

    public UriPath getURI() {
        return URI;
    }

    public ObservableList<Track> getTrackList() {
        return trackList;
    }


}
