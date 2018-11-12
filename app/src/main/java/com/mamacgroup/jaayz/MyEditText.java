package com.mamacgroup.jaayz;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyEditText extends android.support.v7.widget.AppCompatEditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyEditText(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        if (!isInEditMode()) {
            if(Session.get_user_language(context).equals("en")) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GothamRounded-Light.otf");
                setTypeface(tf);
            }else{
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Hacen Tunisia Lt.ttf");
                setTypeface(tf);
            }
        }

    }


}