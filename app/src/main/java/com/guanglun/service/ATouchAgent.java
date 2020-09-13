package com.guanglun.service;

import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.InputEvent;
import android.view.MotionEvent;

import com.guanglun.service.compat.InputManagerWrapper;
import com.guanglun.service.util.AutoTouch;
import com.guanglun.service.util.DeviceMgmt;
import com.guanglun.service.util.EasyTool;
import com.guanglun.service.util.InternalApi;
import com.guanglun.service.util.Touch;

import java.lang.reflect.InvocationTargetException;

@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ATouchAgent extends Thread {

    private final InputManagerWrapper inputManager;
    private final Handler handler;
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


    public static void main(String[] args) {

        Log.i(EasyTool.TAG, "ATouchAgent Start");

        EasyTool.checkKillThread("atouch_process");

        Looper.prepare();
        Handler handler = new Handler();
        Point size = getScreenSize();
        if(size != null) {
            ATouchAgent m = new ATouchAgent(size.x, size.y, handler);
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

    public ATouchAgent(int width, int height, Handler handler) {
        this.handler = handler;
        inputManager = new InputManagerWrapper();
    }

    @Override
    public void run() {
        AutoTouch atouch = new AutoTouch(new AutoTouch.TouchCallback() {
            @Override
            public void onTouch(MotionEvent event) {
                injectEvent(event);
            }
        }
        );

//        try {
//
//            int t1 = atouch.down(Touch.TOUCH_NORMAL,100,1000,0,0,0);
//            Thread.sleep(1000);
//
//            int t2 = atouch.down(Touch.TOUCH_NORMAL,300,1300,0,0,0);
//            Thread.sleep(1000);
//
//
//            int t3 = atouch.down(Touch.TOUCH_NORMAL,500,1600,0,0,0);
//            Thread.sleep(1000);
//
//            atouch.up(t3);
//            Thread.sleep(1000);
//
//            t3 = atouch.down(Touch.TOUCH_NORMAL,550,1650,0,0,0);
//            Thread.sleep(1000);
//
//
////            int t4 = atouch.down(Touch.TOUCH_NORMAL,700,1900,0,0,0);
////            Thread.sleep(1000);
//
//            atouch.up(t2);
//            Thread.sleep(1000);
//
//
//            t2 = atouch.down(Touch.TOUCH_NORMAL,350,1350,0,0,0);
//            Thread.sleep(1000);
//
//
//            int t5 = atouch.down(Touch.TOUCH_MOVE_UP,200,600,600,1000,500);
//            Thread.sleep(1000);
//
//
//            int t6 = atouch.down(Touch.TOUCH_MOVE_NORMAL,300,600,700,1000,500);
//            Thread.sleep(800);
//
//            int t7 = atouch.down(Touch.TOUCH_MOVE_UP,400,600,800,1000,500);
//            Thread.sleep(600);
//
//            int t8 = atouch.down(Touch.TOUCH_MOVE_NORMAL,500,600,900,1000,500);
//            Thread.sleep(400);
//
//            atouch.move(t1,200,1100);
//            Thread.sleep(1000);
//
//            atouch.move(t2,400,1400);
//            Thread.sleep(1000);
////
////            atouch.move(t3,600,1700);
////            Thread.sleep(1000);
//
////            atouch.move(t4,800,2000);
////            Thread.sleep(1000);
//
//            atouch.up(t1);
//            Thread.sleep(1000);
//            atouch.up(t2);
//            Thread.sleep(1000);
//            atouch.up(t3);
////            Thread.sleep(1000);
////            atouch.up(t4);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        DeviceMgmt mgmt = new DeviceMgmt(atouch);

        while(true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
