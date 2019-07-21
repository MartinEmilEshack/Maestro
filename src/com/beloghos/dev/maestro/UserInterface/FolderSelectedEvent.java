package com.beloghos.dev.maestro.UserInterface;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class FolderSelectedEvent extends Event {

    private String folderPath;

    public static final EventType<FolderSelectedEvent> FOLDER_SELECTED_TYPE = new EventType<>(Event.ANY,"FOLDER_SELECTED");

    public FolderSelectedEvent(Object source, EventTarget eventTarget,EventType eventType,String folderPath) {
        super(source, eventTarget, FOLDER_SELECTED_TYPE);
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public FolderSelectedEvent copyFor(Object source, EventTarget eventTarget) {
        return (FolderSelectedEvent) super.copyFor(source, eventTarget);
    }

    @Override
    public EventType<? extends FolderSelectedEvent> getEventType() {
        return (EventType<? extends FolderSelectedEvent>) super.getEventType();
    }

}
