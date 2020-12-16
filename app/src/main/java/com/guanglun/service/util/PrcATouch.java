package com.guanglun.service.util;

import android.util.Log;

import com.guanglun.service.DBManager.MapUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.guanglun.service.util.EasyTool.limit;

public class PrcATouch {


    private DeviceMgmt mgmt;

    private static final int FACE_STEP = 400;
    private static final int WATCH_STEP = 400;

    private static final int AT_CODE_KEY0 =  800;
    private static final int AT_CODE_KEY1 =  801;
    private static final int AT_CODE_KEY2 =  802;
    private static final int AT_CODE_KEY3 =  803;
    private static final int AT_CODE_KEY4 =  804;
    private static final int AT_CODE_KEY5 =  805;
    private static final int AT_CODE_KEY6 =  806;
    private static final int AT_CODE_KEY7 =  807;
    private static final int AT_CODE_KEY8 =  808;
    private static final int AT_CODE_KEY9 =  809;
    private static final int AT_CODE_KEY10 =  810;
    private static final int AT_CODE_KEY11 =  811;
    private static final int AT_CODE_KEY12 =  812;

    private static final byte EVENT_K_DOWN = 0x00;
    private static final byte EVENT_K_UP = 0x01;
    private static final byte EVENT_K_KEEP = 0x02;

    private static final int MOVE_STEP = 150;

    public void init(DeviceMgmt mgmt) {
        this.mgmt = mgmt;
    }

    private static class ATEvent {
        int code;
        byte event;

        public ATEvent(int code, byte event) {
            this.code = code;
            this.event = event;
        }
    }

    private Map<Integer, PrcATouch.ATEvent> mapAT = new HashMap<>();
    private Map<Integer, PrcATouch.ATEvent> mapATFlag = new HashMap<>();

    void getEvent(byte[] buf) {
        int i = 0;
        PrcATouch.ATEvent event;

        if ((buf[12] & 0x01) == 0x01) {
            event = mapAT.get(AT_CODE_KEY0);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY0, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY0, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[12] & 0x02) == 0x02) {
            event = mapAT.get(AT_CODE_KEY1);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY1, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY1, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[12] & 0x04) == 0x04) {
            event = mapAT.get(AT_CODE_KEY2);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY2, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY2, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[12] & 0x08) == 0x08) {
            event = mapAT.get(AT_CODE_KEY3);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY3, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY3, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x01) == 0x01) {
            event = mapAT.get(AT_CODE_KEY4);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY4, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY4, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x02) == 0x02) {
            event = mapAT.get(AT_CODE_KEY5);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY5, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY5, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x04) == 0x04) {
            event = mapAT.get(AT_CODE_KEY6);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY6, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY6, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x08) == 0x08) {
            event = mapAT.get(AT_CODE_KEY7);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY7, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY7, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x10) == 0x10) {
            event = mapAT.get(AT_CODE_KEY8);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY8, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY8, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x20) == 0x20) {
            event = mapAT.get(AT_CODE_KEY9);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY9, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY9, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x40) == 0x40) {
            event = mapAT.get(AT_CODE_KEY10);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY10, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY10, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[13] & 0x80) == 0x80) {
            event = mapAT.get(AT_CODE_KEY11);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY11, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY11, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        if ((buf[14] & 0x01) == 0x01) {
            event = mapAT.get(AT_CODE_KEY12);

            if (event != null) {
                if (event.event == EVENT_K_DOWN) {
                    event.event = EVENT_K_KEEP;
                } else if (event.event == EVENT_K_UP) {
                    event.event = EVENT_K_DOWN;
                }
            } else {
                event = new PrcATouch.ATEvent(AT_CODE_KEY12, EVENT_K_DOWN);
                mapAT.put(event.code, event);
            }
            event = new PrcATouch.ATEvent(AT_CODE_KEY12, EVENT_K_DOWN);
            mapATFlag.put(event.code, event);
        }

        for (PrcATouch.ATEvent e : mapAT.values()) {
            event = mapATFlag.get(e.code);
            if (event == null) {
                e.event = EVENT_K_UP;
                mapAT.put(e.code, e);
            }
        }


        mapATFlag.clear();
    }

    public void procAT(byte[] buf) {




        Touch touch = new Touch();

        if (mgmt.maplist == null) {
            return;
        }

        //Log.i(EasyTool.TAG,EasyTool.bytes2hex(buf,16));

        getEvent(buf);

        for (MapUnit map : mgmt.maplist) {
            if (map.MFV == MapUnit.MFV_NORMAL) {


                if (map.FV0 == MapUnit.FV0_NORMAL_NORMAL) {
                    for (PrcATouch.ATEvent event : mapAT.values()) {
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
                    for (PrcATouch.ATEvent event : mapAT.values()) {
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
            }else if (map.MFV == MapUnit.MFV_PUBG) {
                for (PrcATouch.ATEvent event : mapAT.values()) {
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
            }else if (map.MFV == MapUnit.MFV_ATOUCH) {

                int pit = EasyTool.byte2int(buf,4)/map.FV3;
                //int rol = EasyTool.byte2int(buf,0)/40;
                int yaw = -(EasyTool.byte2int(buf,8)/map.FV4 + 18000/map.FV4);

                if (map.atouch == null) {
                    map.atouch = new ATouch();

                    map.atouch.oldpit = pit;
                    //map.atouch.oldrol = rol;
                    map.atouch.oldyaw = yaw;

                    map.atouch.initpit = pit;
                    //map.atouch.initrol = rol;
                    map.atouch.inityaw = yaw;

                    map.atouch.face_x = map.PX;
                    map.atouch.face_y = map.PY;
                }

                if(map.atouch.oldpit != pit || map.atouch.oldyaw != yaw)
                {
                    if(map.atouch.is_enable) {
                        if (map.id == -1) {
                            map.atouch.face_x = map.PX;
                            map.atouch.face_y = map.PY;
                            touch.startX = map.PX;
                            touch.startY = map.PY;
                            touch.type = Touch.TOUCH_NORMAL;
                            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                        } else {
                            if(Math.abs(yaw - map.atouch.oldyaw) > 400)
                            {
                                map.atouch.oldyaw = yaw;
                            }

                            map.atouch.face_x += (yaw - map.atouch.oldyaw);
                            map.atouch.face_y += (pit - map.atouch.oldpit);

                            map.atouch.reset_cnt++;
                            if (!map.atouch.is_watch) {
                                if ((map.atouch.face_x < (map.PX - FACE_STEP)) || (map.atouch.face_x > (map.PX + FACE_STEP)) ||
                                        (map.atouch.face_y < (map.PY - FACE_STEP)) || (map.atouch.face_y > (map.PY + FACE_STEP)) ||
                                        map.atouch.reset_cnt > 50 ) {

                                    map.atouch.reset_cnt = 0;

                                    mgmt.atouch.up(map.id);
                                    map.id = -1;
                                    map.atouch.face_x = map.PX;
                                    map.atouch.face_y = map.PY;
                                    map.atouch.face_x += (yaw - map.atouch.oldyaw);
                                    map.atouch.face_y += (pit - map.atouch.oldpit);
                                    touch.startX = map.PX;
                                    touch.startY = map.PY;
                                    touch.type = Touch.TOUCH_NORMAL;

                                    map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                                }
                            } else {
                                map.atouch.face_x = limit(map.atouch.face_x, map.FV1 - WATCH_STEP * 2, map.FV1 + WATCH_STEP);
                                map.atouch.face_y = limit(map.atouch.face_y, map.FV2 - WATCH_STEP, map.FV2 + WATCH_STEP);
                            }
                            mgmt.atouch.move(map.id, map.atouch.face_x, map.atouch.face_y);

                            Log.i(EasyTool.TAG, yaw+" "+pit+ " " + touch.startX + " " + touch.startY);
                        }
                    }else{
                        if (map.id != -1) {
                             mgmt.atouch.up(map.id);
                            map.id = -1;
                        }
                    }
                }



                map.atouch.oldpit = pit;
                //map.atouch.oldrol = rol;
                map.atouch.oldyaw = yaw;

                for (PrcATouch.ATEvent event : mapAT.values()) {
                    if (map.FV0 == event.code) {
                        if (event.event == EVENT_K_DOWN) {
                            if (map.atouch != null) {
                                map.atouch.is_enable = !map.atouch.is_enable;
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
        for (PrcATouch.ATEvent event : mapAT.values()) {
            if (event.event == EVENT_K_UP) {
                removeList.add(event.code);

            }
        }

        for (Integer code : removeList) {
            mapAT.remove(code);
        }
    }

    public class ATouch {
        public boolean is_watch = false;
        public boolean is_enable = false;
        public int face_x = 0, face_y = 0;
        public int oldpit,oldrol,oldyaw;
        public int initpit,initrol,inityaw;
        public int reset_cnt = 0;
    }


}

