package com.guanglun.service.util;

import com.guanglun.service.DBManager.DBControlMapUnit;
import com.guanglun.service.DBManager.MapUnit;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class DeviceMgmt {

    private TCPServer tcpServer = null;
    private Socket socketATouch = null,socketADB = null;
    private OutputStream osATouch = null,osADB = null;

    public ATouchRecv atouchRecv = new ATouchRecv();
    public ADBRecv adbRecv = new ADBRecv();
    public PUBG s_pubg = new PUBG();

    public List<MapUnit> maplist = null;
    public PrcKeyboard procKB = new PrcKeyboard();
    public ProcMouse procMouse = new ProcMouse();

    public AutoTouch atouch;

    public DBControlMapUnit dbControl = new DBControlMapUnit();


    public DeviceMgmt(AutoTouch atouch)
    {
        this.atouch = atouch;

        atouchRecv.init(this);
        adbRecv.init(this);
        s_pubg.init(this);
        procKB.init(this);
        procMouse.init(this);

        tcpServer = new TCPServer(1989, new TCPServer.OnReceiveListener() {
            @Override
            public void onReceive(Socket socket, OutputStream os, byte[] buffer) {
                //Log.i(EasyTool.TAG, socket.toString() +"recv " + EasyTool.bytes2hex(buffer,buffer.length));

                if(!socket.equals(socketATouch) && !socket.equals(socketADB)) {

                        if (atouchRecv.atouchRecv(buffer))
                        {
                            atouch.clear();
                            socketATouch = socket;
                            osATouch = os;
                        }

                        if (adbRecv.adbRecv(buffer)) {
                            atouch.clear();
                            socketADB = socket;
                            osADB = os;
                        }

                }else if(socket.equals(socketATouch))
                {
                    atouchRecv.atouchRecv(buffer);
                }else if(socket.equals(socketADB))
                {
                    adbRecv.adbRecv(buffer);
                }
            }

            @Override
            public void onDisConnect(Socket socket) {
                if(socket == socketATouch)
                {
                    socketATouch = null;
                }else if(socket == socketADB)
                {
                    socketADB = null;
                }
            }
        });



    }

    public boolean atouchSend(byte[] buf)
    {
        if(socketATouch != null)
        {
            if(socketATouch.isConnected())
            {
                try {
                    osATouch.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    public void adbSend(byte[] buf)
    {
        if(socketADB != null)
        {
            if(socketADB.isConnected())
            {

                try {
                    osADB.write(buf);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
