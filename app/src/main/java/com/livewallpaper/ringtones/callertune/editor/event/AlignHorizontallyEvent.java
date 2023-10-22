package com.livewallpaper.ringtones.callertune.editor.event;

import android.view.MotionEvent;

import com.livewallpaper.ringtones.callertune.editor.StickerView;


public class AlignHorizontallyEvent implements StickerIconEvent {
    public void onActionDown(StickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionMove(StickerView paramStickerView, MotionEvent paramMotionEvent) {
    }

    public void onActionUp(StickerView paramStickerView, MotionEvent paramMotionEvent) {
        paramStickerView.alignHorizontally();
    }
}
