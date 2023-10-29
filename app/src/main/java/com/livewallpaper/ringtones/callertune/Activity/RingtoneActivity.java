package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_PATH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.livewallpaper.ringtones.callertune.Adapter.RingtoneScrollAdapter;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityRingtoneBinding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RingtoneActivity extends AppCompatActivity {

    ActivityRingtoneBinding binding;
    RingtoneScrollAdapter adapter;
    ArrayList<Integer> data;
    MediaPlayer mediaPlayer;
    boolean isClosed = true;
    String urlAudio = "https://www.api.idecloudstoragepanel.com/media/Applications_Data/com.livewallpaper.ringtones.callertune/Audio_Files/happiness/be_happy.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRingtoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniAnimation();

        initRingtoneData();
        initViewPager2();
        initMediaPlayer();
        initMore();
    }

    private void iniAnimation() {
        binding.clParentOption.animate()
                .translationY(-20)
                .alpha(0)
                .setDuration(0);
    }

    private void initMore() {
        binding.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClosed){
                    binding.clParentOption.animate()
                            .translationY(0)
                            .alpha(1f)
                            .setDuration(200);
                    isClosed = false;
                }else{
                    binding.clParentOption.animate()
                            .translationY(-20)
                            .alpha(0)
                            .setDuration(200);
                    isClosed = true;

                }
            }
        });

        binding.llSetRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        int count;
                        try {
                            URL url = new URL(urlAudio);
                            URLConnection connection = url.openConnection();
                            connection.connect();
                            InputStream input = new BufferedInputStream(url.openStream(), 8192);

                            File dir = new File(RINGTONE_PATH);
                            if(!dir.exists()){
                                dir.mkdir();
                            }

                            File file = new File(RINGTONE_PATH, "set_ringtone_audio.mp3");
                            OutputStream output = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                output = Files.newOutputStream(file.toPath());
                            }else {
                                output = new FileOutputStream(file);

                            }

                            byte data[] = new byte[1024];

                            while ((count = input.read(data)) != -1) {
                                output.write(data, 0, count);
                            }

                            output.flush();
                            output.close();
                            input.close();


                            File k = new File(RINGTONE_PATH, "set_ringtone_audio.mp3"); // path is a file to /sdcard/media/ringtone

                            ContentValues values = new ContentValues();
                            values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
                            values.put(MediaStore.MediaColumns.TITLE, "My Song title");
                            values.put(MediaStore.MediaColumns.SIZE, 215454);
                            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg");
                            values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
                            values.put(MediaStore.Audio.Media.DURATION, 230);
                            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
                            values.put(MediaStore.Audio.Media.IS_ALARM, false);
                            values.put(MediaStore.Audio.Media.IS_MUSIC, false);

//Insert it into the database
                            Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
                            String filePathToDelete = MediaStore.MediaColumns.DATA + "=\"" + k.getAbsolutePath() + "\"";
                            getContentResolver().delete(uri, filePathToDelete, null);
                            Uri newUri = getContentResolver().insert(uri, values);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (Settings.System.canWrite(RingtoneActivity.this)){
                                    RingtoneManager.setActualDefaultRingtoneUri(
                                            RingtoneActivity.this,
                                            RingtoneManager.TYPE_RINGTONE,
                                            newUri
                                    );
                                }
                                else {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }


                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                        }

                    }
                });
            }
        });
    }

    private void initPlayerBtn() {

        binding.lottieAnimationView.setVisibility(View.GONE);
        binding.ivPlay.setVisibility(View.VISIBLE);
        binding.ivPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    binding.ivPlay.setVisibility(View.VISIBLE);
                    binding.ivPause.setVisibility(View.GONE);
                }else{
                    mediaPlayer.start();
                    binding.ivPlay.setVisibility(View.GONE);
                    binding.ivPause.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ivRepeat.setAlpha(1f);
        binding.ivRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(0);
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        });

    }

    private void initSeekbar() {
        int duration = mediaPlayer.getDuration();
        binding.maxDuration.setText(getTimeString(duration));
        binding.seekbarAudio.setMax(mediaPlayer.getDuration()/1000);
        Handler mHandler = new Handler();
//Make sure you update Seekbar on UI thread
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    Log.d("mediaplayercurrentpositon", "Current: "+mediaPlayer.getCurrentPosition());
                    binding.seekbarAudio.setProgress(mCurrentPosition);
                    String current = mediaPlayer.getCurrentPosition()/1000+"";
                    binding.currentDuration.setText(getTimeString(mediaPlayer.getCurrentPosition()));
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        binding.seekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                    mediaPlayer.start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.seekTo(0);
            mediaPlayer.setDataSource(urlAudio);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    initSeekbar();
                    initPlayerBtn();
                }
            });
            mediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    private void initRingtoneData() {
        data = new ArrayList<>();
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
        data.add(R.drawable.sample_ringtone_img);
    }

    private void initViewPager2() {
        adapter = new RingtoneScrollAdapter(RingtoneActivity.this, data);
        binding.vp2Ringtone.setAdapter(adapter);
        binding.vp2Ringtone.setClipToPadding(false);
        binding.vp2Ringtone.setClipChildren(false);
        binding.vp2Ringtone.setOffscreenPageLimit(3);
        binding.vp2Ringtone.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r  = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });

        binding.vp2Ringtone.setPageTransformer(compositePageTransformer);
    }
}