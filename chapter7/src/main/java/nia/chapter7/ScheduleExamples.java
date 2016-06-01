package nia.chapter7;

import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhujie on 16/6/1.
 */
public class ScheduleExamples {

    public static void schedule() {
        // Creates a ScheduledExecutorService with a pool of 10 threads
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        // Creates a Runnable to schedule for later execution
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                // The message to be printed by the task
                System.out.println("Now it is 60 seconds later");
            }
            // Schedules task to run 60 seconds from now
        }, 60, TimeUnit.SECONDS);
        // do something
        // Shuts down ScheduledExecutorService to release resources once the task is complete
        executor.shutdown();
    }

    public static void scheduleViaEventLoop() {
        Channel ch = null;
        // Creates a Runnable to schedule for later execution
        ScheduledFuture<?> future = ch.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                // The code to run
                System.out.println("Now its 60 seconds later");
            }
            // Schedule to run 60 seconds from now
        }, 60, TimeUnit.SECONDS);
    }

    public static void scheduleFixedViaEventLoop() {
        Channel ch = null;
        // Creates a Runnable to schedule for later execution
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // This will run until the ScheduledFuture is canceled.
                System.out.println("Run every 60 seconds");
            }
            // Schedule to run in 60 seconds and every 60 seconds thereafter
        }, 60, 60, TimeUnit.SECONDS);
    }

    public static void  cancelTask() {
        // Schedules task and obtains the returned ScheduledFuture
        ScheduledFuture<?> future = null;
        boolean mayInterruptIfRunning = false;
        // Cancels the task, which prevents it from running again.
        future.cancel(mayInterruptIfRunning);
    }

}
