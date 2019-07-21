package com.beloghos.dev.maestro.Job;

import javafx.concurrent.Task;

public class FolderOpener extends Task<Void> {

    private Method todo;

    public void setTodo(Method todo) {
        this.todo = todo;
    }

    public interface Method {
        void task();
    }

    @Override
    protected Void call() throws Exception {
        todo.task();
        return null;
    }

    @Override
    public void updateProgress(double v, double v1) {
        super.updateProgress(v, v1);
    }

}
