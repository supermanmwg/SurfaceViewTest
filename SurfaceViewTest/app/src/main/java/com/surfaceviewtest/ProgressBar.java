package com.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mwg on 16-5-11.
 */
public class ProgressBar extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "ProgressBar";
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Canvas mCanvas;
    private boolean mIsDrawing;
    private int i = 0;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            draw();
            i++;
        }
    }

    private void draw() {
        Log.d(TAG, "running  i is " + i);
        mCanvas = mHolder.lockCanvas();
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = 60;
        try {
            mCanvas.drawColor(Color.WHITE);  //这个需加上，不然默认是黑色
            //  mCanvas.rotate(i * 2, centerX, centerY);  //需要提前，因为每次都是一个新的canvas,放到后面设置，没有效果
            //  RectF arcRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            //mCanvas.drawArc(arcRectF, -90, 40, false, mPaint);
            //   mPaint.setStrokeWidth(10);
            //  mCanvas.drawLine(centerX, centerY-90,centerX, centerY-100, mPaint);
            float x1 = (float) (100 * Math.sin(Math.toRadians(0 * 90 + i)));
            float y1 = 0 - (float) (100 * Math.cos(Math.toRadians(0 * 90+ i)));
            float x2 = (float) (100 * Math.sin(Math.toRadians(1 * 90+ i)));
            float y2 = 0 - (float) (100 * Math.cos(Math.toRadians(1 * 90+ i)));
            float x3 = (float) (100 * Math.sin(Math.toRadians(2 * 90+ i)));
            float y3 = 0 - (float) (100 * Math.cos(Math.toRadians(2 * 90+ i)));
            float x4 = (float) (100 * Math.sin(Math.toRadians(3 * 90+ i)));
            float y4 = 0 - (float) (100 * Math.cos(Math.toRadians(3 * 90+ i)));
            mCanvas.translate(centerX, centerY);
            mPaint.setTextSize(25);
            mPaint.setStrokeWidth(1);
            mPaint.setStyle(Paint.Style.FILL);
            mCanvas.drawText("老", 0, 1, x1, y1, mPaint);
            mCanvas.drawText("三", 0, 1, x2, y2, mPaint);
            mCanvas.drawText("是", 0, 1, 0, 0, mPaint);
            mCanvas.drawText("S", 0, 1, x3, y3, mPaint);
            mCanvas.drawText("B", 0, 1, x4, y4, mPaint);

        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
