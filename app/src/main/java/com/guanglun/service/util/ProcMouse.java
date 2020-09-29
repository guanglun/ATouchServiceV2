package com.guanglun.service.util;

import android.util.Log;

import com.guanglun.service.DBManager.MapUnit;

import java.util.Timer;
import java.util.TimerTask;

import static com.guanglun.service.util.EasyTool.limit;

public class ProcMouse {

    private static final int FACE_STEP = 400;
    private static final int WATCH_STEP = 400;

    private DeviceMgmt mgmt;

    public void init(DeviceMgmt mgmt) {
        this.mgmt = mgmt;
    }


    final static private int MOUSE_CODE_L = 301;
    final static private int MOUSE_CODE_R = 302;
    final static private int MOUSE_CODE_M = 303;

    public boolean is_mouse = true;
    public boolean is_mouse_show = false;

    private byte[] temp = new byte[10];
    private int mouse_recv_cnt = 0;

    public void changeMode(MapUnit map) {

        Touch touch = new Touch();
        if (map.face == null) {
            map.face = new Face();
            map.face.face_x = map.PX;
            map.face.face_y = map.PY;
        }

        if (map.id == -1) {

            map.face.face_x = map.PX;
            map.face.face_y = map.PY;
            touch.startX = map.PX;
            touch.startY = map.PY;
            touch.type = Touch.TOUCH_NORMAL;

            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

            temp[0] = 0x00;
            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte) 0x02, temp, 1);
            mgmt.atouchSend(bytes);

            is_mouse = false;
        } else {
            mgmt.atouch.up(map.id);
            map.id = -1;
            temp[0] = 0x01;
            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte) 0x02, temp, 1);
            mgmt.atouchSend(bytes);

            is_mouse = true;
        }

    }

    public ProcMouse(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(mouse_recv_cnt == 0 && is_mouse_show)
                {
                    temp[0] = 0x00;
                    byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte) 0x02, temp, 1);
                    if(mgmt.atouchSend(bytes))
                        is_mouse_show = false;
                }else{
                    mouse_recv_cnt = 0;
                }
            }
        };
        timer.schedule(task,2000,2000);
    }

    public boolean procMouse(byte[] buf, int len) {

        mouse_recv_cnt++;

        if(is_mouse)
        {
            if(!is_mouse_show)
            {
                temp[0] = 0x01;
                byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte) 0x02, temp, 1);
                mgmt.atouchSend(bytes);
                is_mouse_show = true;
            }

            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte) 0x01, buf, 4);
            mgmt.atouchSend(bytes);
            return false;
        }

        Touch touch = new Touch();
        if (mgmt.maplist == null) {
            return false;
        }

        for (MapUnit map : mgmt.maplist) {
            if (map.DeviceValue == MapUnit.DEVICE_VALUE_MOUSE && map.MFV == MapUnit.MFV_NORMAL) {


                if (!is_mouse) {
                    if (map.FV0 == MapUnit.FV0_NORMAL_NORMAL) {
                        if (map.KeyCode == MOUSE_CODE_L) {
                            if ((map.id == -1) && ((buf[0] & 0x01) == 0x01)) {
                                touch.startX = map.PX;
                                touch.startY = map.PY;
                                touch.type = Touch.TOUCH_NORMAL;

                                map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

                            } else if ((map.id != -1) && ((buf[0] & 0x01) == 0x00)) {

                                mgmt.atouch.up(map.id);
                                map.id = -1;
                            }
                        } else if (map.KeyCode == MOUSE_CODE_R) {
                            if ((map.id == -1) && ((buf[0] & 0x02) == 0x02)) {
                                touch.startX = map.PX;
                                touch.startY = map.PY;
                                touch.type = Touch.TOUCH_NORMAL;

                                map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

                            } else if ((map.id != -1) && ((buf[0] & 0x02) == 0x00)) {

                                mgmt.atouch.up(map.id);
                                map.id = -1;
                            }
                        } else if (map.KeyCode == MOUSE_CODE_M) {
                            if ((map.id == -1) && ((buf[0] & 0x04) == 0x04)) {
                                touch.startX = map.PX;
                                touch.startY = map.PY;
                                touch.type = Touch.TOUCH_NORMAL;

                                map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);

                            } else if ((map.id != -1) && ((buf[0] & 0x04) == 0x00)) {

                                mgmt.atouch.up(map.id);
                                map.id = -1;
                            }
                        }
                    } else if (map.FV0 == MapUnit.FV0_NORMAL_LONG) {
                        if (map.KeyCode == MOUSE_CODE_L) {
                            if ((buf[0] & 0x01) == 0x01) {
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
                        } else if (map.KeyCode == MOUSE_CODE_R) {
                            if ((buf[0] & 0x02) == 0x02) {
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
                        } else if (map.KeyCode == MOUSE_CODE_M) {
                            if ((buf[0] & 0x04) == 0x04) {
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
            else if (map.MFV == MapUnit.MFV_MOUSE) {
                if (map.face == null) {
                    map.face = new Face();
                    map.face.face_x = map.PX;
                    map.face.face_y = map.PY;
                }

                    if (map.KeyCode == MOUSE_CODE_L) {
                        if ((buf[0] & 0x01) == 0x01) {
                            changeMode(map);
                        }
                    } else if (map.KeyCode == MOUSE_CODE_R) {
                        if ((buf[0] & 0x02) == 0x02) {
                            changeMode(map);
                        }
                    } else if (map.KeyCode == MOUSE_CODE_M) {
                        if ((buf[0] & 0x04) == 0x04) {
                            changeMode(map);
                        }
                    }

                    map.face.face_x += buf[1];
                    map.face.face_y += buf[2];

                    if (!map.face.is_watch) {
                        if ((map.face.face_x < (map.PX - FACE_STEP)) || (map.face.face_x > (map.PX + FACE_STEP)) ||
                                (map.face.face_y < (map.PY - FACE_STEP)) || (map.face.face_y > (map.PY + FACE_STEP))) {
                            mgmt.atouch.up(map.id);
                            map.id = -1;
                            map.face.face_x = map.PX;
                            map.face.face_y = map.PY;
                            map.face.face_x += buf[1];
                            map.face.face_y += buf[2];
                            touch.startX = map.PX;
                            touch.startY = map.PY;
                            touch.type = Touch.TOUCH_NORMAL;

                            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
                        }
                    } else {
                        map.face.face_x = limit(map.face.face_x, map.FV1 - WATCH_STEP * 2, map.FV1 + WATCH_STEP);
                        map.face.face_y = limit(map.face.face_y, map.FV2 - WATCH_STEP, map.FV2 + WATCH_STEP);
                    }
                    mgmt.atouch.move(map.id, map.face.face_x, map.face.face_y);


            }
        }

//
//        if (te_face != -1)
//        {
//
//            face_x += buf[1];
//            face_y += buf[2];
//
//            //LOG("%d\t%d\t%d\t%d\r\n",face_x,face_y,(signed char)buf[1],(signed char)buf[2]);
//
//            if (!is_watch)
//            {
//                if ( (face_x < (mgmt.s_pubg.N13_FaceX - FACE_STEP)) || (face_x > (mgmt.s_pubg.N13_FaceX + FACE_STEP)) ||
//                        (face_y < (mgmt.s_pubg.N14_FaceY - FACE_STEP)) || (face_y > (mgmt.s_pubg.N14_FaceY + FACE_STEP)))
//                {
//                    mgmt.atouch.up(te_face);
//                    te_face = -1;
//                    face_x = mgmt.s_pubg.N13_FaceX;
//                    face_y = mgmt.s_pubg.N14_FaceY;
//                    face_x += buf[1];
//                    face_y += buf[2];
//                    touch.startX = mgmt.s_pubg.N13_FaceX;
//                    touch.startY = mgmt.s_pubg.N14_FaceY;
//                    touch.type = Touch.TOUCH_NORMAL;
//
//                    te_face= mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
//                }
//            }
//            else
//            {
//
//                face_x = limit(face_x, mgmt.s_pubg.N15_WatchX - WATCH_STEP*2, mgmt.s_pubg.N15_WatchX + WATCH_STEP);
//                face_y = limit(face_y, mgmt.s_pubg.N16_WatchY - WATCH_STEP, mgmt.s_pubg.N16_WatchY + WATCH_STEP);
//            }
//
//            mgmt.atouch.move(te_face,face_x, face_y);
//
//            if ((te_attack == -1) && ((buf[0] & 0x01) == 0x01))
//            {
//                touch.startX = mgmt.s_pubg.N3_AttackX;
//                touch.startY = mgmt.s_pubg.N4_AttackY;
//                touch.type = Touch.TOUCH_NORMAL;
//
//                te_attack = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
//
//            }
//            else if ((te_attack != -1) && ((buf[0] & 0x01) == 0x00))
//            {
//
//                mgmt.atouch.up(te_attack);
//                te_attack = -1;
//            }
//
//            if ((te_aim == -1) && ((buf[0] & 0x02) == 0x02))
//            {
//                touch.startX = mgmt.s_pubg.N25_AimX;
//                touch.startY = mgmt.s_pubg.N26_AimY;
//                touch.type = Touch.TOUCH_NORMAL;
//
//                te_aim = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
//
//            }
//            else if ((te_aim != -1) && ((buf[0] & 0x02) == 0x00))
//            {
//                mgmt.atouch.up(te_aim);
//                te_aim = -1;
//            }
//        }
//        else
//        {
//            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte)0x01, buf, 4);
//            mgmt.atouchSend(bytes);
//        }
        return true;
    }

    void set_watch_status(MapUnit map, boolean isdown) {
        Touch touch = new Touch();
        map.face.is_watch = isdown;
        if (map.face.is_watch && map.id != -1) {
            mgmt.atouch.up(map.id);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            map.face.face_x = map.FV1;
            map.face.face_y = map.FV2;

            touch.startX = map.FV1;
            touch.startY = map.FV2;
            touch.type = Touch.TOUCH_NORMAL;

            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
        } else if ((!map.face.is_watch) && map.id != -1) {
            mgmt.atouch.up(map.id);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            map.face.face_x = map.PX;
            map.face.face_y = map.PY;

            touch.startX = map.PX;
            touch.startY = map.PY;
            touch.type = Touch.TOUCH_NORMAL;

            map.id = mgmt.atouch.down(touch.type, touch.startX, touch.startY, touch.endX, touch.endY, touch.step);
        }
    }

    public class Face {
        public boolean is_watch = false;
        public int face_x = 0, face_y = 0;
    }
}

