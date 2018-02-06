package com.teethen.sdk.xwidget.autolayout.utils;

import android.util.TypedValue;

public class DimenUtils
{
    private static int getComplexUnit(int data)
    {
        return TypedValue.COMPLEX_UNIT_MASK & (data >> TypedValue.COMPLEX_UNIT_SHIFT);
    }

    public static boolean isPxVal(TypedValue val)
    {
        if (val != null && val.type == TypedValue.TYPE_DIMENSION &&
                getComplexUnit(val.data) == TypedValue.COMPLEX_UNIT_PX)
        {
            return true;
        }
        return false;
    }
}