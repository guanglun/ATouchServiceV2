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

    public void init(DeviceMgmt mgmt) {
        this.mgmt = mgmt;
    }


    public void procAT(byte[] buf) {




        Touch touch = new Touch();

        if (mgmt.maplist == null) {
            return;
        }


        for (MapUnit map : mgmt.maplist) {
            if (map.MFV == MapUnit.MFV_ATOUCH) {

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
            }
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

