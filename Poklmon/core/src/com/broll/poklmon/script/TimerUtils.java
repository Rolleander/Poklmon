package com.broll.poklmon.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public final class TimerUtils {

    private static Array<TimerTask> tasks = new Array<>();

    private TimerUtils() {

    }

    public static void timerTask(float seconds, Runnable runnable) {
        TimerTask task = new TimerTask();
        task.task = runnable;
        task.remaining = seconds;
        tasks.add(task);
    }

    public static void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Array.ArrayIterator<TimerTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            TimerTask task = iterator.next();
            task.remaining -= deltaTime;
            if (task.remaining <= 0) {
                task.task.run();
                iterator.remove();
            }
        }
    }

    private static class TimerTask {
        public float remaining;
        public Runnable task;
    }
}
