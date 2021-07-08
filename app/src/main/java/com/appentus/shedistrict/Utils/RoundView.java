package com.appentus.shedistrict.Utils;

import android.graphics.drawable.GradientDrawable;

public class RoundView extends GradientDrawable {


    public RoundView(int pStartColor, float[] radius) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pStartColor, pStartColor});
        setShape(GradientDrawable.RECTANGLE);
        setCornerRadii(radius);

    }

    public RoundView(int pStartColor, int pMiddleColor, int pEndColor, float[] radius) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pMiddleColor, pEndColor});
        setShape(GradientDrawable.RECTANGLE);
        setOrientation(Orientation.LEFT_RIGHT);
        setCornerRadii(radius);

    }

    public RoundView(int pStartColor, float[] radius, boolean isStroke, int pStrokeColor) {
        super(Orientation.BOTTOM_TOP, new int[]{pStartColor, pStartColor, pStartColor});
        setShape(GradientDrawable.RECTANGLE);
        setCornerRadii(radius);

        if (isStroke) {
            setStroke(3, pStrokeColor);
        }

    }
}