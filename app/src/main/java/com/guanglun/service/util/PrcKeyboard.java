package com.guanglun.service.util;

import android.util.Log;

import com.guanglun.service.DBManager.MapUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrcKeyboard {

    private static final byte EVENT_K_DOWN = (byte) 0x00;
    private static final byte EVENT_K_UP = (byte) 0x01;
    private static final byte EVENT_K_KEEP = (byte) 0x02;

    private static final int KB_LEFT_CTRL = (int) 260;
    private static final int KB_LEFT_SHIFT = (int) 261;
    private static final int KB_LEFT_ALT = (int) 262;
    private static final int KB_LEFT_GUI = (int) 263;

    private static final int KB_RIGHT_CTRL = (int) 264;
    private static final int KB_RIGHT_SHIFT = (int) 265;
    private static final int KB_RIGHT_ALT = (int) 266;
    private static final int KB_RIGHT_GUI = (int) 267;

    private static final int MOVE_STEP = 150;

    private Map<Integer, KBEvent> mapKB = new HashMap<>();
    private Map<Integer, KBEvent> mapKBFlag = new HashMap<>();

    private DeviceMgmt mgmt;

    public void init(DeviceMgmt mgmt) {
        this.mgmt = mgmt;
    }

    private static class KBEvent {
        int code;
        byte event;

        public KBEvent(int code, byte event) {
            this.code = code;
            this.event = event;
        }
    }

    void getEvent(byte[] buf) {
        int i = 0;
        KBEvent event;
        if ((buf[0] & (byte) 0x01) == (byte) 0x01) {
            event = mapKB.get(KB_LEFT_CTRL);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_LEFT_CTRL, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_LEFT_CTRL, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x02) == 0x02) {
            event = mapKB.get(KB_LEFT_SHIFT);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_LEFT_SHIFT, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_LEFT_SHIFT, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x04) == 0x04) {
            event = mapKB.get(KB_LEFT_ALT);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_LEFT_ALT, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_LEFT_ALT, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x08) == 0x08) {
            event = mapKB.get(KB_LEFT_GUI);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_LEFT_GUI, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_LEFT_GUI, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x10) == 0x10) {
            event = mapKB.get(KB_RIGHT_CTRL);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_RIGHT_CTRL, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_RIGHT_CTRL, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x20) == 0x20) {
            event = mapKB.get(KB_RIGHT_SHIFT);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_RIGHT_SHIFT, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_RIGHT_SHIFT, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x40) == 0x40) {
            event = mapKB.get(KB_RIGHT_ALT);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_RIGHT_ALT, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_RIGHT_ALT, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        if ((buf[0] & 0x80) == 0x80) {
            event = mapKB.get(KB_RIGHT_GUI);
            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new KBEvent(KB_RIGHT_GUI, EVENT_K_DOWN);
                mapKB.put(event.code, event);
            }
            event = new KBEvent(KB_RIGHT_GUI, EVENT_K_DOWN);
            mapKBFlag.put(event.code, event);
        }

        for (i = 2; i < 8; i++) {
            if (buf[i] != (byte) 0x00) {
                event = mapKB.get((int)buf[i]);
                if (event != null) {
                    if (event.event == EVENT_K_DOWN) {
                        event.event = EVENT_K_KEEP;
                    } else if (event.event == EVENT_K_UP) {
                        event.event = EVENT_K_DOWN;
                    }
                } else {
                    event = new KBEvent(buf[i], EVENT_K_DOWN);
                    mapKB.put(event.code, event);
                }
                event = new KBEvent(buf[i], EVENT_K_DOWN);
                mapKBFlag.put(event.code, event);
            }
        }

        for (KBEvent e : mapKB.values()) {
            event = mapKBFlag.get(e.code);
            if (event == null) {
                e.event = EVENT_K_UP;
                mapKB.put(e.code, e);
            }
        }
        mapKBFlag.clear();
    }

    public void procKB(byte[] buf) {

        Touch touch = new Touch();

        if (mgmt.maplist == null) {
            return;
        }

        getEvent(buf);

        for (KBEvent event : mapKB.values()) {
            for (MapUnit map : mgmt.maplist) {
                if (map.MFV == MapUnit.MFV_NORMAL) {
                    if (map.KeyCode == event.code) {
                        if (map.FV0 == MapUnit.FV0_NORMAL_NORMAL) {
                            if (event.event == EVENT_K_DOWN && map.id == -1) {
                                touch.startX = map.PX;
                                touch.startY = map.PY;
                                touch.type = Touch.TOUCH_NORMAL;

                                map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

                                if(map.FV1 == MapUnit.FV1_NORMAL_MOUSE)
                                {
                                    for (MapUnit m : mgmt.maplist)
                                    {
                                        if(m.MFV == MapUnit.MFV_MOUSE && m.face != null)
                                        {
                                            mgmt.procMouse.changeMode(m);
                                        }
                                    }
                                }

                            } else if (event.event == EVENT_K_UP && map.id != -1) {
                                mgmt.atouch.up(map.id);
                                map.id = -1;

                            }
                        } else {
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
                                
                                if(map.FV1 == MapUnit.FV1_NORMAL_MOUSE)
                                {
                                    for (MapUnit m : mgmt.maplist)
                                    {
                                        if(m.MFV == MapUnit.MFV_MOUSE && m.face != null)
                                        {
                                            mgmt.procMouse.changeMode(m);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (map.MFV == MapUnit.MFV_PUBG) {
                    if (map.slide == null) {
                        map.slide = new Slide();
                        map.slide.move_x = map.PX;
                        map.slide.move_y = map.PY;
                    }

                    //Log.i(EasyTool.TAG, "code : " + event.code);

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
                } else if (map.MFV == MapUnit.MFV_MOUSE) {
                    Log.i(EasyTool.TAG,map.toString());

                    if (map.KeyCode == event.code) {
                        if(event.event == EVENT_K_DOWN)
                        {
                            mgmt.procMouse.changeMode(map);
                        }
                    }

                    if (map.FV0 == event.code) {
                        if(event.event == EVENT_K_DOWN)
                        {
                            if(map.face != null)
                            {
                                mgmt.procMouse.set_watch_status(map,true);
                            }
                        }else if(event.event == EVENT_K_UP)
                        {
                            if(map.face != null)
                            {
                                mgmt.procMouse.set_watch_status(map,false);
                            }
                        }
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
        for (KBEvent event : mapKB.values()) {
            if (event.event == EVENT_K_UP) {
                removeList.add(event.code);

            }
        }

        for (Integer code : removeList) {
            mapKB.remove(code);
        }

    }


    public static class Slide {
        private int move_x, move_y;
        private boolean is_left_shift_down = false;
    }
}

