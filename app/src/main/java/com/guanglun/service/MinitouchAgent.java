package com.guanglun.service;

import android.graphics.Point;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.MotionEvent;

import com.guanglun.service.compat.InputManagerWrapper;
import com.guanglun.service.util.InternalApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MinitouchAgent extends Thread {

    private static final String TAG = MinitouchAgent.class.getSimpleName();
    private static final String SOCKET = "minitouchagent";
    private static final int DEFAULT_MAX_CONTACTS = 10;
    private static final int DEFAULT_MAX_PRESSURE = 0;
    private final int width;
    private final int height;
    private LocalServerSocket serverSocket;

    private MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[2];
    private MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[2];
    private PointerEvent[] events = new PointerEvent[2];

    private final InputManagerWrapper inputManager;
    private final Handler handler;

    private class PointerEvent {
        long lastMouseDown;
        int lastX;
        int lastY;
        int action;
    }

    /**
     * Get the width and height of the display by getting the DisplayInfo through reflection
     * Using the android.hardware.display.DisplayManagerGlobal but there might be other ways.
     *
     * @return a Point whose x is the width and y the height of the screen
     */
    public static Point getScreenSize() {
        Object displayManager = InternalApi.getSingleton("android.hardware.display.DisplayManagerGlobal");
        try {
            Object displayInfo = displayManager.getClass().getMethod("getDisplayInfo", int.class)
                    .invoke(displayManager, Display.DEFAULT_DISPLAY);
            if (displayInfo != null) {
                Class<?> cls = displayInfo.getClass();
                int width = cls.getDeclaredField("logicalWidth").getInt(displayInfo);
                int height = cls.getDeclaredField("logicalHeight").getInt(displayInfo);
                return new Point(width, height);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Keep a way to start only the MinitouchAgent for debugging purpose
     */
    public static void main(String[] args) {
        //To create a Handler our main thread has to prepare the Looper
        Looper.prepare();
        Handler handler = new Handler();
        Point size = getScreenSize();
        Log.i(TAG, "getScreenSize " + size.x + " " + size.y);
        if(size != null) {
            MinitouchAgent m = new MinitouchAgent(size.x, size.y, handler);
            m.start();
            Looper.loop();
        } else {
            System.err.println("Couldn't get screen resolution");
            System.exit(1);
        }
    }

    private void injectEvent(InputEvent event) {
        handler.post(() -> inputManager.injectInputEvent(event));
    }

    private MotionEvent getMotionEvent(PointerEvent p) {
        return getMotionEvent(p,0);
    }

    private MotionEvent getMotionEvent(PointerEvent p, int idx) {
        long now = SystemClock.uptimeMillis();
        if (p.action == MotionEvent.ACTION_DOWN) {
            p.lastMouseDown = now;
        }
        MotionEvent.PointerCoords coords = pointerCoords[idx];
        double rad = Math.toRadians(90.0);
        coords.x = (float)(p.lastX * Math.cos(-rad) - p.lastY * Math.sin(-rad));
        coords.y = (width)+(float)(p.lastX * Math.sin(-rad) + p.lastY * Math.cos(-rad));
        return MotionEvent.obtain(p.lastMouseDown, now, p.action, idx+1, pointerProperties,
                pointerCoords, 0, 0, 1f, 1f, 0, 0,
                InputDevice.SOURCE_TOUCHSCREEN, 0);
    }

    private List<MotionEvent> getMotionEvent(PointerEvent p1, PointerEvent p2) {
        List<MotionEvent> combinedEvents = new ArrayList<>(2);
        long now = SystemClock.uptimeMillis();
        if (p1.action != MotionEvent.ACTION_MOVE) {
            combinedEvents.add(getMotionEvent(p1));
            combinedEvents.add(getMotionEvent(p2,1));
        } else {
            MotionEvent.PointerCoords coords1 = pointerCoords[0];
            MotionEvent.PointerCoords coords2 = pointerCoords[1];
            double rad = Math.toRadians(90.0);

            coords1.x = (float) (p1.lastX * Math.cos(-rad) - p1.lastY * Math.sin(-rad));
            coords1.y = (width) + (float) (p1.lastX * Math.sin(-rad) + p1.lastY * Math.cos(-rad));

            coords2.x = (float) (p2.lastX * Math.cos(-rad) - p2.lastY * Math.sin(-rad));
            coords2.y = (width) + (float) (p2.lastX * Math.sin(-rad) + p2.lastY * Math.cos(-rad));

            MotionEvent event = MotionEvent.obtain(p1.lastMouseDown, now, p1.action, 2, pointerProperties,
                    pointerCoords, 0, 0, 1f, 1f, 0, 0,
                    InputDevice.SOURCE_TOUCHSCREEN, 0);
            combinedEvents.add(event);
        }
        return combinedEvents;
    }

    private void sendBanner(LocalSocket clientSocket) {
        try{
            OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream());
            out.write("v 1\n");
            String resolution = String.format(Locale.US, "^ %d %d %d %d%n",
                    DEFAULT_MAX_CONTACTS, width, height, DEFAULT_MAX_PRESSURE);
            out.write(resolution);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Manages the client connection. The client is supposed to be minitouch.
     */
    private void manageClientConnection() {
        while (true) {
            Log.i(TAG, String.format("Listening on %s", SOCKET));
            LocalSocket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                Log.d(TAG, "client connected");
                sendBanner(clientSocket);
                processCommandLoop(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * processCommandLoop parses touch related commands sent by stf
     * and inject them in Android InputManager.
     * Commmands can be of type down, up, move, commit
     * Note that it currently doesn't support multitouch
     *
     * @param clientSocket the socket to read on
     */
    private void processCommandLoop(LocalSocket clientSocket) throws IOException{
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String cmd;
            int count = 0;
            while ((cmd = in.readLine()) != null) {
                try (Scanner scanner = new Scanner(cmd)) {
                    scanner.useDelimiter(" ");
                    String type = scanner.next();
                    int contact;
                    switch (type) {
                        case "c":
                            if (count == 1) {
                                injectEvent(getMotionEvent(events[0]));
                            } else if (count == 2) {
                                for (MotionEvent event : getMotionEvent(events[0], events[1])) {
                                    injectEvent(event);
                                }
                            } else {
                                System.out.println("count not manage events #" + count);
                            }
                            count = 0;
                            break;
                        case "u":
                            count++;
                            contact = scanner.nextInt();
                            events[contact].action = (contact == 0) ? MotionEvent.ACTION_UP : MotionEvent.ACTION_POINTER_2_UP;
                            break;
                        case "d":
                            count++;
                            contact = scanner.nextInt();
                            events[contact].lastX = scanner.nextInt();
                            events[contact].lastY = scanner.nextInt();
                            //scanner.nextInt(); //pressure is currently not supported
                            events[contact].action = (contact == 0) ? MotionEvent.ACTION_DOWN : MotionEvent.ACTION_POINTER_2_DOWN;
                            break;
                        case "m":
                            count++;
                            contact = scanner.nextInt();
                            events[contact].lastX = scanner.nextInt();
                            events[contact].lastY = scanner.nextInt();
                            //scanner.nextInt(); //pressure is currently not supported
                            events[contact].action = MotionEvent.ACTION_MOVE;
                            break;
                        case "w":
                            int delayMs = scanner.nextInt();
                            Thread.sleep(delayMs);
                            break;
                        default:
                            System.out.println("could not parse: " + cmd);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("could not parse: " + cmd);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MinitouchAgent(int width, int height, Handler handler) {
        this.width = width;
        this.height = height;
        this.handler = handler;
        inputManager = new InputManagerWrapper();
        MotionEvent.PointerProperties pointerProps0 = new MotionEvent.PointerProperties();
        pointerProps0.id = 0;
        pointerProps0.toolType = MotionEvent.TOOL_TYPE_FINGER;
        MotionEvent.PointerProperties pointerProps1 = new MotionEvent.PointerProperties();
        pointerProps1.id = 1;
        pointerProps1.toolType = MotionEvent.TOOL_TYPE_FINGER;
        pointerProperties[0] = pointerProps0;
        pointerProperties[1] = pointerProps1;

        MotionEvent.PointerCoords pointerCoords0 = new MotionEvent.PointerCoords();
        MotionEvent.PointerCoords pointerCoords1 = new MotionEvent.PointerCoords();
        pointerCoords0.orientation = 0;
        pointerCoords0.pressure = 1; // pressure and size have to be set
        pointerCoords0.size = 1;
        pointerCoords1.orientation = 0;
        pointerCoords1.pressure = 1;
        pointerCoords1.size = 1;
        pointerCoords[0] = pointerCoords0;
        pointerCoords[1] = pointerCoords1;

        events[0] = new PointerEvent();
        events[1] = new PointerEvent();
    }

    @Override
    public void run() {
        try {
            Log.i(TAG, String.format("creating socket %s", SOCKET));
            serverSocket = new LocalServerSocket(SOCKET);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Log.i(TAG, "guanglun test input");

//        PointerEvent event = new PointerEvent();
//        event.action = MotionEvent.ACTION_DOWN;
//        event.lastX = 500;
//        event.lastY = 500;
//
//        injectEvent(getMotionEvent(event));
//
//        event.lastX = 1000;
//        event.lastY = 1000;
//
//        injectEvent(getMotionEvent(event));

        pointerProperties[0].id = 0;
        pointerProperties[1].id = 1;

        pointerCoords[0].x = 100;
        pointerCoords[0].y = 1000;

        pointerCoords[1].x = 400;
        pointerCoords[1].y = 1000;

        long now = SystemClock.uptimeMillis();
        MotionEvent te = MotionEvent.obtain(now, now, MotionEvent.ACTION_DOWN, 1, pointerProperties,
                pointerCoords, 0, 0, 1f, 1f, 0, 0,
                InputDevice.SOURCE_TOUCHSCREEN, 0);

        injectEvent(te);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pointerProperties[0].id = 1;
        pointerProperties[1].id = 1;

        pointerCoords[0].x = 300;
        pointerCoords[0].y = 1000;


        te = MotionEvent.obtain(now, now, MotionEvent.ACTION_POINTER_DOWN, 1, pointerProperties,
                pointerCoords, 0, 0, 1f, 1f, 0, 0,
                InputDevice.SOURCE_TOUCHSCREEN, 0);

        injectEvent(te);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pointerProperties[0].id = 0;
        pointerProperties[1].id = 1;

        pointerCoords[0].x = 200;
        pointerCoords[0].y = 1100;


        te = MotionEvent.obtain(now, now, MotionEvent.ACTION_MOVE, 1, pointerProperties,
                pointerCoords, 0, 0, 1f, 1f, 0, 0,
                InputDevice.SOURCE_TOUCHSCREEN, 0);

        injectEvent(te);





        manageClientConnection();

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
