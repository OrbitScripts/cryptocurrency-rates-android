package com.orbitsoft.cryptocurrencyrates;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {

    private static final int THREAD_COUNT = 3;
    private static final long DEBUG_TIMEOUT = 5000;

    private static volatile AppExecutors INSTANCE;

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;
    private static Handler debugHandler;

    private static AppExecutors getInstance() {
        if (INSTANCE == null) {
            synchronized (AppExecutors.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppExecutors();
                }
            }
        }
        return INSTANCE;
    }

    public static Executor diskIO() {
        return getInstance().diskIO;
    }

    public static Executor networkIO() {
        return getInstance().networkIO;
    }

    public static Executor mainThread() {
        return getInstance().mainThread;
    }


    @VisibleForTesting
    AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }


    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    public static void startDebug() {
        startDebug(DEBUG_TIMEOUT);
    }

    public static void startDebug(long timeout) {
        stopDebug();
        debugHandler = new Handler();
        debugHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("AppExecutors", "NetworkIO: "+AppExecutors.networkIO().toString());
                if (debugHandler != null) {
                    debugHandler.postDelayed(this, timeout);
                }
            }
        });
    }

    public static void stopDebug() {
        if (debugHandler != null) {
            debugHandler.removeCallbacksAndMessages(null);
            debugHandler = null;
        }
    }
}
