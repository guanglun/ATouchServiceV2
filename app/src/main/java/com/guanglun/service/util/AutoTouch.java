package com.guanglun.service.util;

import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.MotionEvent;

import com.guanglun.service.DBManager.DBControlMapUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AutoTouch {

    public static final int TOUCH_NUM               = 9;
    public static final int TOUCH_ERROR_ID          = 255;

    private Map<Integer, Touch> mapTouch = new HashMap<>();

    private ArrayList<MotionEvent.PointerProperties> pplist = new ArrayList<MotionEvent.PointerProperties>();
    private ArrayList<MotionEvent.PointerCoords> pclist = new ArrayList<MotionEvent.PointerCoords>();

    private TouchCallback cb;
    private MotionEvent event;

    private Thread threadTouch = null;


    public interface TouchCallback {
        void onTouch(MotionEvent event);
    }

    public AutoTouch(TouchCallback cb)
    {

        this.cb = cb;
        threadTouch = new Thread(touchRunnable);
        threadTouch.start();
    }

    public void clear()
    {
        for(Touch touch : mapTouch.values())
        {
            up(touch.id);
        }
        mapTouch.clear();
    }

    private Runnable touchRunnable = new Runnable() {
        @Override
        public synchronized void run() {

            int x, y;

            while(true)
            {
                for(Touch touch : mapTouch.values())
                {
                    if(touch != null)
                    {
                        if(touch.type != Touch.TOUCH_NORMAL)
                        {
                            //Log.i(EasyTool.TAG, "move " + touch.id + " " + touch.stepCount);
                            if (touch.stepCount < touch.step)
                            {
                                touch.stepCount++;

                                x = touch.startX + (touch.endX - touch.startX) * touch.stepCount / touch.step;
                                y = touch.startY + (touch.endY - touch.startY) * touch.stepCount / touch.step;


                                move(touch.id, x, y);

                                mapTouch.put(touch.id,touch);
                            }
                            else
                            {
                                if (touch.type == Touch.TOUCH_MOVE_UP)
                                {
                                    up(touch.id);
                                    break;
                                }
                                else if (touch.type == Touch.TOUCH_MOVE_NORMAL)
                                {
                                    touch.type = Touch.TOUCH_NORMAL;

                                    mapTouch.put(touch.id,touch);
                                }
                            }


                        }
                    }
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private MotionEvent.PointerProperties getPointerProperties()
    {
        MotionEvent.PointerProperties pointerProps = new MotionEvent.PointerProperties();
        pointerProps.id = 0;
        pointerProps.toolType = MotionEvent.TOOL_TYPE_FINGER;
        return pointerProps;
    }

    private MotionEvent.PointerCoords getPointerCoords()
    {
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.orientation = 0;
        pointerCoords.pressure = 1; // pressure and size have to be set
        pointerCoords.size = 1;
        pointerCoords.orientation = 0;
        return pointerCoords;
    }

    private int getId()
    {
        int id = 0;
        for(; id<TOUCH_NUM; id++)
        {
            if(mapTouch.get(id) == null)
            {
                return id;
            }
        }
        return TOUCH_ERROR_ID;
    }

    private int getPointerAction(int motionEnvent, int index) {
        return motionEnvent + (index << MotionEvent.ACTION_POINTER_INDEX_SHIFT);
    }

    public int down(int type,int startX,int startY,int endX,int endY,int step) {
        long now = SystemClock.uptimeMillis();
        Touch touch = new Touch(type,startX,startY,endX,endY,step);

        int id = TOUCH_ERROR_ID;
        int size = mapTouch.size();

        if(size > TOUCH_NUM)
        {
            return TOUCH_ERROR_ID;
        }else if(size == 0)
        {
            id = 0;

            MotionEvent.PointerProperties pp = getPointerProperties();
            MotionEvent.PointerCoords pc = getPointerCoords();

            pp.id = id;
            pc.x = touch.startX;
            pc.y = touch.startY;

            pplist.add(pp);
            pclist.add(pc);

            event = MotionEvent.obtain(now, now, MotionEvent.ACTION_DOWN, 1,
                    (MotionEvent.PointerProperties[])pplist.toArray(new MotionEvent.PointerProperties[pplist.size()]),
                    (MotionEvent.PointerCoords[])pclist.toArray(new MotionEvent.PointerCoords[pclist.size()]),
                    0, 0, 1f, 1f, 0, 0,
                    InputDevice.SOURCE_TOUCHSCREEN, 0);

        }else{

            id = getId();

            if(id == TOUCH_ERROR_ID)
            {
                return TOUCH_ERROR_ID;
            }

            MotionEvent.PointerProperties pp = getPointerProperties();
            MotionEvent.PointerCoords pc = getPointerCoords();

            pp.id = id;
            pc.x = touch.startX;
            pc.y = touch.startY;

            pplist.add(id,pp);
            pclist.add(id,pc);

            event = MotionEvent.obtain(now, now, getPointerAction(MotionEvent.ACTION_POINTER_DOWN, id), size + 1,
                    (MotionEvent.PointerProperties[])pplist.toArray(new MotionEvent.PointerProperties[pplist.size()]),
                    (MotionEvent.PointerCoords[])pclist.toArray(new MotionEvent.PointerCoords[pclist.size()]),
                    0, 0, 1f, 1f, 0, 0,
                    InputDevice.SOURCE_TOUCHSCREEN, 0);

        }

        //Log.i(EasyTool.TAG, "down "+id+" " + event.getAction());

        touch.id = id;
        cb.onTouch(event);
        mapTouch.put(id,touch);



        return id;
    }

    public void move(int id, int x, int y)
    {
        long now = SystemClock.uptimeMillis();
        Touch touch = mapTouch.get(id);
        int size = mapTouch.size();
        int index;

        //Log.i(EasyTool.TAG, "move "+id);

        if(touch != null) {

            index = 0;
            for(MotionEvent.PointerProperties pp: pplist)
            {
                if(pp.id == id)
                {
                    pclist.get(index).x = x;
                    pclist.get(index).y = y;
                    break;
                }
                index++;
            }

            event = MotionEvent.obtain(now, now, MotionEvent.ACTION_MOVE, size,
                    (MotionEvent.PointerProperties[])pplist.toArray(new MotionEvent.PointerProperties[pplist.size()]),
                    (MotionEvent.PointerCoords[])pclist.toArray(new MotionEvent.PointerCoords[pclist.size()]),
                    0, 0, 1f, 1f, 0, 0,
                    InputDevice.SOURCE_TOUCHSCREEN, 0);

            cb.onTouch(event);
        }
    }

    public void up(int id)
    {
        long now = SystemClock.uptimeMillis();
        Touch touch = mapTouch.get(id);
        int size = mapTouch.size();
        int index;

        if(touch != null)
        {
            if(size == 1)
            {
                event = MotionEvent.obtain(now, now, MotionEvent.ACTION_UP, 1,
                        (MotionEvent.PointerProperties[])pplist.toArray(new MotionEvent.PointerProperties[pplist.size()]),
                        (MotionEvent.PointerCoords[])pclist.toArray(new MotionEvent.PointerCoords[pclist.size()]),
                        0, 0, 1f, 1f, 0, 0,
                        InputDevice.SOURCE_TOUCHSCREEN, 0);

            }else
            {

                index = 0;
                for(MotionEvent.PointerProperties pp: pplist)
                {
                    if(pp.id == id)
                    {
                        break;
                    }
                    index++;
                }

                event = MotionEvent.obtain(now, now, getPointerAction(MotionEvent.ACTION_POINTER_UP, index), size,
                        (MotionEvent.PointerProperties[])pplist.toArray(new MotionEvent.PointerProperties[pplist.size()]),
                        (MotionEvent.PointerCoords[])pclist.toArray(new MotionEvent.PointerCoords[pclist.size()]),
                        0, 0, 1f, 1f, 0, 0,
                        InputDevice.SOURCE_TOUCHSCREEN, 0);

            }

            //Log.i(EasyTool.TAG, "up "+id+" " + event.getAction() + " " + size);

            mapTouch.remove(id);

            index = 0;
            for(MotionEvent.PointerProperties pp: pplist)
            {

                if(pp.id == id)
                {
                    pplist.remove(index);
                    pclist.remove(index);
                    break;
                }
                index++;
            }

            cb.onTouch(event);
        }


    }
}
