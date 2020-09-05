package com.guanglun.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ATouchService extends Service {

    private static String TAG = "ATouchService";

    // We can only access CLIPBOARD_SERVICE from the main thread
    private static Object clipboardManager;
    private static final int NOTIFICATION_ID = 0x1;


    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");

        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE);

        String channelId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("stf_channel", "STF Channel");
        } else {
            channelId = "";
        }

        Intent notificationIntent = new Intent(this, IdentityActivity.class);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setTicker(getString(R.string.service_ticker))
                .setContentTitle(getString(R.string.service_title))
                .setContentText(getString(R.string.service_text))
                .setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(NOTIFICATION_ID, notification);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //while(true)
                {
                    //Log.i(TAG, "Thread start");
                    try {
                        String ret = execCommand("pm path com.guanglun.service");
                        int index = ret.indexOf("package:");
                        ret = ret.substring(index + 8);

                        Log.i(TAG, ret);

                        //execCommand("export CLASSPATH=\"" + ret + "\"");
                        //execCommand("exec app_process /system/bin com.guanglun.service.MinitouchAgent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Log.i(TAG, "Thread end");
                }
            }
        });

        t.start();

    }

    public static String execCommand(String command) throws IOException {
        String returnStr = null;

        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        try {
            if (proc.waitFor() != 0) {
                Log.i(TAG, "exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = in.toString();
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
            }
            returnStr = stringBuffer.toString();
            //打印出执行cmd命令后返回的数据
            Log.i(TAG, "****" + returnStr);

        } catch (InterruptedException e) {
            System.err.println(e);
        }

        return returnStr;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "onStart()");
    }


    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
    }
}
