package com.beloghos.dev.maestro.Job;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Task extends javafx.concurrent.Task<Void> {

    private Queue<Method> todoQueue;
    private int priority;

    public Task(Priority priority){
        this.priority = priority.priority;
        todoQueue = new ConcurrentLinkedQueue<>();
    }

    public interface Method {
        void task();
    }

    public enum Priority {

        MAX(4),
        HIGH(3),
        MEDIUM(2),
        LOW(1);

        private int priority;

        Priority(int priority){
            this.priority = priority;
        }

    }

    @Override
    protected Void call() throws Exception {
        while (!todoQueue.isEmpty()) todoQueue.poll().task();
        return null;
    }

    @Override
    public void updateProgress(double v, double v1) {
        super.updateProgress(v, v1);
    }

    @Override
    public void updateProgress(long l, long l1) {
        super.updateProgress(l, l1);
    }

    @Override
    public void updateMessage(String s) {
        super.updateMessage(s);
    }

    @Override
    public void updateTitle(String s) {
        super.updateTitle(s);
    }

    @Override
    public void updateValue(Void aVoid) {
        super.updateValue(aVoid);
    }

    public void is(Method todo){
        this.todoQueue.add(todo);
    }

    public void then(Method todo){
        this.todoQueue.add(todo);
    }

    int getPriority() {
        return priority;
    }

}
