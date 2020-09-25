package com.guanglun.service.util;

import android.util.Log;

import com.guanglun.service.DBManager.MapUnit;

public class ATouchRecv {

    private int  receive_atouch_flag = 0, data_atouch_len = 0;
    private byte[] data_atouch_buffer = new byte[2048];
    private byte check_atouch = 0;


    private DeviceMgmt mgmt;
    public void init(DeviceMgmt mgmt)
    {
        this.mgmt = mgmt;
    }


    public boolean atouchRecv(byte[] buf){

        boolean isSuccess = false;

        for(byte data:buf)
        {
            if (receive_atouch_flag == 0 && data == (byte)0xCC)
            {
                receive_atouch_flag = 1;
            }
            else if (receive_atouch_flag == 1 && data == (byte)0xDD)
            {
                data_atouch_len = 0;
                check_atouch = 0;
                receive_atouch_flag = 2;
            }
            else if (receive_atouch_flag == 2)
            {
                data_atouch_len = ((data << 8) & 0xffff);

                receive_atouch_flag = 3;
                check_atouch += data;
            }
            else if (receive_atouch_flag == 3)
            {

                data_atouch_len |= (data & 0x00ff);
                receive_atouch_flag = 4;
                check_atouch += data;
            }
            else if ((receive_atouch_flag >= 4) && (receive_atouch_flag < (data_atouch_len + 4)))
            {

                data_atouch_buffer[receive_atouch_flag - 4] = data;
                receive_atouch_flag++;
                check_atouch += data;
            }
            else if ((receive_atouch_flag == (data_atouch_len + 4)) && (check_atouch == data))
            {
                isSuccess = true;
                //Log.i(EasyTool.TAG, "atouchRecv success");
                recvProtocol(data_atouch_buffer, data_atouch_len);
                receive_atouch_flag = 0;
            }
            else if (data == (byte)0xCC)
            {
                receive_atouch_flag = 1;
            }
            else
            {
                receive_atouch_flag = 0;
            }
        }

        return isSuccess;
    }

    public byte[] atouchCreatCmd(byte cmd, byte[] in_buf, int len)
    {
        byte[] out_buf = new byte[len + 6];
        byte check_sum = 0;
        int i = 0;

        out_buf[0] = (byte)0xCC;
        out_buf[1] = (byte)0xDD;
        out_buf[2] = (byte)((len + 1) >> 8);
        out_buf[3] = (byte)((len + 1));
        out_buf[4] = (byte)cmd;

        System.arraycopy(in_buf, 0, out_buf, 5, len);

        for (; i < len + 3; i++)
        {
            check_sum += out_buf[i + 2];
        }

        out_buf[5 + len] = check_sum;

        return out_buf;
    }

    private void recvProtocol(byte[] buf, int len)
    {
        byte[] bytes;
        switch (buf[0])
        {
            case 0x01:

                bytes = new byte[len-1];
                System.arraycopy(buf,1,bytes,0,len-1);
                String map_name = new String(bytes);

                mgmt.maplist = mgmt.dbControl.getRawByName(map_name);
                if(mgmt.maplist != null)
                {
                    Log.i(EasyTool.TAG, "receive keymap " + map_name);
                }
                break;
            case 0x02:
                bytes = new byte[len-1];
                System.arraycopy(buf,1,bytes,0,len-1);
                get_soft_mouse_buffer(bytes);

                break;

            case 0x03:
                bytes = new byte[len-1];
                System.arraycopy(buf,1,bytes,0,len-1);
                get_rotation(bytes);

                break;

            case 0x04:
                bytes = new byte[len];
                System.arraycopy(buf,0,bytes,0,len);
                mgmt.adbSend(bytes);
                break;

            default:
                break;
        }
    }

    void get_rotation(byte[] buf)
    {
        int width = (int)((buf[1]<<8)|buf[2]);
        int heigh = (int)((buf[3]<<8)|buf[4]);
        byte i = buf[0];

        if(i == 0 || i == 2)
        {
            set_rotation(buf[0],width,heigh);
        }else if(i == 1 || i== 3){
            set_rotation(buf[0],heigh,width);
        }

    }

    private byte rotation = 0;
    private int width = 1920,heigth = 1080;
    void set_rotation(byte ro,int w,int h)
    {
        rotation = ro;
        width = w;
        heigth = h;
    }


    private int te_mouse = -1;
    private void get_soft_mouse_buffer(byte[] buf)
    {

        int x = EasyTool.byte2int(buf[1], buf[2]);
        int y = EasyTool.byte2int(buf[3], buf[4]);

        if (((buf[0] & 0x01) == 0x01) && te_mouse == -1)
        {
            Touch touch = new Touch();
            touch.startX = x;
            touch.startY = y;
            touch.type = Touch.TOUCH_NORMAL;

            te_mouse = mgmt.atouch.down(touch.type,touch.startX,touch.startY,touch.endX,touch.endY,touch.step);

        }
        else if ((te_mouse != -1) && ((buf[0] & 0x01) == 0x00))
        {

            mgmt.atouch.up(te_mouse);
            te_mouse = -1;
        }
        else if ((te_mouse != -1) && ((buf[0] & 0x01) == 0x01))
        {
            mgmt.atouch.move(te_mouse,x,y);
        }


    }

}
