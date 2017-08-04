package com.redpacketdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RedPacketTest redRainView1;
    private Button start, stop;
    private TextView money;
    private int totalmoney = 0;

    private int leftTimes = 14;
    AlertDialog.Builder ab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ab = new AlertDialog.Builder(MainActivity.this);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        money = (TextView) findViewById(R.id.money);
        redRainView1 = (RedPacketTest) findViewById(R.id.red_packets_view1);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start) {
/*
            double d1 = 0.154911;
            double d2 = 0.154911;
            double d3 = d1+d2;

            BigDecimal b = new BigDecimal(d3);
            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            DecimalFormat df = new DecimalFormat("#.00");
           String  totalMoney = df.format(d1) ;

            String result = String .format("%.2f",d1);

            Log.e("MainActivity","BigDecimal = "+String.valueOf(f1));
            Log.e("MainActivity","result = "+result);
            Log.e("MainActivity","totalMoney = "+totalMoney);*/

            startRedRain();
        } else if (v.getId() == R.id.stop) {
            stopRedRain();
        }
    }

    /**
     * 开始下红包雨
     */
    private void startRedRain() {
        redRainView1.startRain();
        redRainView1.setOnRedPacketClickListener(new RedPacketTest.OnRedPacketClickListener() {
            @Override
            public void onRedPacketClickListener(final RedPacket redPacket) {
               /* ImageView iv = new ImageView(MainActivity.this);
                setBitmapSrc(iv,redPacket.y,redPacket.x);*/


//                redRainView1.pauseRain();
                ab.setCancelable(false);
                ab.setTitle("红包提醒");
                ab.setNegativeButton("继续抢红包", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        redRainView1.restartRain();

                    }
                });

                if (leftTimes > 0) {
                    if (redPacket.isRealRed) {
//
//                        ImageView iv = new ImageView(MainActivity.this);
//                        setBitmapSrc(iv, redPacket.y, redPacket.x);

                        leftTimes--;
                        ab.setMessage("恭喜你，抢到了" + redPacket.money + "元！");
                        totalmoney += redPacket.money;
                        money.setText("中奖金额: " + totalmoney);
                    } else {

//                        ImageView iv = new ImageView(MainActivity.this);
//                        setBitmapSrc(iv, redPacket.y, redPacket.x);
//                        ab.setMessage("很遗憾，下次继续努力！");
                    }

                } else {
                    ab.setMessage("今日次数已用完，明天再来把！");
                    redRainView1.stopRainNow();
                }

                redRainView1.post(new Runnable() {
                    @Override
                    public void run() {
                        ab.show();

                    }
                });
            }
        });
    }

    /**
     * 停止下红包雨
     */
    private void stopRedRain() {
        totalmoney = 0;//金额清零
        redRainView1.stopRainNow();
    }

    Bitmap bm;

    public void setBitmapSrc(final ImageView iv, float x, final float y) {
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.yun);
        iv.setImageBitmap(bm);
        final ValueAnimator animator1 = ValueAnimator.ofFloat(y, x);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bm = BitmapFactory.decodeResource(getResources(), R.mipmap.yun);
                iv.setImageBitmap(bm);
//                setAlpha(0);
//                setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        animator1.setDuration(200).start();
        animator1.addListener(new AnimatorListenerAdapter() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setTranslationY(y);
                iv.setAlpha(0);
            }
        });

    }
}
