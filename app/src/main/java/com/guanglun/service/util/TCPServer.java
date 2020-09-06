package com.guanglun.service.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    public static final int READ_BLOCK_SIZE                 = 1024;
    public static final int TCP_MAX_CONNECT_COUNT           = 2;

    private ServerSocket serverSocket = null;
    private List<Socket> clientList = null;
    private OnReceiveListener onReceiveListener = null;
    private ExecutorService executorService = null;


    public interface OnReceiveListener {
        public void onReceive(Socket socket, OutputStream os, byte[] buffer);
        public void onDisConnect(Socket socket);
    }


    class ReadRunnable implements Runnable {


        private Socket socket = null;
        private InputStream is = null;
        private OutputStream os = null;


        public ReadRunnable(Socket socket) {
            this.socket = socket;
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            int len = 0;
            byte[] buffer = new byte[READ_BLOCK_SIZE];
            while (this.socket.isConnected()) {
                try {
                    len = is.read(buffer, 0, READ_BLOCK_SIZE);
                    if (len > 0) {
                        if (onReceiveListener != null) {
                            byte buf[] = new byte[len];
                            System.arraycopy(buffer,0,buf,0,len);
                            onReceiveListener.onReceive(socket, os, buf);
                        }
                    }else{
                        break;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    break;
                }
            }
            //Log.i(EasyTool.TAG, "socket disconnect " + socket.toString());
            onReceiveListener.onDisConnect(socket);
            clientList.remove(socket);
            closeClient(socket);
        }
    }


    public TCPServer(int port, OnReceiveListener onReceivedListener) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (serverSocket == null) {
            return;
        }
        this.onReceiveListener = onReceivedListener;
        clientList = new ArrayList<Socket>();

        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        //Log.i(EasyTool.TAG, "new socket connect " + socket.toString());
                        clientList.add(socket);

                        Thread clientThread = new Thread(new ReadRunnable(socket));
                        clientThread.start();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        break;
                    }
                }
                closeClient(socket);
            }
        });

        serverThread.start();


    }


    public synchronized boolean send(Socket socket, OutputStream os, byte[] buffer) {
        boolean result = false;
        if (os != null) {
            try {
                os.write(buffer);
                os.flush();
                result = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public void closeClient(Socket socket) {
        if (socket == null) {
            return;
        }
        try {
            InputStream is = socket.getInputStream();
            if (is != null) {
                is.close();
            }
            OutputStream os = socket.getOutputStream();
            if (os != null) {
                os.close();
            }
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void close() {
        if (clientList != null) {
            for (Socket socket : clientList) {
                closeClient(socket);
            }
        }
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
