package com.example.sayacuygulamasyt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ShakeDetector mShakeDetector;
    SensorManager mSensorManager;
    Sensor mAccelerometer;

    EditText count;
    Button btn_minus;
    Button btn_plus;
    Button btn_settings;
    int counter;

    int up_limit;
    int low_limit;

    boolean up_vib;
    boolean low_vib;
    boolean up_sound;
    boolean low_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                counter = 0;
            }
        });

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MediaPlayer sound = MediaPlayer.create(this, R.raw.uyarisesi);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        count = (EditText) findViewById(R.id.count);
        btn_settings = (Button) findViewById(R.id.btn_settings);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent);
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(counter>low_limit){
                counter--;
                count.setText(String.valueOf(counter));}
                if(counter==low_limit){
                    if(low_sound){sound.start();}
                    if(low_vib){v.vibrate(2000);}
                }
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(counter<up_limit){
                counter++;
                count.setText(String.valueOf(counter));
                    if(counter==up_limit){
                        if(up_sound){sound.start();}
                        if(up_vib){v.vibrate(2000);}
                    }
            }}
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
          counter-=5;
          count.setText(String.valueOf(counter));
      }
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            counter+=5;
            count.setText(String.valueOf(counter));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        up_limit = sharedPreferences.getInt("UpLimit",20);
        low_limit = sharedPreferences.getInt("LowLimit",-10);
        up_vib = sharedPreferences.getBoolean("Up_Vib",false);
        up_sound = sharedPreferences.getBoolean("Up_Sound",false);
        low_vib = sharedPreferences.getBoolean("Low_Vib",false);
        low_sound = sharedPreferences.getBoolean("Low_Sound",false);
    }
}