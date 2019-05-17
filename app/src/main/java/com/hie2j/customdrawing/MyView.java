package com.hie2j.customdrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    public static final int PEN = 0;
    public static final int RECT = 1;
    public static final int CIRCLE = 2;

    private Paint paint = new Paint();
    private int paintMode = 0;

    private Path path = new Path();

    private Point rectLeftTop = new Point();
    private Point rectRightBottom = new Point();

    private float radius = 0;
    private Point circleCenter = new Point();

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);//反锯齿
        paint.setDither(true);//防抖动
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);//使画笔更加圆润
        paint.setStrokeCap(Paint.Cap.ROUND);//同上
    }

    public void setPaintColor(int color){
        paint.setColor(color);
    }

    public void setPaintMode(int mode){
        paintMode = mode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (paintMode){
            case PEN:
                canvas.drawPath(path,paint);
                break;
            case RECT:
                canvas.drawRect(rectLeftTop.x,rectLeftTop.y,rectRightBottom.x,rectRightBottom.y,paint);
                break;
            case CIRCLE:
                canvas.drawCircle(circleCenter.x,circleCenter.y,radius,paint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (paintMode){
            case PEN:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        path.moveTo(event.getX(),event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        path.lineTo(event.getX(),event.getY());
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            case RECT:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rectLeftTop.x = (int) event.getX();
                        rectLeftTop.y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        rectRightBottom.x = (int) event.getX();
                        rectRightBottom.y = (int) event.getY();
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
            case CIRCLE:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        circleCenter.x = (int) event.getX();
                        circleCenter.y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        radius = (float) Math.sqrt(Math.pow(event.getX() - circleCenter.x,2) +
                        Math.pow(event.getY() - circleCenter.y,2));
                        invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
        }
        return true;
    }
//画笔加粗
    public void setPaintThick() {
        float value = paint.getStrokeWidth();
        Log.e("MyView","value = " + value);
        if (value < 30){
            paint.setStrokeWidth((value+1));
        }
    }
//画笔变细
    public void setPaintFine() {
        float value = paint.getStrokeWidth();
        Log.e("MyView","value = " + value);
        if (value > 1){
            paint.setStrokeWidth((value-1));
        }
    }
}
