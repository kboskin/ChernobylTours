package com.tourkiev.chernobyltours.helpers;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.hyphen.SqueezeHyphenator;

/**
 * Created by hp on 029 29.12.2017.
 */

public class PressableDocumentView extends DocumentView {
    public PressableDocumentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPressableDocumentView();
    }

    public PressableDocumentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPressableDocumentView();
    }

    public PressableDocumentView(Context context) {
        super(context);
        initPressableDocumentView();
    }

    public PressableDocumentView(Context context, int type) {
        super(context, type);
        initPressableDocumentView();
    }

    public PressableDocumentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPressableDocumentView();
    }

    private void initPressableDocumentView() {
        getDocumentLayoutParams().setHyphenator(SqueezeHyphenator.getInstance());
        getDocumentLayoutParams().setHyphenated(true);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);

        if (pressed) {
            getDocumentLayoutParams().setTextColor(Color.BLACK);
            setDisallowInterceptTouch(true);
        } else {
            getDocumentLayoutParams().setTextColor(Color.WHITE);
            setDisallowInterceptTouch(false);
        }

        invalidate();
    }
}
