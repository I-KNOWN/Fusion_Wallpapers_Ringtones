package com.livewallpaper.ringtones.callertune.editor.event;

import android.view.MotionEvent;

import com.livewallpaper.ringtones.callertune.editor.StickerView;


public interface StickerIconEvent {
    void onActionDown(StickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionMove(StickerView paramStickerView, MotionEvent paramMotionEvent);

    void onActionUp(StickerView paramStickerView, MotionEvent paramMotionEvent);
}
