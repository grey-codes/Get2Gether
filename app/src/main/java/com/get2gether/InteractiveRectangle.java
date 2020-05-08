package com.get2gether;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class InteractiveRectangle extends RectangleView {
    private boolean selected;
    private boolean canBeToggled;
    private int selectedColor;
    private int unselectedColor;

    public InteractiveRectangle(Context context, int xPos, int yPos, int width, int height, int borderSize) {
        super(context, xPos, yPos, width, height, borderSize, 0x00000000,0x00000000);
        canBeToggled = true;
        selected = false;
        selectedColor = 0x00000000;
        unselectedColor = 0x00000000;
        //setOnTouchListener(this);
    }

    public InteractiveRectangle(Context context, int xPos, int yPos, int width, int height, int borderSize, int backgroundColor, int selectedColor, int unselectedColor) {
        super(context, xPos, yPos, width, height, borderSize, backgroundColor, unselectedColor);
        canBeToggled = true;
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
        //setOnTouchListener(this);
    }

    public boolean getSelected() {
        return selected;
    }

    public boolean getCanBeToggled() {
        return canBeToggled;
    }

    @Override
    public void setSelected(boolean selected) {
        if (canBeToggled) {
            this.selected = selected;
            if (selected) {
                setForegroundColor(selectedColor);
            }
            else {
                setForegroundColor(unselectedColor);
            }
            this.invalidate();

            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setFillEnabled(true);
            animationSet.setInterpolator(new BounceInterpolator());

            ScaleAnimation ta = new ScaleAnimation(1f,0.95f,1f,0.95f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            ta.setDuration(150);
            animationSet.addAnimation(ta);

            ScaleAnimation ta2 = new ScaleAnimation(0.95f,1f,0.95f,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            ta2.setDuration(150);
            ta2.setStartOffset(150);
            animationSet.addAnimation(ta2);
            this.startAnimation(animationSet);
        }
    }

    public void setCanBeToggled(boolean newSetting) {
        canBeToggled = newSetting;
    }
}
