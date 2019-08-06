package com.beloghos.dev.maestro.userInterface.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Memorey {

    private final UriPath URI = new UriPath();
    private final ObservableList<Track> trackList = FXCollections.observableArrayList();

    public UriPath getURI() {
        return URI;
    }

    public ObservableList<Track> getTrackList() {
        return trackList;
    }



}
