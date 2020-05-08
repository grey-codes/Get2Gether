package com.get2gether;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

class RectangleView extends View {
    private Paint paint;
    private int xPos;
    private int yPos;
    private int yOffset;
    private int width;
    private int height;
    private int borderSize;
    private int foregroundColor;
    private int backgroundColor;
    private int textSize;
    private String text;

    public RectangleView (Context context, int xPos, int yPos, int width, int height, int borderSize, int backgroundColor, int foregroundColor) {
        super (context);
        this.xPos = xPos;
        this.yPos = yPos;
        this.yOffset = 0;
        this.width = width;
        this.height = height;
        this.borderSize = borderSize;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        textSize = 100;
        text = "";
        paint = new Paint();
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

    public int getForegroundColor() {
        return foregroundColor;
    }
    
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public String getText() {
        return text;
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

    public void setForegroundColor(int newForegroundColor) {
        foregroundColor = newForegroundColor;
    }
    
    public void setBackgroundColor(int newBackgroundColor) {
        backgroundColor = newBackgroundColor;
    }

    public void setTextSize(int size) {
        textSize = size;
    }

    public void setText(String newText) {
        text = newText;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(backgroundColor);
        canvas.drawRect(xPos, yPos, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(foregroundColor);
        canvas.drawRect(xPos + borderSize, yPos + borderSize, getMeasuredWidth() - borderSize * 2, getMeasuredHeight() - borderSize * 2, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);

        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);

        canvas.drawText(text, width / 2 - (bounds.right-bounds.left) / 2, height / 2 + (bounds.bottom-bounds.top) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Defines the extra padding for the shape name text
        int textPadding = 10;
        int contentWidth = width;

        // Resolve the width based on our minimum and the measure spec
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        // Ask for a height that would let the view get as big as it can
        int minh = height + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        // Calling this method determines the measured width and height
        // Retrieve with getMeasuredWidth or getMeasuredHeight methods later
        setMeasuredDimension(w, h);
    }
}