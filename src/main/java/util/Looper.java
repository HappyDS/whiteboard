package util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Yangzhe Xie
 * @date 6/10/19
 */
public class Looper {
    private volatile boolean isWorking = true;
    private final BlockingQueue<Runnable> taskQueue;
    private WorkerThread thread;

    public Looper() {
        taskQueue = new ArrayBlockingQueue<>(10);
        thread = new WorkerThread();
        thread.start();
    }

    /**
     * Worker thread
     */
    class WorkerThread extends Thread {
        Runnable task = null;

        @Override
        public void run() {
            try {
                while (isWorking) {
                    if (isInterrupted()) {
                        return;
                    }
                    task = taskQueue.take();
                    task.run();
                    task = null;
                }
            } catch (InterruptedException e) {
                System.out.println("Lopper stopped");
            }
        }
    }

    public boolean post(Runnable task) {
        try {
            if (isWorking) {
                taskQueue.put(task);
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stop() {
        isWorking = false;
        thread.interrupt();
    }
}
