package com.livewallpaper.ringtones.callertune.Keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;

import com.livewallpaper.ringtones.callertune.R;
import com.livewallpaper.ringtones.callertune.databinding.ItemKeyboardBinding;

import java.util.ArrayList;

public class CustomKeyboard extends InputMethodService {
    private ItemKeyboardBinding binding;
    ArrayList<Integer> ids;
    boolean isUppercase = false;
    boolean isBackPress = false;
    Handler handler = new Handler();

    @Override
    public void onWindowHidden() {
        super.onWindowHidden();
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
/*        binding.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputConnection connection = getCurrentInputConnection();
                connection.commitText(".", 1);
            }
        });*/

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
                        binding.btnBackpress.setPressed(true);
                        handler.postDelayed(deleteTextRunnable, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        isBackPress = false;
                        binding.btnBackpress.setPressed(false);
                        handler.removeCallbacks(deleteTextRunnable);
                        break;

                }

                return true;
            }
        });



        binding.btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.mainText.setVisibility(View.INVISIBLE);
                binding.micParent.setVisibility(View.VISIBLE);
            }
        });

        initMicButton();



        return binding.getRoot();
    }

    private void initMicButton() {
        binding.btnKeyboardChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.micParent.setVisibility(View.GONE);
                binding.mainText.setVisibility(View.VISIBLE);
            }
        });
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