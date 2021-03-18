package com.bebs.wardrobepicker;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WardrobePicker extends Application {

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    // Creates a thread pool manager
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            NUMBER_OF_CORES,       // Initial pool size
            NUMBER_OF_CORES,       // Max pool size
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue
    );

    public static DatabaseSetup dbSetup;

    public static class DatabaseSetup{
        public DatabaseClothing clothDatabase;
        public DatabaseSetup() {}
    }

    @Override
    public void onCreate(){
        super.onCreate();
        dbSetup = new DatabaseSetup();
        dbSetup.clothDatabase = Room.databaseBuilder(this, DatabaseClothing.class, "ClothDatabase").build();
    }

    //TODO Cleanup Code all throughout the app

    //TODO Put Strings and static Values from xml to res

    //TODO Change up Design (maybe make it customizable?)
}
