package com.get2gether;

import android.content.Context;

public class InteractiveRectangle extends RectangleView {
    private boolean selected;
    private int selectedColor;
    private int unselectedColor;

    public InteractiveRectangle(Context context, int xPos, int yPos, int width, int height) {
        super(context, xPos, yPos, width, height);
        selected = false;
        selectedColor = 0x00000000;
        unselectedColor = 0x00000000;

    }

    public InteractiveRectangle(Context context, int xPos, int yPos, int width, int height, int selectedColor, int unselectedColor) {
        super(context, xPos, yPos, width, height);
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
    }

    public void touch() {
        if (selected) {
            selected = false;
            setColor(unselectedColor);
        }
        else {
            selected = true;
            setColor(selectedColor);
        }
    }

}
