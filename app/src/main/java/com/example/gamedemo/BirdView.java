package com.example.gamedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BirdView extends View {

    private final Paint mPaint;
    private boolean isFinish = false;
    private int num = 0;
    private int mWidth;
    private int mHeight;

    //鸟大小
    private float mBirdSize = 16;

    //鸟速度
    private int mBirdSudu = 3;

    //鸟上升像素
    private int mbirdUp = 90;

    private float mBirdX;
    private float mBirdY;
    private float mRectHeight, mRect2Height;
    private float mRect1X, mRect2X;
    private float mRect1Y, mRect2Y;
    private float mRectWidth = 80;

    //有效距离
    private int flyHeight = 200;
    //柱子速度
    private int mRectSUdu = 5;
    private final Timer mTimer;
    private BirdView mBirdView;
    private static final String TAG = "BirdView";

    private boolean timePause = false;

    public BirdView(Context context, int width, int height) {
        super(context);
        mBirdView = this;
        mPaint = new Paint();
        mWidth = width;
        mHeight = height;
        mTimer = new Timer();
        play();
    }

    public void play() {
        isFinish = false;
        //设置柱子的初始位置

        mRect1X = mWidth - mRectWidth;
        mRect1Y = 0;

        mRect2X = mWidth - mRectWidth;
        mRect2Y = mHeight;

        mRectHeight = mHeight / 2 - flyHeight;
        mRect2Height = mHeight - mRectHeight - flyHeight;

        mBirdX = 50;
        mBirdY = mHeight / 2;
        postInvalidate();
        if (mTimer != null) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (timePause) {
                        return;
                    }
                    mBirdY = mBirdY + mBirdSudu;
                    mRect1X = mRect1X - mRectSUdu;
                    mRect2X = mRect2X - mRectSUdu;

                    //如果柱子碰到屏幕边缘
                    if ((mRect1X < -mRectWidth)) {
                        mRect1X = mWidth - mRectWidth;
                        mRect2X = mWidth - mRectWidth;

                    }

                    //判断小鸟是否碰到屏幕边缘
                    if (mBirdY >= mHeight || mBirdY <= 0) {
                        isFinish = true;
                    }

                    if (mBirdX >= mRect1X) {
                        if ((mBirdY< mRectHeight || mBirdY >mRect2Height)) {
                            isFinish = true;
                        }
                    }

                    postInvalidate();
                }
            }, 0, 15);
        }

        mBirdView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Log.i(TAG, "onTouch: --- > ACTION_DOWN");
                        //手指点击事件
                        mBirdY = mBirdY - mbirdUp;
                        postInvalidate();
                        break;
                }
                return true;
            }
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);

        //抗锯齿
        mPaint.setAntiAlias(true);

        if (isFinish) {
            timePause = true;
            //执行结束效果
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(80);
            canvas.drawText("游戏结束",mWidth/2,mHeight/2,mPaint);
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            timePause = false;
                            play();
                            break;
                    }
                    return true;
                }
            });

        } else {
            //执行游戏进行中的效果
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(80);
            canvas.drawText(num + "", mWidth / 2 - 10, 80, mPaint);
            canvas.drawCircle(mBirdX, mBirdY, mBirdSize, mPaint);

            //绘制上柱子
            mPaint.setColor(Color.BLUE);
            canvas.drawRect(mRect1X, mRect1Y, mRect2X + mRectWidth, mRect1Y + mRectHeight, mPaint);

            //绘制下柱子
            canvas.drawRect(mRect2X, mRect2Y - mRect2Height, mRect2X + mRectWidth, mRect2Y + mRect2Height, mPaint);
        }
    }


}
