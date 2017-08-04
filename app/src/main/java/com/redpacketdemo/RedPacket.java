package com.redpacketdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/3.
 */

public class RedPacket {
    public float x, y;
    public float rotation;
    public float speed;
    public float rotationSpeed;
    public int width, height;
    public Bitmap bitmap;
    public int money;
    public boolean isRealRed;

    public RedPacket(Context context, Bitmap originalBitmap, int speed, float maxSize, float minSize, int viewWidth) {
        //获取一个显示红包大小的倍数
        double widthRandom = Math.random();
        if (widthRandom < minSize || widthRandom > maxSize) {
            widthRandom = maxSize;
        }
        //红包的宽度
        width = (int) (originalBitmap.getWidth() * widthRandom);
        //红包的高度
        height = width * originalBitmap.getHeight() / originalBitmap.getWidth();
        int mWidth = (viewWidth == 0) ? context.getResources().getDisplayMetrics().widthPixels : viewWidth;
        //生成红包bitmap
        bitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);
        originalBitmap.recycle();
        Random random = new Random();
        //红包起始位置x:[0,mWidth-width]
        int rx = random.nextInt(mWidth) - width;
        x = rx <= 0 ? 0 : rx;
        //红包起始位置y
        y = -height;

        Log.e(TAG, "RedPacket init(): height:" + height);

        //初始化该红包的下落速度
        this.speed = speed + (float) Math.random() * 1000;
        //初始化该红包的初始旋转角度
        rotation = (float) Math.random() * 180 - 90;
        //初始化该红包的旋转速度
        rotationSpeed = (float) Math.random() * 90 - 45;
        //初始化是否为中奖红包
        isRealRed = isRealRedPacket();
    }

    /**
     * 判断当前点是否包含在区域内
     */
    public boolean isContains(float x, float y) {
        //稍微扩大下点击的区域
        return this.x - 50 < x && this.x + 50 + width > x
                && this.y - 50 < y && this.y + 50 + height > y;
    }

    /**
     * 随机 是否为中奖红包
     */
    public boolean isRealRedPacket() {
        Random random = new Random();
        int num = random.nextInt(10) + 1;
        //如果[1,10]随机出的数字是2的倍数 为中奖红包
        if (num % 2 == 0) {
            money = num * 2;//中奖金额
            return true;
        }
        return false;
    }

    /**
     * 回收图片
     */
    public void recycle() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}