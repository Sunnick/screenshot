package com.example.issuser.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by issuser on 2017/2/2.
 */

public class LineShape extends Shape {

    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Canvas canvas;
    private Paint paint;

    public LineShape(float startX, float startY, float endX, float endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawLine(startX,startY,endX,endY,paint);
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }
}
