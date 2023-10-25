package com.livewallpaper.ringtones.callertune.Keyboard;

import static com.livewallpaper.ringtones.callertune.SingletonClasses.AppOpenAds.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.livewallpaper.ringtones.callertune.Activity.MainActivity;
import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ItemKeyboardBinding;

import java.util.ArrayList;
import java.util.Locale;

public class CustomKeyboard extends InputMethodService {
    private ItemKeyboardBinding binding;
    ArrayList<Integer> ids;
    boolean isUppercase = false;
    boolean isBackPress = false;
    Handler handler = new Handler();
    Context context;
    String currentString = "";
    Animation scaleUp, scaleDown;
    @Override
    public void onWindowHidden() {
        super.onWindowHidden();
        currentString = "";
        isUppercase = false;
        isBackPress = false;
        initAlphabetData();
        initBtnCapsRe();
        initBtnSwitcher();
        initBtnCaps();

        binding.micParent.setVisibility(View.GONE);
        binding.mainText.setVisibility(View.VISIBLE);
    }

    private void initBtnSwitcher() {
        binding.btnNum.setText("123");
    }

    private void initBtnCapsRe() {
        binding.btnCaps.setBackgroundResource(R.drawable.button_ripple_white_transparent_uppercaes_bg);
        binding.btnCaps.setText("");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateInputView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ItemKeyboardBinding.inflate(inflater);
        context = binding.getRoot().getContext();
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.button_scale_down);
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.button_scale_up);

        initData();
        initABCData();

        initBtnCaps();


        binding.btnBackpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
        });
        binding.btnSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
            }
        });

        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            }
        });
        binding.btnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUppercase = false;
                if (binding.btnNum.getText().equals("123")){
                    binding.btnCaps.setBackgroundResource(R.drawable.button_ripple_white_transparent);
                    binding.btnCaps.setText("1/2");
                    binding.btnCaps.setTextColor(Color.WHITE);
                    binding.btnNum.setText("abc");
                    initNumberData();
                    initSpecialData1();
                    initBtnCapSwitcher();
                }else{
                    binding.btnCaps.setBackgroundResource(R.drawable.button_ripple_white_transparent_uppercaes_bg);
                    binding.btnCaps.setText("");
                    binding.btnNum.setText("123");
                    initBtnCaps();
                    initAlphabetData();
                }
            }
        });
        binding.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.commitText(".", 1);
            }
        });

        binding.btnCommma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_NUMPAD_COMMA));
            }
        });

        binding.btnBackpress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_MULTIPLE, KeyEvent.KEYCODE_BACK));
                return true;
            }
        });

        binding.btnBackpress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isBackPress = true;
                        binding.btnBackpress.performClick();
                        binding.btnBackpress.setPressed(true);
                        view.animate()
                                .scaleX(0.8f)
                                .scaleY(0.8f)
                                .alpha(0.7f)
                                .setDuration(50);
                        handler.postDelayed(deleteTextRunnable, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        isBackPress = false;
                        binding.btnBackpress.setPressed(false);
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(50);
                        handler.removeCallbacks(deleteTextRunnable);
                        break;

                }

                return true;
            }
        });



        binding.btnMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.mainText.setVisibility(View.INVISIBLE);
                binding.micParent.setVisibility(View.VISIBLE);
            }
        });

        initMicButton();



        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initMicButton() {
        binding.btnKeyboardChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.micParent.setVisibility(View.GONE);
                binding.mainText.setVisibility(View.VISIBLE);

            }
        });


        binding.cvMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(onCreateInputView().getContext());
                final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speechRecognizer.startListening(speechRecognizerIntent);

                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {

                    }

                    @Override
                    public void onBeginningOfSpeech() {

                    }

                    @Override
                    public void onRmsChanged(float rmsdB) {

                    }

                    @Override
                    public void onBufferReceived(byte[] buffer) {

                    }

                    @Override
                    public void onEndOfSpeech() {
                        currentString = "";
                    }

                    @Override
                    public void onError(int error) {

                    }

                    @Override
                    public void onResults(Bundle results) {
                        if(currentString.isEmpty()){
                            InputConnection connection = getCurrentInputConnection();
                            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                            connection.commitText(" "+data.get(0), 1);
                        }
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {
                        InputConnection connection = getCurrentInputConnection();
                        ArrayList<String> data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        String value = data.get(0);
                        Log.d("WhatistheData", currentString);
/*                        while (true){
                            if(!currentString.isEmpty()){
                                value = value.replace(currentString, "");

                                if(value.equals(data.get(0))){
                                    currentString = currentString.substring(0, currentString.length() - 2);
                                }else{
                                    break;
                                }
                            }else{
                                break;
                            }
                        }*/
                        value = value.replace(currentString, "");

                        currentString = currentString+value;
                        Log.d("WhatistheData", value);
                        connection.commitText(value, 1);
                    }

                    @Override
                    public void onEvent(int eventType, Bundle params) {

                    }
                });
            }
        });


        binding.btnEnterMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnEnter.performClick();
            }
        });

        binding.btnBackpressMic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {


                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isBackPress = true;
                        binding.btnBackpress.performClick();
                        binding.btnBackpressMic.setPressed(true);
                        view.animate()
                                .scaleX(0.8f)
                                .scaleY(0.8f)
                                .alpha(0.7f)
                                .setDuration(50);
                        handler.postDelayed(deleteTextRunnable, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        isBackPress = false;
                        binding.btnBackpressMic.setPressed(false);
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(50);
                        handler.removeCallbacks(deleteTextRunnable);
                        break;

                }

                return true;
            }
        });

        binding.btnSpaceMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
            }
        });


        binding.btnCommmaMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputConnection connection = getCurrentInputConnection();
                connection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_COMMA));
            }
        });

        binding.btnDotMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputConnection connection = getCurrentInputConnection();
                connection.commitText(".", 1);
            }
        });

        initAllButtonClick();


    }

    private void initAllButtonClick() {
        initButtonclickEffect(binding.btnCaps);

        initButtonclickEffect(binding.btnCommma);
        initButtonclickEffect(binding.btnDot);
        initButtonclickEffect(binding.btnEnter);
        initButtonclickEffect(binding.btnSpace);
//        initButtonclickEffect(binding.btnKeyboardChanger);
        initButtonclickEffect(binding.btnNum);
        initButtonclickEffect(binding.btnMic);
        initButtonclickEffect(binding.btnSpaceMic);
        initButtonclickEffect(binding.btnEnterMic);
        initButtonclickEffect(binding.btnCommmaMic);
        initButtonclickEffect(binding.btnDotMic);
    }

    private void initButtonclickEffect(View view) {

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    v.performClick();
                    v.setPressed(true);
                    v.animate()
                            .scaleX(0.8f)
                            .scaleY(0.8f)
                            .alpha(0.7f)
                            .setDuration(50);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    v.setPressed(false);
                    v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .alpha(1f)
                            .setDuration(50);
                }
                return true;
            }
        });

    }

    private boolean checkPermission(String permission) {
        int res = checkCallingOrSelfPermission(permission);

        return res == PackageManager.PERMISSION_GRANTED;
    }



    private void initBtnCapSwitcher() {
        binding.btnCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(binding.btnCaps.getText().equals("1/2")){
                    binding.btnCaps.setText("2/2");
                    initSpecialData2();

                }else if (binding.btnCaps.getText().equals("2/2")){
                    binding.btnCaps.setText("1/2");
                    initSpecialData1();
                }
            }
        });

    }

    private Runnable deleteTextRunnable = new Runnable() {
        @Override
        public void run() {
            if (isBackPress) {
                binding.btnBackpress.performClick();
                handler.postDelayed(this, 100);
            }
        }
    };
    private void initSpecialData2() {
        Button button = binding.getRoot().findViewById(R.id.btn_a);
        button.setText("!");
        button = binding.getRoot().findViewById(R.id.btn_s);
        button.setText("@");
        button = binding.getRoot().findViewById(R.id.btn_d);
        button.setText("#");
        button = binding.getRoot().findViewById(R.id.btn_f);
        button.setText("$");
        button = binding.getRoot().findViewById(R.id.btn_g);
        button.setText("%");
        button = binding.getRoot().findViewById(R.id.btn_h);
        button.setText("^");
        button = binding.getRoot().findViewById(R.id.btn_j);
        button.setText("&");
        button = binding.getRoot().findViewById(R.id.btn_k);
        button.setText("|");
        button = binding.getRoot().findViewById(R.id.btn_l);
        button.setText("\\");

        button = binding.getRoot().findViewById(R.id.btn_z);
        button.setText("(");
        button = binding.getRoot().findViewById(R.id.btn_x);
        button.setText(")");
        button = binding.getRoot().findViewById(R.id.btn_c);
        button.setText("{");
        button = binding.getRoot().findViewById(R.id.btn_v);
        button.setText("}");
        button = binding.getRoot().findViewById(R.id.btn_b);
        button.setText("[");
        button = binding.getRoot().findViewById(R.id.btn_n);
        button.setText("]");
        button = binding.getRoot().findViewById(R.id.btn_m);
        button.setText("~");
    }

    private void initSpecialData1() {


        Button button = binding.getRoot().findViewById(R.id.btn_a);
        button.setText("+");
        button = binding.getRoot().findViewById(R.id.btn_s);
        button.setText("×");
        button = binding.getRoot().findViewById(R.id.btn_d);
        button.setText("÷");
        button = binding.getRoot().findViewById(R.id.btn_f);
        button.setText("=");
        button = binding.getRoot().findViewById(R.id.btn_g);
        button.setText("/");
        button = binding.getRoot().findViewById(R.id.btn_h);
        button.setText("_");
        button = binding.getRoot().findViewById(R.id.btn_j);
        button.setText("<");
        button = binding.getRoot().findViewById(R.id.btn_k);
        button.setText(">");
        button = binding.getRoot().findViewById(R.id.btn_l);
        button.setText("*");

        button = binding.getRoot().findViewById(R.id.btn_z);
        button.setText("-");
        button = binding.getRoot().findViewById(R.id.btn_x);
        button.setText("'");
        button = binding.getRoot().findViewById(R.id.btn_c);
        button.setText("\"");
        button = binding.getRoot().findViewById(R.id.btn_v);
        button.setText(":");
        button = binding.getRoot().findViewById(R.id.btn_b);
        button.setText(";");
        button = binding.getRoot().findViewById(R.id.btn_n);
        button.setText(",");
        button = binding.getRoot().findViewById(R.id.btn_m);
        button.setText("?");
    }

    private void initBtnCaps() {
        binding.btnCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isUppercase){
                    binding.btnCaps.setBackgroundResource(R.drawable.button_ripple_white_transparent_uppercaes_bg_filled);
                    for(int id: ids){
                        Button button = binding.getRoot().findViewById(id);
                        button.setText(button.getText().toString().toUpperCase());
                    }
                    isUppercase = true;
                }else{
                    binding.btnCaps.setBackgroundResource(R.drawable.button_ripple_white_transparent_uppercaes_bg);
                    for(int id: ids){
                        Button button = binding.getRoot().findViewById(id);
                        button.setText(button.getText().toString().toLowerCase());
                    }
                    isUppercase = false;
                }


            }
        });
    }

    private void initAlphabetData() {
        Button button = binding.getRoot().findViewById(R.id.btn_q);
        button.setText("q");
        button = binding.getRoot().findViewById(R.id.btn_w);
        button.setText("w");
        button = binding.getRoot().findViewById(R.id.btn_e);
        button.setText("e");
        button = binding.getRoot().findViewById(R.id.btn_r);
        button.setText("r");
        button = binding.getRoot().findViewById(R.id.btn_t);
        button.setText("t");
        button = binding.getRoot().findViewById(R.id.btn_y);
        button.setText("y");
        button = binding.getRoot().findViewById(R.id.btn_u);
        button.setText("u");
        button = binding.getRoot().findViewById(R.id.btn_i);
        button.setText("i");
        button = binding.getRoot().findViewById(R.id.btn_o);
        button.setText("o");
        button = binding.getRoot().findViewById(R.id.btn_p);
        button.setText("p");


        button = binding.getRoot().findViewById(R.id.btn_a);
        button.setText("a");
        button = binding.getRoot().findViewById(R.id.btn_s);
        button.setText("s");
        button = binding.getRoot().findViewById(R.id.btn_d);
        button.setText("d");
        button = binding.getRoot().findViewById(R.id.btn_f);
        button.setText("f");
        button = binding.getRoot().findViewById(R.id.btn_g);
        button.setText("g");
        button = binding.getRoot().findViewById(R.id.btn_h);
        button.setText("h");
        button = binding.getRoot().findViewById(R.id.btn_j);
        button.setText("j");
        button = binding.getRoot().findViewById(R.id.btn_k);
        button.setText("k");
        button = binding.getRoot().findViewById(R.id.btn_l);
        button.setText("l");

        button = binding.getRoot().findViewById(R.id.btn_z);
        button.setText("z");
        button = binding.getRoot().findViewById(R.id.btn_x);
        button.setText("x");
        button = binding.getRoot().findViewById(R.id.btn_c);
        button.setText("c");
        button = binding.getRoot().findViewById(R.id.btn_v);
        button.setText("v");
        button = binding.getRoot().findViewById(R.id.btn_b);
        button.setText("b");
        button = binding.getRoot().findViewById(R.id.btn_n);
        button.setText("n");
        button = binding.getRoot().findViewById(R.id.btn_m);
        button.setText("m");
    }

    private void initNumberData() {

        Button button = binding.getRoot().findViewById(R.id.btn_q);
        button.setText("1");
        button = binding.getRoot().findViewById(R.id.btn_w);
        button.setText("2");
        button = binding.getRoot().findViewById(R.id.btn_e);
        button.setText("3");
        button = binding.getRoot().findViewById(R.id.btn_r);
        button.setText("4");
        button = binding.getRoot().findViewById(R.id.btn_t);
        button.setText("5");
        button = binding.getRoot().findViewById(R.id.btn_y);
        button.setText("6");
        button = binding.getRoot().findViewById(R.id.btn_u);
        button.setText("7");
        button = binding.getRoot().findViewById(R.id.btn_i);
        button.setText("8");
        button = binding.getRoot().findViewById(R.id.btn_o);
        button.setText("9");
        button = binding.getRoot().findViewById(R.id.btn_p);
        button.setText("0");

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initABCData() {
        for(int id: ids){
            Button button = binding.getRoot().findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputConnection connection = getCurrentInputConnection();
                    connection.commitText(button.getText().toString(), 1);
                }
            });
            initButtonclickEffect(button);


        }
    }
    public void initData(){
        ids = new ArrayList<>();
        ids.add(R.id.btn_a);
        ids.add(R.id.btn_b);
        ids.add(R.id.btn_c);
        ids.add(R.id.btn_d);
        ids.add(R.id.btn_e);
        ids.add(R.id.btn_f);
        ids.add(R.id.btn_g);
        ids.add(R.id.btn_h);
        ids.add(R.id.btn_i);
        ids.add(R.id.btn_j);
        ids.add(R.id.btn_k);
        ids.add(R.id.btn_l);
        ids.add(R.id.btn_m);
        ids.add(R.id.btn_n);
        ids.add(R.id.btn_o);
        ids.add(R.id.btn_p);
        ids.add(R.id.btn_q);
        ids.add(R.id.btn_r);
        ids.add(R.id.btn_s);
        ids.add(R.id.btn_t);
        ids.add(R.id.btn_u);
        ids.add(R.id.btn_v);
        ids.add(R.id.btn_w);
        ids.add(R.id.btn_x);
        ids.add(R.id.btn_y);
        ids.add(R.id.btn_z);
    }

}
