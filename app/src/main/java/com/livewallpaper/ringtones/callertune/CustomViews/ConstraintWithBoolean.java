package com.livewallpaper.ringtones.callertune.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.livewallpaper.ringtones.callertune.R;

public class ConstraintWithBoolean extends ConstraintLayout {

    private TypedArray typedArray;
    private boolean isOn;
    public ConstraintWithBoolean(@NonNull Context context) {
        super(context);
    }

    public ConstraintWithBoolean(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ConstraintWithBoolean);
        setIsOn(typedArray.getBoolean(R.styleable.ConstraintWithBoolean_isOn, false));
    }

    public void setIsOn(boolean isOn){
        this.isOn = isOn;
    }
    public boolean isOn(){
        return isOn;
    }

}
