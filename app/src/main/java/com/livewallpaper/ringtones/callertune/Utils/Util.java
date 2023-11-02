package com.livewallpaper.ringtones.callertune.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.TextView;

import com.livewallpaper.ringtones.callertune.R;

public class Util {

    static Dialog dialog;
    static Handler handler;
    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            TextView tv_progress = dialog.findViewById(R.id.tv_download);
            if(tv_progress.getText().toString().equals("Downloading...")){
                tv_progress.setText("Downloading");
            }else if(tv_progress.getText().toString().equals("Downloading")){
                tv_progress.setText("Downloading.");
            }else if(tv_progress.getText().toString().equals("Downloading.")){
                tv_progress.setText("Downloading..");
            }else if(tv_progress.getText().toString().equals("Downloading..")){
                tv_progress.setText("Downloading...");
            }
            handler.postDelayed(runnable, 700);
        }
    };

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static void showDownloadDialog(Context context){
        if(dialog == null){
            dialog = new Dialog(context);
            handler = new Handler();
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.download_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
        updateDialogProgress();
    }


    public static void updateDialogProgress(){
        handler.postDelayed(runnable, 700);
    }

    public static void hideDownloadDialog(){
        if (dialog.isShowing() && dialog != null){
            handler.removeCallbacks(runnable);
            dialog.dismiss();
        }
    }


    public static void shareApp(Activity activity) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage = "Introducing the Ultimate All-in-One App!\n" +
                    "\n" +
                    "Transform your digital experience with our app, combining Wallpaper, Ringtone, and Keyboard features in one place. Personalize your device's look with stunning wallpapers, set unique ringtones, and enjoy a feature-rich keyboard for expressive typing. Download now and make your device truly yours!";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + activity.getPackageName() + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "Share Via"));
        } catch (Exception e) {
            //e.toString();
        }
    }

}
