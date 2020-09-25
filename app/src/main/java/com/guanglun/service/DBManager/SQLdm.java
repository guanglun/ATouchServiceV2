package com.guanglun.service.DBManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

public class SQLdm {

    private String DEBUG_TAG = "SQLdm";

    private final String filePath = "/ATouch";
    private final String DBName = "KeyboardMouse.db";
    private final String SettingName = "AppConfig.properties";

    private String sd_path = null;
    private SQLiteDatabase database;

    public SQLiteDatabase openDatabase(){

        sd_path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(sd_path + filePath + "/" + DBName);

        if(file.exists())
        {
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        }

        return null;
    }
}