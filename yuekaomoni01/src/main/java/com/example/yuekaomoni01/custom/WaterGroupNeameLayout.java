package com.example.yuekaomoni01.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.yuekaomoni01.R;

@SuppressLint("AppCompatCustomView")
public class WaterGroupNeameLayout extends TextView {
    public WaterGroupNeameLayout(Context context) {
        super(context);
    }

    public WaterGroupNeameLayout(Context context,  AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.WeekLayout);
        int color = array.getColor(R.styleable.WeekLayout_textColor, Color.BLUE);
        setTextColor(color);
        array.recycle();
    }
}
