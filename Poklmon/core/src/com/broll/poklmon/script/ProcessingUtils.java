package com.broll.poklmon.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

import org.mozilla.javascript.WrappedException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public final class ProcessingUtils {

    private ProcessingUtils() {

    }

    public static void cancel(Thread processingThread, ScriptProcessingRunnable runnable) {
        runnable.cancel();
        processingThread.interrupt();
    }

    public static Object runScript(ScriptEngine engine, String script) {
        try {
            return engine.eval(script);
        } catch (javax.script.ScriptException e) {
            handleException(e, script);
            return null;
        }
    }

    public static Object tryInvokeFunction(ScriptEngine engine, String function, Object... params) {
        Invocable invocable = (Invocable) engine;
        try {
            return invocable.invokeFunction(function, params);
        } catch (ScriptException e) {
            handleException(e, function);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return null;
    }

    public static Object invokeFunction(ScriptEngine engine, String script, String function) {
        runScript(engine, script);
        Invocable invocable = (Invocable) engine;
        try {
            return invocable.invokeFunction(function);
        } catch (ScriptException e) {
            handleException(e, script);
        } catch (NoSuchMethodException e) {
            handleException(e, script);
        }
        return null;
    }

    private static void handleException(Exception e, String script) {
        if (e.getCause() instanceof WrappedException) {
            WrappedException wrappedException = (WrappedException) e.getCause();
            if (wrappedException.getWrappedException() instanceof CancelProcessingException) {
                Gdx.app.error("ProcessingUtils", "Running script was cancelled!");
                throw new CancelProcessingException();
            }
        }
        Gdx.app.error("ProcessingUtils", "Error in Script [" + script + "]");
        throw new GdxRuntimeException(e);
    }

}
