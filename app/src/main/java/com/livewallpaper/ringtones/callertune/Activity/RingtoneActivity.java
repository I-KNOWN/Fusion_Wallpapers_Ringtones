package com.livewallpaper.ringtones.callertune.Activity;

import static com.livewallpaper.ringtones.callertune.Utils.Constants.RINGTONE_PATH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
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
import android.widget.Toast;

import com.adsmodule.api.adsModule.retrofit.AdsResponseModel;
import com.adsmodule.api.adsModule.utils.Constants;
import com.google.gson.JsonObject;
import com.livewallpaper.ringtones.callertune.Adapter.RingtoneScrollAdapter;
import com.livewallpaper.ringtones.callertune.Model.ExtraCategoryModel;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ActivityRingtoneBinding;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RingtoneActivity extends AppCompatActivity {

    ActivityRingtoneBinding binding;
    RingtoneScrollAdapter adapter;
    ArrayList<String> data;
    MediaPlayer mediaPlayer;
    boolean isClosed = true;
    String category;
    int currentPos;
    List<String> id;
    String urlAudio = "https://www.api.idecloudstoragepanel.com/media/Applications_Data/com.livewallpaper.ringtones.callertune/Audio_Files/happiness/be_happy.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRingtoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id = new ArrayList<>();
        category = getIntent().getStringExtra("category");
        currentPos = getIntent().getIntExtra("pos", 0);
        iniAnimation();
        initRingtoneData();
        initViewPager2();
        initMore();
        initBtn();
    }

    private void initBtn() {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerCanceller();
                finish();
            }
        });
    }

    private void iniAnimation() {
        binding.clParentOption.animate()
                .translationY(-20)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        binding.clParentOption.setVisibility(View.GONE);

                    }
                })
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
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    binding.clParentOption.setVisibility(View.VISIBLE);
                                }
                            })
                            .setDuration(200);
                    isClosed = false;
                }else{
                    binding.clParentOption.animate()
                            .translationY(-20)
                            .alpha(0)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                }
                            })
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

                            File file = new File(RINGTONE_PATH, "set_ringtone_audio_" + System.currentTimeMillis() + ".mp3");
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (Settings.System.canWrite(RingtoneActivity.this)){
                                    setRingtone(RingtoneActivity.this, Uri.fromFile(file));
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


    public void setRingtone(Context context, Uri uri) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.TITLE, "RingTone_" + System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg");
        values.put(MediaStore.Audio.Media.ARTIST, context.getString(R.string.app_name));
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri newUri = context.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (newUri != null) {
            try {
                // Copy the content of the audioUri to the newUri
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                OutputStream outputStream = context.getContentResolver().openOutputStream(newUri);
                if (inputStream != null && outputStream != null) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    outputStream.close();
                }

                RingtoneManager.setActualDefaultRingtoneUri((Activity) context, RingtoneManager.TYPE_RINGTONE, newUri);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Ringtone has been set successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Song type is not supported", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
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
                    binding.ivPlay.setVisibility(View.GONE);
                    binding.ivPause.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ivBackword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPos != 0){
                    --currentPos;
                    binding.vp2Ringtone.setCurrentItem(currentPos);
                }
            }
        });

        binding.ivForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPos < id.size() -1 ){
                    ++currentPos;
                    binding.vp2Ringtone.setCurrentItem(currentPos);
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
                    if(
                            mediaPlayer.isPlaying()){
                        binding.ivPlay.setVisibility(View.GONE);
                        binding.ivPause.setVisibility(View.VISIBLE);
                    }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.ivPlay.setVisibility(View.GONE);
                binding.lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.seekTo(0);
            mediaPlayer.setDataSource(urlAudio);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.lottieAnimationView.setVisibility(View.GONE);
                            binding.ivPlay.setVisibility(View.VISIBLE);
                            initSeekbar();
                            initPlayerBtn();
                        }
                    });
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
        List<AdsResponseModel.ExtraDataFieldDTO.RingtoneCategoriesDTO> dto = Constants.adsResponseModel.getExtra_data_field().getRingtone_categories();
        int currentIndex = 0;
        for (int i = 0; i < dto.size(); i++) {
            if (dto.get(i).getCategory_name().equals(category)) {
                currentIndex = i;
            }
        }

        id.addAll(dto.get(currentIndex).getIds());
//            String baseUrl = Constants.adsResponseModel.getExtra_data_field().getKeyboard_base_url();
        for (int i = 0; i < id.size(); i++) {
            JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getRingtone_data();
            JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
            String urlKpop = kpop1.get("ringtone_img").getAsString();
            data.add(urlKpop);
        }


        binding.ivShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(id);
                data.clear();
                for (int i = 0; i < id.size(); i++) {
                    JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getRingtone_data();
                    JsonObject kpop1 = wallpaperData.getAsJsonObject(id.get(i));
                    String urlKpop = kpop1.get("ringtone_img").getAsString();
                    data.add(urlKpop);
                }
                adapter.initDataAgain(data);
                mediaPlayerCanceller();
                changeSongPreview();
            }
        });

    }

    private void initViewPager2() {
        adapter = new RingtoneScrollAdapter(RingtoneActivity.this, data);
        binding.vp2Ringtone.setAdapter(adapter);
        binding.vp2Ringtone.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPos = position;
                mediaPlayerCanceller();
                changeSongPreview();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }
        });
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
        binding.vp2Ringtone.setCurrentItem(currentPos);
    }
    private void mediaPlayerCanceller(){
        if (mediaPlayer!= null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mediaPlayer.stop();
        }
    }
    private void changeSongPreview() {
        String currentId = id.get(currentPos);
        JsonObject wallpaperData = Constants.adsResponseModel.getExtra_data_field().getRingtone_data();
        JsonObject kpop1 = wallpaperData.getAsJsonObject(currentId);
        String ringtoneName = kpop1.get("ringtone_name").getAsString();
        String ringtoneAthor = kpop1.get("ringtone_author").getAsString();
        String baseUrl = Constants.adsResponseModel.getExtra_data_field().getRingtone_base_url();
        binding.tvTitle.setText(ringtoneName);
        binding.tvname.setText(ringtoneName);
        binding.tvAuthor.setText(ringtoneAthor);
        urlAudio = (baseUrl+kpop1.get("url").getAsString());
        binding.ivPause.setVisibility(View.GONE);
        binding.ivPlay.setVisibility(View.VISIBLE);
        binding.ivPlayer.setOnClickListener(null);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                initMediaPlayer();
            }
        });
    }
}