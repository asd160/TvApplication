package com.example.ubuntu.bick.tvapplication.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ubuntu on 18-2-1.
 */

@SuppressLint("AppCompatCustomView")
public class BoldText extends TextView {

    public BoldText(Context context) {
        super(context);
    }

    public BoldText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoldText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }


    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(),"regular.ttf"), style);
    }
}
