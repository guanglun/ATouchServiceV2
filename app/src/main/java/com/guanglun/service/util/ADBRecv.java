package com.guanglun.service.util;

import android.util.Log;

public class ADBRecv {

    private int receive_adb_flag = 0, data_adb_len = 0;
    private byte[] data_adb_buffer = new byte[2048];
    private byte check_adb = 0;

    private DeviceMgmt mgmt;
    public void init(DeviceMgmt mgmt)
    {
        this.mgmt = mgmt;
    }

    public boolean adbRecv(byte[] buf)
    {
        boolean isSuccess = false;
        int i = 0;

        for (byte data : buf)
        {
            if (receive_adb_flag == 0 && data == (byte)0xAA)
            {
                receive_adb_flag = 1;
            }
            else if (receive_adb_flag == 1 && data == (byte)0xBB)
            {
                data_adb_len = 0;
                check_adb = 0;
                receive_adb_flag = 2;
            }
            else if (receive_adb_flag == 2)
            {
                data_adb_len = data;
                receive_adb_flag = 3;
                check_adb += data;
            }
            else if ((receive_adb_flag >= 3) && (receive_adb_flag < (data_adb_len + 3)))
            {
                data_adb_buffer[receive_adb_flag - 3] = data;
                receive_adb_flag++;
                check_adb += data;
            }
            else if ((receive_adb_flag == (data_adb_len + 3)) && (check_adb == data))
            {
                isSuccess = true;
                Log.i(EasyTool.TAG, "adbRecv success");
                adbRecvProtocol(data_adb_buffer, data_adb_len);
                receive_adb_flag = 0;
            }
            else if (data == (byte)0xAA)
            {
                receive_adb_flag = 1;
            }
            else
            {
                receive_adb_flag = 0;
            }
        }

        return isSuccess;
    }

    void adbRecvProtocol(byte[] buf, int len)
    {
        byte[] bytes;

        switch (buf[0])
        {
            case 0x00:

                bytes = new byte[4];
                System.arraycopy(buf,1,bytes,0,4);
                bytes = mgmt.atouchRecv.atouchCreatCmd((byte)0x00, bytes, 4);
                mgmt.atouchSend(bytes);

                break;
            case 0x02:
                if (len >= 4)
                {
                    bytes = new byte[len-1];
                    System.arraycopy(buf,1,bytes,0,len-1);
                    mgmt.procMouse.procMouse(bytes,len-1);
                }
                break;

            case 0x03:
                if (len == 9)
                {
                    bytes = new byte[len-1];
                    System.arraycopy(buf,1,bytes,0,len-1);
                    mgmt.procKB.procKB(bytes,len-1);
                }
                break;

            default:
                break;
        }
    }
}
