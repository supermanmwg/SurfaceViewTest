package com.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mwg on 16-5-10.
 */
public class SinView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final String TAG = "SinView";
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private boolean mIsRunning;
    private int x = 0;
    private int y = 0;

    public SinView(Context context) {
        this(context, null);
    }

    public SinView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mPath.moveTo(x, y);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surface view is destroyed");
        mIsRunning = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "on detach from window");
        mIsRunning = false;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            draw();

            x += 1;
            y = (int)(100 * Math.sin(x * 2 * Math.PI /180) + 400);
            mPath.lineTo(x, y);
        }
    }

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        if(mCanvas != null) {
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

}
