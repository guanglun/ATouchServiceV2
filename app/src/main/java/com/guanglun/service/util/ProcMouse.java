package com.guanglun.service.util;

import static com.guanglun.service.util.EasyTool.limit;

public class ProcMouse {
    private static final int FACE_STEP  = 400;
    private static final int WATCH_STEP = 400;

    private boolean is_watch = false;
    private int te_face = -1;
    private int face_x = 0, face_y = 0;

    private DeviceMgmt mgmt;

    public void init(DeviceMgmt mgmt)
    {
        this.mgmt = mgmt;
    }

    public void reset()
    {
        if(te_face != -1)
        {
            mgmt.atouch.up(te_face);
            te_face = -1;
        }
    }

    private int te_attack = -1,te_aim = -1;

    void procMouse(byte[] buf, int len)
    {

        Touch touch = new Touch();
        byte[] temp = new byte[200],temp2 = new byte[10];

        if ((te_face == -1) && ((buf[0] & 0x04) == 0x04))
        {

            if (!is_watch)
            {
                face_x = mgmt.s_pubg.N13_FaceX;
                face_y = mgmt.s_pubg.N14_FaceY;
                touch.startX = mgmt.s_pubg.N13_FaceX;
                touch.startY = mgmt.s_pubg.N14_FaceY;
                touch.type = Touch.TOUCH_NORMAL;
            }
            else
            {
                face_x = mgmt.s_pubg.N15_WatchX;
                face_y = mgmt.s_pubg.N16_WatchY;
                touch.startX = mgmt.s_pubg.N15_WatchX;
                touch.startY = mgmt.s_pubg.N16_WatchY;
                touch.type = Touch.TOUCH_NORMAL;
            }

            te_face = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

            temp2[0] = 0x00;
            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte)0x02, temp2, 1);
            mgmt.atouchSend(bytes);
        }
        else if ((te_face != -1) && ((buf[0] & 0x04) == 0x04))
        {

            mgmt.atouch.up(te_face);
            te_face = -1;

            temp2[0] = 0x01;
            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte)0x02, temp2, 1);
            mgmt.atouchSend(bytes);
        }

        if (te_face != -1)
        {

            face_x += buf[1];
            face_y += buf[2];

            //LOG("%d\t%d\t%d\t%d\r\n",face_x,face_y,(signed char)buf[1],(signed char)buf[2]);

            if (!is_watch)
            {
                if ( (face_x < (mgmt.s_pubg.N13_FaceX - FACE_STEP)) || (face_x > (mgmt.s_pubg.N13_FaceX + FACE_STEP)) ||
                        (face_y < (mgmt.s_pubg.N14_FaceY - FACE_STEP)) || (face_y > (mgmt.s_pubg.N14_FaceY + FACE_STEP)))
                {
                    mgmt.atouch.up(te_face);
                    te_face = -1;
                    face_x = mgmt.s_pubg.N13_FaceX;
                    face_y = mgmt.s_pubg.N14_FaceY;
                    face_x += buf[1];
                    face_y += buf[2];
                    touch.startX = mgmt.s_pubg.N13_FaceX;
                    touch.startY = mgmt.s_pubg.N14_FaceY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_face= mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
                }
            }
            else
            {

                face_x = limit(face_x, mgmt.s_pubg.N15_WatchX - WATCH_STEP*2, mgmt.s_pubg.N15_WatchX + WATCH_STEP);
                face_y = limit(face_y, mgmt.s_pubg.N16_WatchY - WATCH_STEP, mgmt.s_pubg.N16_WatchY + WATCH_STEP);
            }

            mgmt.atouch.move(te_face,face_x, face_y);

            if ((te_attack == -1) && ((buf[0] & 0x01) == 0x01))
            {
                touch.startX = mgmt.s_pubg.N3_AttackX;
                touch.startY = mgmt.s_pubg.N4_AttackY;
                touch.type = Touch.TOUCH_NORMAL;

                te_attack = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

            }
            else if ((te_attack != -1) && ((buf[0] & 0x01) == 0x00))
            {

                mgmt.atouch.up(te_attack);
                te_attack = -1;
            }

            if ((te_aim == -1) && ((buf[0] & 0x02) == 0x02))
            {
                touch.startX = mgmt.s_pubg.N25_AimX;
                touch.startY = mgmt.s_pubg.N26_AimY;
                touch.type = Touch.TOUCH_NORMAL;

                te_aim = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

            }
            else if ((te_aim != -1) && ((buf[0] & 0x02) == 0x00))
            {
                mgmt.atouch.up(te_aim);
                te_aim = -1;
            }
        }
        else
        {
            byte[] bytes = mgmt.atouchRecv.atouchCreatCmd((byte)0x01, buf, 4);
            mgmt.atouchSend(bytes);
        }
    }

    void set_watch_status(boolean isdown)
    {
        Touch touch = new Touch();
        is_watch = isdown;
        if (is_watch && te_face != -1)
        {
            mgmt.atouch.up(te_face);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            face_x = mgmt.s_pubg.N15_WatchX;
            face_y = mgmt.s_pubg.N16_WatchY;

            touch.startX = mgmt.s_pubg.N15_WatchX;
            touch.startY = mgmt.s_pubg.N16_WatchY;
            touch.type = Touch.TOUCH_NORMAL;

            te_face= mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
        }
        else if ((!is_watch) && te_face != -1)
        {
            mgmt.atouch.up(te_face);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            face_x = mgmt.s_pubg.N13_FaceX;
            face_y = mgmt.s_pubg.N14_FaceY;

            touch.startX = mgmt.s_pubg.N13_FaceX;
            touch.startY = mgmt.s_pubg.N14_FaceY;
            touch.type = Touch.TOUCH_NORMAL;

            te_face= mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
        }
    }
}
