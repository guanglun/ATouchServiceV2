package com.guanglun.service.compat;

import android.view.InputEvent;
import android.view.KeyEvent;

import com.guanglun.service.util.InternalApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputManagerWrapper {
    private EventInjector eventInjector;

    public InputManagerWrapper() {
        try {
            eventInjector = new InputManagerEventInjector();
        }
        catch (UnsupportedOperationException e) {

        }
    }

    public boolean injectKeyEvent(KeyEvent event) {
        return eventInjector.injectKeyEvent(event);
    }

    public boolean injectInputEvent(InputEvent event) {
        return eventInjector.injectInputEvent(event);
    }

    private interface EventInjector {
        boolean injectKeyEvent(KeyEvent event);
        boolean injectInputEvent(InputEvent event);
    }

    /**
     * EventInjector for SDK >=16
     */
    private class InputManagerEventInjector implements EventInjector {
        public static final int INJECT_INPUT_EVENT_MODE_ASYNC = 0;
        private Object inputManager;
        private Method injector;

        public InputManagerEventInjector() {
            try {
                inputManager = InternalApi.getSingleton("android.hardware.input.InputManager");

                // injectInputEvent() is @hidden
                injector = inputManager.getClass()
                        // public boolean injectInputEvent(InputEvent event, int mode)
                        .getMethod("injectInputEvent", InputEvent.class, int.class);
            }
            catch (NoSuchMethodException e) {
                throw new UnsupportedOperationException("InputManagerEventInjector is not supported");
            }
        }

        public boolean injectKeyEvent(KeyEvent event) {
            return injectInputEvent(event);
        }

        @Override
        public boolean injectInputEvent(InputEvent event) {
            try {
                injector.invoke(inputManager, event, INJECT_INPUT_EVENT_MODE_ASYNC);
                return true;
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


}

