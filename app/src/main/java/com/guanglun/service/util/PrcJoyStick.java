package com.guanglun.service.util;

import android.util.Log;

import com.guanglun.service.DBManager.MapUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrcJoyStick {

    private static final int JS_CODE_LT =  600;
    private static final int JS_CODE_LD =  601;
    private static final int JS_CODE_LL =  602;
    private static final int JS_CODE_LR =  603;

    private static final int JS_CODE_RT =  604;
    private static final int JS_CODE_RD =  605;
    private static final int JS_CODE_RL =  606;
    private static final int JS_CODE_RR =  607;

    private static final int JS_CODE_B1 =  608;
    private static final int JS_CODE_B2 =  609;
    private static final int JS_CODE_B3 =  610;
    private static final int JS_CODE_B4 =  611;

    private static final byte EVENT_K_DOWN = 0x00;
    private static final byte EVENT_K_UP = 0x01;
    private static final byte EVENT_K_KEEP = 0x02;

    private static final int MOVE_STEP = 150;

    private DeviceMgmt mgmt;

    public void init(DeviceMgmt mgmt) {
        this.mgmt = mgmt;
    }



    private static class JSEvent {
        int code;
        byte event;

        public JSEvent(int code, byte event) {
            this.code = code;
            this.event = event;
        }
    }

    private Map<Integer, JSEvent> mapJS = new HashMap<>();
    private Map<Integer, JSEvent> mapJSFlag = new HashMap<>();

    void getEvent(byte[] buf) {
        int i = 0;
        JSEvent event;
        if ((buf[5] & 0x0F) == 0x00 || (buf[5] & 0x0F) == 0x01  || (buf[5] & 0x0F) == 0x07) {
            event = mapJS.get(JS_CODE_LT);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_LT, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_LT, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x0F) == 0x02 || (buf[5] & 0x0F) == 0x01  || (buf[5] & 0x0F) == 0x03) {
            event = mapJS.get(JS_CODE_LR);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_LR, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_LR, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x0F) == 0x04 || (buf[5] & 0x0F) == 0x03  || (buf[5] & 0x0F) == 0x05) {
            event = mapJS.get(JS_CODE_LD);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_LD, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_LD, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x0F) == 0x06 || (buf[5] & 0x0F) == 0x05  || (buf[5] & 0x0F) == 0x07) {
            event = mapJS.get(JS_CODE_LL);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_LL, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_LL, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }

        if ((buf[5] & 0x10) == 0x10) {
            event = mapJS.get(JS_CODE_RT);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_RT, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_RT, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x20) == 0x20) {
            event = mapJS.get(JS_CODE_RR);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_RR, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_RR, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x40) == 0x40) {
            event = mapJS.get(JS_CODE_RD);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_RD, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_RD, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[5] & 0x80) == 0x80) {
            event = mapJS.get(JS_CODE_RL);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_RL, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_RL, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }

        if ((buf[6] & 0x04) == 0x04) {
            event = mapJS.get(JS_CODE_B1);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_B1, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_B1, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[6] & 0x01) == 0x01) {
            event = mapJS.get(JS_CODE_B2);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_B2, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_B2, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[6] & 0x08) == 0x08) {
            event = mapJS.get(JS_CODE_B3);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_B3, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_B3, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }
        if ((buf[6] & 0x02) == 0x02) {
            event = mapJS.get(JS_CODE_B4);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new JSEvent(JS_CODE_B4, EVENT_K_DOWN);
                mapJS.put(event.code, event);
            }
            event = new JSEvent(JS_CODE_B4, EVENT_K_DOWN);
            mapJSFlag.put(event.code, event);
        }


        for (JSEvent e : mapJS.values()) {
            event = mapJSFlag.get(e.code);
            if (event == null) {
                e.event = EVENT_K_UP;
                mapJS.put(e.code, e);
            }
        }
        mapJSFlag.clear();
    }

    public void procJS(byte[] buf) {

        Touch touch = new Touch();

        if (mgmt.maplist == null) {
            return;
        }

        getEvent(buf);

        for (MapUnit map : mgmt.maplist) {
                if (map.MFV == MapUnit.MFV_NORMAL) {
                    if (map.FV0 == MapUnit.FV0_NORMAL_NORMAL) {
                        for (JSEvent event : mapJS.values()) {
                                if (event.code == map.KeyCode) {
                                    if ((map.id == -1) && event.event == EVENT_K_DOWN) {
                                        touch.startX = map.PX;
                                        touch.startY = map.PY;
                                        touch.type = Touch.TOUCH_NORMAL;

                                        map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

                                    } else if ((map.id != -1) && event.event == EVENT_K_UP) {

                                        mgmt.atouch.up(map.id);
                                        map.id = -1;
                                    }
                                }
                            }
                    }
                    else if (map.FV0 == MapUnit.FV0_NORMAL_LONG) {
                        for (JSEvent event : mapJS.values()) {
                                if (event.code == map.KeyCode) {
                                    if (event.event == EVENT_K_DOWN) {
                                        if (map.id == -1) {
                                            touch.startX = map.PX;
                                            touch.startY = map.PY;
                                            touch.type = Touch.TOUCH_NORMAL;

                                            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                                        } else {
                                            mgmt.atouch.up(map.id);
                                            map.id = -1;
                                        }
                                    }
                                }
                            }
                    }
                }
                else if (map.MFV == MapUnit.MFV_PUBG) {
                    for (JSEvent event : mapJS.values()) {

                        //Log.i(EasyTool.TAG,map.KeyCode +" "+event.code);

                        if (map.slide == null) {
                            map.slide = new PrcKeyboard.Slide();
                            map.slide.move_x = map.PX;
                            map.slide.move_y = map.PY;
                        }

                        //top
                        if (map.KeyCode == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.slide.move_y -= MOVE_STEP;
                            } else if (event.event == EVENT_K_UP) {
                                map.slide.move_y += MOVE_STEP;
                            }
                        }

                        //back
                        if (map.FV4 == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.slide.move_y += MOVE_STEP;
                            } else if (event.event == EVENT_K_UP) {
                                map.slide.move_y -= MOVE_STEP;
                            }
                        }

                        //left
                        if (map.FV2 == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.slide.move_x -= MOVE_STEP;
                            } else if (event.event == EVENT_K_UP) {
                                map.slide.move_x += MOVE_STEP;
                            }
                        }

                        //right
                        if (map.FV3 == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.slide.move_x += MOVE_STEP;
                            } else if (event.event == EVENT_K_UP) {
                                map.slide.move_x -= MOVE_STEP;
                            }
                        }

                        //sup
                        if (map.FV5 == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.slide.is_left_shift_down = true;
                            } else if (event.event == EVENT_K_UP) {
                                map.slide.is_left_shift_down = false;
                            }
                        }
                    }
                }
                else if (map.MFV == MapUnit.MFV_ROCKER){

                    if (map.rocker == null) {
                        map.rocker = new Rocker();
                        map.rocker.face_x = map.PX;
                        map.rocker.face_y = map.PY;
                    }

                    for (JSEvent event : mapJS.values()) {

                        if (map.FV0 == event.code) {
                            if (event.event == EVENT_K_DOWN) {
                                map.rocker.is_watch = true;
                                if (map.id != -1) {
                                    mgmt.atouch.up(map.id);
                                    touch.startX = map.FV1;
                                    touch.startY = map.FV2;
                                    map.rocker.oldy = 0;
                                    map.rocker.oldy = 0;
                                    touch.type = Touch.TOUCH_NORMAL;
                                    map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                                }
                            }else if (event.event == EVENT_K_UP){
                                map.rocker.is_watch = false;
                                if (map.id != -1) {
                                    mgmt.atouch.up(map.id);
                                    touch.startX = map.PX;
                                    touch.startY = map.PY;

                                    map.rocker.oldy = 0;
                                    map.rocker.oldy = 0;
                                    touch.type = Touch.TOUCH_NORMAL;
                                    map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                                }
                            }
                        }
                    }

                    int x= -128,y= -128;

                    if(map.FV3 == 0)//左摇杆
                    {
                        x = buf[0];
                        y = buf[1];
                    }else if(map.FV3 == 1)//右摇杆
                    {
                        x = buf[3];
                        y = buf[4];
                    }

                    if(x == -128 && y == -128)
                    {
                        if(map.id != -1)
                        {
                            mgmt.atouch.up(map.id);
                            map.id = -1;
                        }
                    }else
                    {

                        if(map.id == -1)
                        {
                            if(map.rocker.is_watch)
                            {
                                touch.startX = map.FV1;
                                touch.startY = map.FV2;
                            }else {
                                touch.startX = map.PX;
                                touch.startY = map.PY;
                            }
                            map.rocker.oldy = 0;
                            map.rocker.oldy = 0;
                            touch.type = Touch.TOUCH_NORMAL;
                            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                        }else{

                            if(x>=0)
                            {
                                x=127-x;
                                if(x < map.rocker.oldx)
                                {
                                    x = map.rocker.oldx;
                                }
                            }else{
                                x=-127-x;
                                if(x > map.rocker.oldx)
                                {
                                    x = map.rocker.oldx;
                                }
                            }

                            if(y>=0)
                            {
                                y=127-y;
                                if(y < map.rocker.oldy)
                                {
                                    y = map.rocker.oldy;
                                }
                            }else{
                                y=-127-y;
                                if(y > map.rocker.oldy)
                                {
                                    y = map.rocker.oldy;
                                }
                            }

                            if(map.rocker.is_watch)
                            {
                                touch.startX = map.FV1 - x/2;
                                touch.startY = map.FV2 - y/2;
                            }else{
                                touch.startX = map.PX - x/2;
                                touch.startY = map.PY - y/2;
                            }

                            map.rocker.oldx = x;
                            map.rocker.oldy = y;

                            mgmt.atouch.move(map.id,touch.startX,touch.startY);
                        }
                    }
                }
        }

        for (MapUnit map : mgmt.maplist) {
            if (map.MFV == MapUnit.MFV_PUBG && map.slide != null) {

                if ((map.slide.move_x != map.PX || map.slide.move_y != map.PY) && map.id == -1) {
                    if (map.slide.is_left_shift_down && map.slide.move_y == map.PY - MOVE_STEP && map.slide.move_x == map.PX) //shift
                    {
                        touch.startX = map.PX;
                        touch.startY = map.PY;
                        touch.type = Touch.TOUCH_MOVE_NORMAL;
                        touch.endX = map.FV0;
                        touch.endY = map.FV1;
                        touch.step = 2;
                    } else {
                        touch.startX = map.PX;
                        touch.startY = map.PY;
                        touch.type = Touch.TOUCH_MOVE_NORMAL;
                        touch.endX = map.slide.move_x;
                        touch.endY = map.slide.move_y;
                        touch.step = 2;
                    }

                    map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                } else if (map.slide.move_x == map.PX && map.slide.move_y == map.PY && map.id != -1) {
                    mgmt.atouch.move(map.id, map.PX, map.PY - MOVE_STEP);

                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mgmt.atouch.up(map.id);
                    map.id = -1;
                }else if (map.id != -1)
                {
                    if (map.slide.is_left_shift_down && map.slide.move_y == map.PY - MOVE_STEP && map.slide.move_x == map.PX) //shift
                    {
                        mgmt.atouch.move(map.id,map.FV0, map.FV1);
                    }
                    else
                    {
                        mgmt.atouch.move(map.id,map.slide.move_x, map.slide.move_y);
                    }
                }
            }
        }

        List<Integer> removeList = new ArrayList<>();
        for (JSEvent event : mapJS.values()) {
            if (event.event == EVENT_K_UP) {
                removeList.add(event.code);

            }
        }

        for (Integer code : removeList) {
            mapJS.remove(code);
        }

    }
    public class Rocker {
        public boolean is_watch = false;
        public int face_x = 0, face_y = 0;
        public int oldx,oldy;
    }

}

