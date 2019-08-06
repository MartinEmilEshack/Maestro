package com.beloghos.dev.maestro.userInterface;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class FolderSelectedEvent extends Event {

    private String folderPath;

    public static final EventType<FolderSelectedEvent> FOLDER_SELECTED = new EventType<>(Event.ANY, "FOLDER_SELECTED");
    public static final EventType<FolderSelectedEvent> PREVIOUS_FOLDER_SELECTED = new EventType<>(FOLDER_SELECTED, "PREVIOUS_FOLDER_SELECTED");
    public static final EventType<FolderSelectedEvent> NEXT_FOLDER_SELECTED = new EventType<>(FOLDER_SELECTED, "NEXT_FOLDER_SELECTED");
    public static final EventType<FolderSelectedEvent> PARENT_FOLDER_SELECTED = new EventType<>(FOLDER_SELECTED,"PARENT_FOLDER_SELECTED");

    public FolderSelectedEvent(Object source, EventTarget eventTarget, EventType<FolderSelectedEvent> eventType, String folderPath) {
        super(source, eventTarget, eventType);
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public FolderSelectedEvent copyFor(Object source, EventTarget eventTarget) {
        return (FolderSelectedEvent) super.copyFor(source, eventTarget);
    }

}
