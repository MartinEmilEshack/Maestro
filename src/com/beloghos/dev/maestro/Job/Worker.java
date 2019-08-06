package com.beloghos.dev.maestro.Job;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Worker {

    /**
     * for reference visit https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ThreadPoolExecutor.html
     * or https://stackoverflow.com/questions/3545623/how-to-implement-priorityblockingqueue-with-threadpoolexecutor-and-custom-tasks
     */
    private ThreadPoolExecutor exe;

    public Worker() {
        exe = new ThreadPoolExecutor(
                4,
                8,
                (long) 5,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(
                        5,
                        Comparator.comparingInt((Runnable task) -> ((Task) task).getPriority()))) {
//            @Override
//            protected void beforeExecute(Thread t, Runnable r) {
//                System.out.println(t.getName()+" Thread will be initialized and will do a task of "+((Task)r).getPriority()+" priority");
//            }
//
//            @Override
//            protected void afterExecute(Runnable r, Throwable t) {
//                System.out.println(" Thread finished task of "+((Task)r).getPriority()+" priority is done");
//            }
        };
    }

//    class ThreadProducer implements ThreadFactory{
//
//        @Override
//        public Thread newThread(Runnable runnable) {
//
//            SecurityManager securityManager = System.getSecurityManager();
//            ThreadGroup threadGroup = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
//
//            switch (((Task)runnable).getPriority()){
//                case 4:
//                    Thread thread4 = new Thread(threadGroup,runnable,"MAX");
//                    thread4.setPriority(4);
//                    return thread4;
//                case 3:
//                    Thread thread3 = new Thread(threadGroup,runnable,"HIGH");
//                    thread3.setPriority(3);
//                    return thread3;
//                case 2:
//                    Thread thread2 = new Thread(threadGroup,runnable,"MEDIUM");
//                    thread2.setPriority(2);
//                    return thread2;
//                    default:
//                    Thread thread1 = new Thread(threadGroup,runnable,"LOW");
//                    thread1.setPriority(1);
//                    thread1.setDaemon(true);
//                    return thread1;
//            }
//        }
//    }

    public void execute(Task task) {
        exe.execute(task);
    }

    public void shutdown() {
//        exe.shutdown();
//        System.err.println(" shutting down");
//        try {
//            if (!exe.awaitTermination(30, TimeUnit.SECONDS)) {
//                exe.shutdownNow();
//                System.err.println(" shutdownNow!!");
//                if (!exe.awaitTermination(30, TimeUnit.SECONDS))
//                    System.err.println(" did not terminate");
//            }
//        } catch (InterruptedException ie) {
//            exe.shutdownNow();
//            Thread.currentThread().interrupt();
//        }
//
//
        try {
            exe.shutdown();
            System.err.println(" shutting down");
            exe.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("termination interrupted");
        } finally {
            if (!exe.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            exe.shutdownNow();
        }

    }

}
