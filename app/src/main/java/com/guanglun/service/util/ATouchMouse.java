package com.guanglun.service.util;

import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;

public class ATouchMouse {

    private MotionEvent event;

    private Thread threadTouch = null;

    private TouchCallback cb;

    private MotionEvent.PointerProperties[]   ppl = new MotionEvent.PointerProperties[1];
    private MotionEvent.PointerCoords[]       pcl = new MotionEvent.PointerCoords[1];

    private MotionEvent.PointerProperties pp = new MotionEvent.PointerProperties();
    private MotionEvent.PointerCoords     pc = new MotionEvent.PointerCoords();

    public interface TouchCallback {
        void onTouch(MotionEvent event);
    }

    public ATouchMouse(TouchCallback cb)
    {
        this.cb = cb;
    }

    public void enable(int x,int y)
    {
        long now = SystemClock.uptimeMillis();



        pp.id = 0;
        pp.toolType = MotionEvent.TOOL_TYPE_MOUSE;
        pc.x = x;
        pc.y = y;
        pc.size = 10;
        ppl[0] = pp;
        pcl[0] = pc;

        event = MotionEvent.obtain(0, now, MotionEvent.ACTION_HOVER_ENTER, 1,
                ppl,pcl,
                0, 0, 0f, 0f, 17, 0,
                InputDevice.SOURCE_MOUSE, 2);
        cb.onTouch(event);
    }

    public void move(int x,int y)
    {
        long now = SystemClock.uptimeMillis();

        pp.id = 0;
        pp.toolType = MotionEvent.TOOL_TYPE_MOUSE;
        pc.x = x;
        pc.y = y;
        pc.size = 10;
        ppl[0] = pp;
        pcl[0] = pc;

        event = MotionEvent.obtain(0, now, MotionEvent.ACTION_HOVER_MOVE, 1,
                ppl,pcl,
                0, 0, 0f, 0f, 17, 0,
                InputDevice.SOURCE_MOUSE, 2);
        cb.onTouch(event);
    }

    public void leftdown(float x,float y)
    {
        long now = SystemClock.uptimeMillis();

        pp.id = 0;
        pp.toolType = MotionEvent.TOOL_TYPE_MOUSE;
        pc.x = x;
        pc.y = y;
        pc.size = 10;
        ppl[0] = pp;
        pcl[0] = pc;

        event = MotionEvent.obtain(0, now, MotionEvent.ACTION_BUTTON_PRESS, 1,
                ppl,pcl,
                0, 0, 0f, 0f, 17, 0,
                InputDevice.SOURCE_MOUSE, 2);

        cb.onTouch(event);
    }
}
