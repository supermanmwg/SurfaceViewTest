package com.surfaceviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.surfaceviewtest.utils.Time;
import com.surfaceviewtest.utils.TimeSet;

/**
 * Created by mwg on 16-5-11.
 */
public class TimeView extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    private final String TAG = "TimeView";
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Paint mCirlcePaint;
    private Paint mHourPaint;
    private Paint mPointPaint;
    private boolean mIsDrawing;
    private int mCenterX;
    private int mCenterY;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);  ///这个一定要加上
        mCirlcePaint = new Paint();
        mCirlcePaint.setAntiAlias(true);
        mCirlcePaint.setStyle(Paint.Style.STROKE);
        mCirlcePaint.setStrokeWidth(2);
        mCirlcePaint.setColor(Color.BLACK);

        mHourPaint = new Paint();
        mHourPaint.setAntiAlias(true);
        mHourPaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setStrokeWidth(2);
        mHourPaint.setColor(Color.BLACK);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(2);
        mPointPaint.setColor(Color.BLACK);
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
        while(mIsDrawing) {
            draw();
        }
    }

    private void draw() {
        mCanvas = mHolder.lockCanvas();
        try {
            drawTime(mCanvas);
        }catch (Exception e) {
            Log.d(TAG, "exception details is " + e.getMessage());
        } finally {
            if(mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawTime(Canvas canvas) {
        mCenterX = (getMeasuredWidth() / 2);
        mCenterY = (getMeasuredHeight() / 2);
        Log.d(TAG, "center x is " + mCenterX + ", center y is " + mCenterY);
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, 100, mCirlcePaint);

        canvas.save();
        for(int i = 1; i <= 12; i++) {
            mCanvas.rotate(30, mCenterX, mCenterY);
            if( i == 3 || i == 6 || i == 9 || i == 12) {
                mHourPaint.setStrokeWidth(2);
                canvas.drawLine(mCenterX, mCenterY - 90, mCenterX, mCenterY - 100, mHourPaint);
            } else {
                mHourPaint.setStrokeWidth(1);
                canvas.drawLine(mCenterX, mCenterY - 90, mCenterX, mCenterY - 100, mHourPaint);
            }
        }

        canvas.restore();

        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        for (int i = 1; i <= 12; i ++) {
            if(i == 3 || i == 6 || i == 9 || i == 12) {
                float x = (float) (75 * Math.sin(Math.toRadians(i * 30)));
                float y = 0 - (float) (75 * Math.cos(Math.toRadians(i * 30)));
                String hour = String.valueOf(i);
                mHourPaint.setTextSize(15);
                Paint.FontMetrics fontMetrics = mHourPaint.getFontMetrics();
                float bottomY = -fontMetrics.top -(fontMetrics.bottom - fontMetrics.top) / 2;
                canvas.drawText(hour , 0, hour.length(), x - mHourPaint.measureText(hour) / 2  , y + bottomY, mHourPaint );
            } else {
                float x = (float) (80 * Math.sin(Math.toRadians(i * 30)));
                float y = 0 - (float) (80 * Math.cos(Math.toRadians(i * 30)));
                String hour = String.valueOf(i);
                mHourPaint.setTextSize(10);
                Paint.FontMetrics fontMetrics = mHourPaint.getFontMetrics();
                float bottomY = -fontMetrics.top - (fontMetrics.bottom - fontMetrics.top) / 2;
                canvas.drawText(hour , 0, hour.length(),  x - mHourPaint.measureText(hour) / 2, y + bottomY, mHourPaint );
            }
        }
        canvas.restore();

        TimeSet set = Time.getTime();
        drawPoint(canvas, set.hour, set.minute, set.second);
    }

    private void drawPoint(Canvas canvas, int hour, int minute, int second) {
        canvas.save();
        mPointPaint.setStrokeWidth(4);
        mPointPaint.setColor(Color.BLACK);
        canvas.rotate(hour * 30 + (minute * 60 + second) / 3600f * 30, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - 30, mPointPaint);
        canvas.restore();

        canvas.save();
        mPointPaint.setStrokeWidth(3);
        canvas.rotate(minute * 6 + (second) / 60f * 6, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - 40, mPointPaint);
        canvas.restore();

        canvas.save();
        mPointPaint.setStrokeWidth(2);
        mPointPaint.setColor(Color.RED);
        canvas.rotate(second * 6, mCenterX, mCenterY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - 50, mPointPaint);
        canvas.drawCircle(mCenterX, mCenterY, 3, mPointPaint);
        canvas.restore();
    }

}
