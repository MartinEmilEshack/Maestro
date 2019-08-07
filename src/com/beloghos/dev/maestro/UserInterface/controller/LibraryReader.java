package com.beloghos.dev.maestro.userInterface.controller;

import com.beloghos.dev.maestro.Job.Task;
import com.beloghos.dev.maestro.Main;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

public class LibraryReader {

    private Task task;

    private SimpleFileVisitor<Path> fileVisitor;

    private AtomicInteger filesNumber;
    private AtomicInteger filesRead;

    public LibraryReader() {

        task = new Task(Task.Priority.LOW);
        fileVisitor = new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
//                File file = dir.toFile();
//                if (file.isDirectory()) {
//                    String[] files = file.list();
//                    filesNumber.set(files != null ? files.length : 0);
//                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
//                if (isTrack(file.toFile())) {
//
//                }
                    filesRead.getAndIncrement();
//                    task.updateProgress(filesRead.get(), filesNumber.get());
                    task.updateMessage(filesRead.toString());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
//                File file = dir.toFile();
//                if(file.isDirectory()){
//                    String[] files = file.list();
//                    filesRead.getAndAdd(files != null ? files.length : 0);
//                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                if (exc != null)
                    System.out.println("error when accessing Directory: " + file + " exception -> " + exc);
                return FileVisitResult.CONTINUE;
            }

        };
        filesNumber = new AtomicInteger(0);
        filesRead = new AtomicInteger(0);

    }

    private boolean isTrack(File file) {
        if (file.isFile()) {
            String path = file.getPath();
            int dotIndex = path.lastIndexOf(".");
            return (dotIndex != -1) &&
                    (path.substring(dotIndex).equals(".mp3") ||
                            path.substring(dotIndex).equals(".wav") ||
                            path.substring(dotIndex).equals(".m4a"));
        }
        return false;
    }

    public ReadOnlyDoubleProperty readProgress() {
        return task.progressProperty();
    }

    public ReadOnlyStringProperty filesFoundProgress() {
        return task.messageProperty();
    }

    public void whenFinishedDo(Task.Method todo) {
        task.setOnSucceeded(workerStateEvent -> {
            workerStateEvent.consume();
            todo.task();
            task.updateProgress(0,0);});
    }

    public void startReading(String folder) {

        task.is(() -> {
            try {
                Files.walkFileTree(Paths.get(folder), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Main.WORKER.execute(task);

    }

}
