package com.guanglun.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcKeyboard {

            private static final byte EVENT_K_DOWN  = (byte)0x00;
            private static final byte EVENT_K_UP  = (byte)0x01;
            private static final byte EVENT_K_KEEP  = (byte)0x02;

            private static final byte KB_KANA  = (byte)0x00;
            private static final byte KB_VOLUME_DOWN  = (byte)0x01;
            private static final byte KB_VOLUME_UP  = (byte)0x02;
            private static final byte KB_VOLUME_MUTE  = (byte)0x03;
            private static final byte KB_A  = (byte)0x04;
            private static final byte KB_B  = (byte)0x05;
            private static final byte KB_C  = (byte)0x06;
            private static final byte KB_D  = (byte)0x07;
            private static final byte KB_E  = (byte)0x08;
            private static final byte KB_F  = (byte)0x09;
            private static final byte KB_G  = (byte)0x0A;
            private static final byte KB_H  = (byte)0x0B;
            private static final byte KB_I  = (byte)0x0C;
            private static final byte KB_J  = (byte)0x0D;
            private static final byte KB_K  = (byte)0x0E;
            private static final byte KB_L  = (byte)0x0F;
            private static final byte KB_M  = (byte)0x10;
            private static final byte KB_N  = (byte)0x11;
            private static final byte KB_O  = (byte)0x12;
            private static final byte KB_P  = (byte)0x13;
            private static final byte KB_Q  = (byte)0x14;
            private static final byte KB_R  = (byte)0x15;
            private static final byte KB_S  = (byte)0x16;
            private static final byte KB_T  = (byte)0x17;
            private static final byte KB_U  = (byte)0x18;
            private static final byte KB_V  = (byte)0x19;
            private static final byte KB_W  = (byte)0x1A;
            private static final byte KB_X  = (byte)0x1B;
            private static final byte KB_Y  = (byte)0x1C;
            private static final byte KB_Z  = (byte)0x1D;
            private static final byte KB_1  = (byte)0x1E;
            private static final byte KB_2  = (byte)0x1F;
            private static final byte KB_3  = (byte)0x20;
            private static final byte KB_4  = (byte)0x21;
            private static final byte KB_5  = (byte)0x22;
            private static final byte KB_6  = (byte)0x23;
            private static final byte KB_7  = (byte)0x24;
            private static final byte KB_8  = (byte)0x25;
            private static final byte KB_9  = (byte)0x26;
            private static final byte KB_0  = (byte)0x27;
            private static final byte KB_ENTER  = (byte)0x28;
            private static final byte KB_ESCAPE  = (byte)0x29;
            private static final byte KB_BSPACE  = (byte)0x2A;
            private static final byte KB_TAB  = (byte)0x2B;
            private static final byte KB_SPACE  = (byte)0x2C;
            private static final byte KB_MINUS  = (byte)0x2D;
            private static final byte KB_EQUAL  = (byte)0x2E;
            private static final byte KB_LBRACKET  = (byte)0x2F;
            private static final byte KB_RBRACKET  = (byte)0x30;
            private static final byte KB_BSLASH  = (byte)0x31;
            private static final byte KB_NONUS_HASH  = (byte)0x32;
            private static final byte KB_SCOLON  = (byte)0x33;
            private static final byte KB_QUOTE  = (byte)0x34;
            private static final byte KB_GRAVE  = (byte)0x35;
            private static final byte KB_COMMA  = (byte)0x36;
            private static final byte KB_DOT  = (byte)0x37;
            private static final byte KB_SLASH  = (byte)0x38;
            private static final byte KB_CAPSLOCK  = (byte)0x39;
            private static final byte KB_F1  = (byte)0x3A;
            private static final byte KB_F2  = (byte)0x3B;
            private static final byte KB_F3  = (byte)0x3C;
            private static final byte KB_F4  = (byte)0x3D;
            private static final byte KB_F5  = (byte)0x3E;
            private static final byte KB_F6  = (byte)0x3F;
            private static final byte KB_F7  = (byte)0x40;
            private static final byte KB_F8  = (byte)0x41;
            private static final byte KB_F9  = (byte)0x42;
            private static final byte KB_F10  = (byte)0x43;
            private static final byte KB_F11  = (byte)0x44;
            private static final byte KB_F12  = (byte)0x45;
            private static final byte KB_PSCREEN  = (byte)0x46;
            private static final byte KB_SCROLLLOCK  = (byte)0x47;
            private static final byte KB_PAUSE  = (byte)0x48;
            private static final byte KB_INSERT  = (byte)0x49;
            private static final byte KB_HOME  = (byte)0x4A;
            private static final byte KB_PGUP  = (byte)0x4B;
            private static final byte KB_DELETE  = (byte)0x4C;
            private static final byte KB_END  = (byte)0x4D;
            private static final byte KB_PGDOWN  = (byte)0x4E;
            private static final byte KB_RIGHT  = (byte)0x4F;
            private static final byte KB_LEFT  = (byte)0x50;
            private static final byte KB_DOWN  = (byte)0x51;
            private static final byte KB_UP  = (byte)0x52;
            private static final byte KB_NUMLOCK  = (byte)0x53;
            private static final byte KB_KP_SLASH  = (byte)0x54;
            private static final byte KB_KP_ASTERISK  = (byte)0x55;
            private static final byte KB_KP_MINUS  = (byte)0x56;
            private static final byte KB_KP_PLUS  = (byte)0x57;
            private static final byte KB_KP_ENTER  = (byte)0x58;
            private static final byte KB_KP_1  = (byte)0x59;
            private static final byte KB_KP_2  = (byte)0x5A;
            private static final byte KB_KP_3  = (byte)0x5B;
            private static final byte KB_KP_4  = (byte)0x5C;
            private static final byte KB_KP_5  = (byte)0x5D;
            private static final byte KB_KP_6  = (byte)0x5E;
            private static final byte KB_KP_7  = (byte)0x5F;
            private static final byte KB_KP_8  = (byte)0x60;
            private static final byte KB_KP_9  = (byte)0x61;
            private static final byte KB_KP_0  = (byte)0x62;
            private static final byte KB_KP_DOT  = (byte)0x63;
            private static final byte KB_NONUS_BSLASH  = (byte)0x64;
            private static final byte KB_APPLICATION  = (byte)0x65;
            private static final byte KB_KP_COMMA  = (byte)0x66;
            private static final byte KB_KP_EQUAL  = (byte)0x67;
            private static final byte KB_F13  = (byte)0x68;
            private static final byte KB_F14  = (byte)0x69;
            private static final byte KB_F15  = (byte)0x6A;
            private static final byte KB_F16  = (byte)0x6B;
            private static final byte KB_F17  = (byte)0x6C;
            private static final byte KB_F18  = (byte)0x6D;
            private static final byte KB_F19  = (byte)0x6E;
            private static final byte KB_F20  = (byte)0x6F;
            private static final byte KB_F21  = (byte)0x70;
            private static final byte KB_F22  = (byte)0x71;
            private static final byte KB_F23  = (byte)0x72;
            private static final byte KB_F24  = (byte)0x73;
            private static final byte KB_JYEN  = (byte)0x74;
            private static final byte KB_RO  = (byte)0x75;
            private static final byte KB_HENK  = (byte)0x76;
            private static final byte KB_MHEN  = (byte)0x77;
            private static final byte KB_LCTRL  = (byte)0x78;
            private static final byte KB_LSHIFT  = (byte)0x79;
            private static final byte KB_LALT  = (byte)0x7A;
            private static final byte KB_LGUI  = (byte)0x7B;
            private static final byte KB_RCTRL  = (byte)0x7C;
            private static final byte KB_RSHIFT  = (byte)0x7D;
            private static final byte KB_RALT  = (byte)0x7E;
            private static final byte KB_RGUI  = (byte)0x7F;
            private static final byte KB_NO  = (byte)0x80;


            private static final byte KB_LEFT_CTRL  = (byte)0xA0;
            private static final byte KB_LEFT_SHIFT  = (byte)0xA1;
            private static final byte KB_LEFT_ALT  = (byte)0xA2;
            private static final byte KB_LEFT_GUI  = (byte)0xA3;

            private static final byte KB_RIGHT_CTRL  = (byte)0xA4;
            private static final byte KB_RIGHT_SHIFT  = (byte)0xA5;
            private static final byte KB_RIGHT_ALT  = (byte)0xA6;
            private static final byte KB_RIGHT_GUI  = (byte)0xA7;

    private static final int MOVE_STEP = 150;

    private Map<Byte, KBEvent> mapKB = new HashMap<>();
    private Map<Byte, KBEvent> mapKBFlag = new HashMap<>();

    private int move_x, move_y;

    private DeviceMgmt mgmt;
    public void init(DeviceMgmt mgmt)
    {
        this.mgmt = mgmt;
    }

    private class KBEvent
    {
        byte code;
        byte event;

        public KBEvent(byte code,byte event)
        {
            this.code = code;
            this.event = event;
        }
    }

    void setMoveXY(int x,int y)
    {
        this.move_x = x;
        this.move_y = y;
    }

    void getEvent(byte[] buf, int len)
    {
        int i = 0;
        KBEvent event;
        if ((buf[0] & (byte)0x01) == (byte)0x01)
        {
            event = mapKB.get(KB_LEFT_CTRL);

            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_LEFT_CTRL, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_LEFT_CTRL, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x02) == 0x02)
        {
            event = mapKB.get(KB_LEFT_SHIFT);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_LEFT_SHIFT, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_LEFT_SHIFT, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x04) == 0x04)
        {
            event = mapKB.get(KB_LEFT_ALT);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_LEFT_ALT, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_LEFT_ALT, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x08) == 0x08)
        {
            event = mapKB.get(KB_LEFT_GUI);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_LEFT_GUI, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_LEFT_GUI, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x10) == 0x10)
        {
            event = mapKB.get(KB_RIGHT_CTRL);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_RIGHT_CTRL, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_RIGHT_CTRL, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x20) == 0x20)
        {
            event = mapKB.get(KB_RIGHT_SHIFT);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_RIGHT_SHIFT, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_RIGHT_SHIFT, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x40) == 0x40)
        {
            event = mapKB.get(KB_RIGHT_ALT);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_RIGHT_ALT, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_RIGHT_ALT, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        if ((buf[0] & 0x80) == 0x80)
        {
            event = mapKB.get(KB_RIGHT_GUI);
            if (event != null)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    event.event = EVENT_K_KEEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    event.event = EVENT_K_DOWN;
                }
            }
            else
            {
                event = new KBEvent(KB_RIGHT_GUI, EVENT_K_DOWN);
                mapKB.put(event.code,event);
            }
            event = new KBEvent(KB_RIGHT_GUI, EVENT_K_DOWN);
            mapKBFlag.put(event.code,event);
        }

        for (i = 2; i < 8; i++)
        {
            if (buf[i] != (byte)0x00)
            {
                event = mapKB.get(buf[i]);
                if (event != null)
                {
                    if (event.event == EVENT_K_DOWN)
                    {
                        event.event = EVENT_K_KEEP;
                    }
                    else if (event.event == EVENT_K_UP)
                    {
                        event.event = EVENT_K_DOWN;
                    }
                }
                else
                {
                    event = new KBEvent(buf[i], EVENT_K_DOWN);
                    mapKB.put(event.code,event);
                }
                event = new KBEvent(buf[i], EVENT_K_DOWN);
                mapKBFlag.put(event.code,event);
            }
        }

        for(KBEvent e : mapKB.values())
        {
            event = mapKBFlag.get(e.code);
            if (event == null)
            {
                e.event = EVENT_K_UP;
                mapKB.put(e.code,e);
            }
        }
        mapKBFlag.clear();
    }

    private boolean is_left_shift_down = false;
    private int te_move = -1,te_jump = -1,te_squat = -1,te_lie = -1,
            te_package = -1,te_arms_left = -1,te_arms_right = -1,te_map = -1,
            te_aim = -1,te_check_package = -1,te_door = -1,te_drive = -1,te_getoff = -1,te_grenade = -1,
            te_medicine = -1,te_reload = -1,te_save = -1,te_sprint = -1,te_follow = -1,te_pick = -1,
            te_ride = -1,te_pick1 = -1,te_pick2 = -1,te_pick3 = -1;

    void procKB(byte[] buf, int len)
    {

        Touch touch = new Touch();

        getEvent(buf, len);

        for (KBEvent event:mapKB.values())
        {
            /**移动部分 start**/
            //Log.i("DEBUG0", "Event:" + pNode->event + " Code:" + pNode->code);

            if (event.code == KB_W)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    move_y -= MOVE_STEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    move_y += MOVE_STEP;
                }
            }

            if (event.code == KB_S)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    move_y += MOVE_STEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    move_y -= MOVE_STEP;
                }
            }

            if (event.code == KB_A)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    move_x -= MOVE_STEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    move_x += MOVE_STEP;
                }
            }

            if (event.code == KB_D)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    move_x += MOVE_STEP;
                }
                else if (event.event == EVENT_K_UP)
                {
                    move_x -= MOVE_STEP;
                }
            }

            if (event.code == KB_LEFT_SHIFT)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    is_left_shift_down = true;
                }
                else if (event.event == EVENT_K_UP)
                {
                    is_left_shift_down = false;
                }
            }
            /**移动部分 end**/

            if (event.code == KB_1)
            {
                if (event.event == EVENT_K_DOWN && te_pick1 == -1)
                {
                    touch.startX = mgmt.s_pubg.N51_Pick1X;
                    touch.startY = mgmt.s_pubg.N52_Pick1Y;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_pick1 = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
                }
                else if (event.event == EVENT_K_UP && te_pick1 != -1)
                {
                    mgmt.atouch.up(te_pick1);
                    te_pick1 = -1;
                }
            }

            if (event.code == KB_2)
            {
                if (event.event == EVENT_K_DOWN && te_pick2 == -1)
                {
                    touch.startX = mgmt.s_pubg.N53_Pick2X;
                    touch.startY = mgmt.s_pubg.N54_Pick2Y;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_pick2 = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
                }
                else if (event.event == EVENT_K_UP && te_pick2 != -1)
                {
                    mgmt.atouch.up(te_pick2);
                    te_pick2 = -1;
                }
            }

            if (event.code == KB_3)
            {
                if (event.event == EVENT_K_DOWN && te_pick3 == -1)
                {
                    touch.startX = mgmt.s_pubg.N55_Pick3X;
                    touch.startY = mgmt.s_pubg.N56_Pick3Y;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_pick3 = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_pick3 != -1)
                {
                    mgmt.atouch.up(te_pick3);
                    te_pick3 = -1;
                }
            }

            if (event.code == KB_Q)
            {
                if (event.event == EVENT_K_DOWN && te_arms_left == -1)
                {
                    touch.startX = mgmt.s_pubg.N19_ArmsLeftX;
                    touch.startY = mgmt.s_pubg.N20_ArmsLeftY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_arms_left = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_arms_left != -1)
                {
                    mgmt.atouch.up(te_arms_left);
                    te_arms_left = -1;
                }
            }

            if (event.code == KB_E)
            {
                if (event.event == EVENT_K_DOWN && te_arms_right == -1)
                {
                    touch.startX = mgmt.s_pubg.N21_ArmsRightX;
                    touch.startY = mgmt.s_pubg.N22_ArmsRightY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_arms_right = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_arms_right != -1)
                {
                    mgmt.atouch.up(te_arms_right);
                    te_arms_right = -1;
                }
            }

            if (event.code == KB_SPACE)
            {
                if (event.event == EVENT_K_DOWN && te_jump == -1)
                {
                    touch.startX = mgmt.s_pubg.N7_JumpX;
                    touch.startY = mgmt.s_pubg.N8_JumpY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_jump = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_jump != -1)
                {
                    mgmt.atouch.up(te_jump);
                    te_jump = -1;
                }
            }

            if (event.code == KB_LEFT_ALT)
            {
                if (event.event == EVENT_K_DOWN && te_squat == -1)
                {
                    touch.startX = mgmt.s_pubg.N9_SquatX;
                    touch.startY = mgmt.s_pubg.N10_SquatY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_squat = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_squat != -1)
                {
                    mgmt.atouch.up(te_squat);
                    te_squat = -1;
                }
            }

            if (event.code == KB_LEFT_CTRL)
            {
                if (event.event == EVENT_K_DOWN && te_lie == -1)
                {
                    touch.startX = mgmt.s_pubg.N11_LieX;
                    touch.startY = mgmt.s_pubg.N12_LieY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_lie = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_lie != -1)
                {
                    mgmt.atouch.up(te_lie);
                    te_lie = -1;
                }
            }

            if (event.code == KB_B)
            {
                if (event.event == EVENT_K_DOWN && te_package == -1)
                {
                    touch.startX = mgmt.s_pubg.N17_PackageX;
                    touch.startY = mgmt.s_pubg.N18_PackageY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_package = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_package != -1)
                {
                    mgmt.atouch.up(te_package);
                    te_package = -1;
                }
            }

            if (event.code == KB_M)
            {
                if (event.event == EVENT_K_DOWN && te_map == -1)
                {
                    touch.startX = mgmt.s_pubg.N23_MapX;
                    touch.startY = mgmt.s_pubg.N24_MapY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_map = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_map != -1)
                {
                    mgmt.atouch.up(te_map);
                    te_map = -1;
                }
            }

            if (event.code == KB_R)
            {
                if (event.event == EVENT_K_DOWN && te_reload == -1)
                {
                    touch.startX = mgmt.s_pubg.N39_ReloadX;
                    touch.startY = mgmt.s_pubg.N40_ReloadY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_reload = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_reload != -1)
                {
                    mgmt.atouch.up(te_reload);
                    te_reload = -1;
                }
            }

            if (event.code == KB_Z)
            {
                if (event.event == EVENT_K_DOWN && te_drive == -1)
                {
                    touch.startX = mgmt.s_pubg.N31_DriveX;
                    touch.startY = mgmt.s_pubg.N32_DriveY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_drive = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_drive != -1)
                {
                    mgmt.atouch.up(te_drive);
                    te_drive = -1;
                }
            }

            if (event.code == KB_X)
            {
                if (event.event == EVENT_K_DOWN && te_ride == -1)
                {
                    touch.startX = mgmt.s_pubg.N49_RideX;
                    touch.startY = mgmt.s_pubg.N50_RideY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_ride = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_ride != -1)
                {
                    mgmt.atouch.up(te_ride);
                    te_ride = -1;
                }
            }

            if (event.code == KB_C)
            {
                if (event.event == EVENT_K_DOWN && te_getoff == -1)
                {
                    touch.startX = mgmt.s_pubg.N33_GetOffX;
                    touch.startY = mgmt.s_pubg.N34_GetOffY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_getoff = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_getoff != -1)
                {
                    mgmt.atouch.up(te_getoff);
                    te_getoff = -1;
                }
            }

            if (event.code == KB_T)
            {
                if (event.event == EVENT_K_DOWN && te_check_package == -1)
                {
                    touch.startX = mgmt.s_pubg.N27_CheckPackageX;
                    touch.startY = mgmt.s_pubg.N28_CheckPackageY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_check_package = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_check_package != -1)
                {
                    mgmt.atouch.up(te_check_package);
                    te_check_package = -1;
                }
            }

            if (event.code == KB_H)
            {
                if (event.event == EVENT_K_DOWN && te_save == -1)
                {
                    touch.startX = mgmt.s_pubg.N41_SaveX;
                    touch.startY = mgmt.s_pubg.N42_SaveY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_save = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_save != -1)
                {
                    mgmt.atouch.up(te_save);
                    te_save = -1;
                }
            }

            if (event.code == KB_G)
            {
                if (event.event == EVENT_K_DOWN && te_medicine == -1)
                {
                    touch.startX = mgmt.s_pubg.N37_MedicineX;
                    touch.startY = mgmt.s_pubg.N38_MedicineY;
                    touch.type = Touch.TOUCH_NORMAL;

                    te_medicine = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

                }
                else if (event.event == EVENT_K_UP && te_medicine != -1)
                {
                    mgmt.atouch.up(te_medicine);
                    te_medicine = -1;
                }
            }

            if (event.code == KB_F)
            {
                if (event.event == EVENT_K_DOWN)
                {
                    mgmt.procMouse.set_watch_status(true);
                }
                else if (event.event == EVENT_K_UP)
                {
                    mgmt.procMouse.set_watch_status(false);
                }
            }
        }

        /**移动部分 start**/

        //Log.i("DEBUG2", "MouseX:" + move_x + " MouseY:" + move_y);
        if ((move_x != mgmt.s_pubg.N5_MoveX || move_y != mgmt.s_pubg.N6_MoveY) && te_move == -1)
        {
            if (is_left_shift_down && move_y == mgmt.s_pubg.N6_MoveY - MOVE_STEP && move_x == mgmt.s_pubg.N5_MoveX) //shift
            {
                touch.startX = mgmt.s_pubg.N5_MoveX;
                touch.startY = mgmt.s_pubg.N6_MoveY;
                touch.type = Touch.TOUCH_MOVE_NORMAL;
                touch.endX = mgmt.s_pubg.N43_SprintX;
                touch.endY = mgmt.s_pubg.N44_SprintY;
                touch.step = 2;
            }
            else
            {
                touch.startX = mgmt.s_pubg.N5_MoveX;
                touch.startY = mgmt.s_pubg.N6_MoveY;
                touch.type = Touch.TOUCH_MOVE_NORMAL;
                touch.endX = move_x;
                touch.endY = move_y;
                touch.step = 2;
            }

            te_move = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);
        }
        else if (move_x == mgmt.s_pubg.N5_MoveX && move_y == mgmt.s_pubg.N6_MoveY && te_move != -1)
        {
            mgmt.atouch.move(te_move,mgmt.s_pubg.N5_MoveX, mgmt.s_pubg.N6_MoveY - MOVE_STEP);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mgmt.atouch.up(te_move);
            te_move = -1;
        }
        else if (te_move != -1)
        {
            if (is_left_shift_down && move_y == mgmt.s_pubg.N6_MoveY - MOVE_STEP && move_x == mgmt.s_pubg.N5_MoveX) //shift
            {
                mgmt.atouch.move(te_move,mgmt.s_pubg.N43_SprintX, mgmt.s_pubg.N44_SprintY);

            }
            else
            {
                mgmt.atouch.move(te_move,move_x, move_y);
            }
        }
        /**移动部分 end**/

        List<Byte> removeList = new ArrayList<>();
        for(KBEvent event:mapKB.values())
        {
            if(event.event == EVENT_K_UP)
            {
                removeList.add(event.code);

            }
        }

        for(Byte code: removeList)
        {
            mapKB.remove(code);
        }
    }

}
