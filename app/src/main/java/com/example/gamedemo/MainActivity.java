package com.example.gamedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private Display mDisplay;
    private DisplayMetrics mMetrics;
    private int mWidthPixels;
    private int mHeightPixels;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager windowManager = getWindowManager();
        mDisplay = windowManager.getDefaultDisplay();
        mMetrics = new DisplayMetrics();
        mDisplay.getMetrics(mMetrics);
        //获取屏幕的宽高

        mWidthPixels = mMetrics.widthPixels;
        mHeightPixels = mMetrics.heightPixels;

        Log.i(TAG, "onCreate: --- >mWidthPixels "+mWidthPixels);
        Log.i(TAG, "onCreate: --- >mHeightPixels "+mHeightPixels);

        //使程序全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BirdView birdView = new BirdView(this,mWidthPixels,mHeightPixels);
        setContentView(birdView);




    }
}
