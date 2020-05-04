package com.get2gether;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

class RectangleView extends View {
    private Rect rectangle;
    private Paint paint;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public RectangleView (Context context, int xPos, int yPos, int width, int height) {
        super (context);
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        paint = new Paint();
        rectangle = new Rect(xPos, yPos, width, height);
        paint.setColor(Color.LTGRAY);
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getRectWidth() {
        return width;
    }
    public int getRectHeight() {
        return height;
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setXPos(int newXPos) {
        xPos = newXPos;
    }

    public void setYPos(int newYPos) {
        yPos = newYPos;
    }

    public void setRectWidth(int newWidth) {
        width = newWidth;
    }

    public void setRectHeight(int newHeight) {
        height = newHeight;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void onDraw (Canvas canvas) {
        canvas.drawRect(rectangle, paint);
    }
}