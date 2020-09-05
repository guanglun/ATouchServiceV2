package com.guanglun.service.util;

public class Touch implements Cloneable{

    public static final int TOUCH_NORMAL        = 0;
    public static final int TOUCH_MOVE_NORMAL   = 1;
    public static final int TOUCH_MOVE_UP       = 2;

    public boolean isUse;
    public int type;
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public int step;
    public int stepCount;
    public int id;

    Touch()
    {
        this.isUse = false;
    }

    Touch(int type,int startX,int startY,int endX,int endY,int step)
    {
        this.type = type;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.step = step;
    }

}
