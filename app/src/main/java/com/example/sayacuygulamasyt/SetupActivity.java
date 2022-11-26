package com.example.sayacuygulamasyt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btn_minus_up;
    Button btn_minus_low;

    Button btn_plus_up;
    Button btn_plus_low;

    CheckBox up_vib;
    CheckBox low_vib;

    CheckBox up_sound;
    CheckBox low_sound;

    int up_counter;
    int low_counter;

    EditText editText_up;
    EditText editText_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Context context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        btn_minus_low = (Button) findViewById(R.id.btn_mınus_low);
        btn_minus_up = (Button) findViewById(R.id.btn_mınus_up);

        btn_plus_up = (Button) findViewById(R.id.btn_plus_up);
        btn_plus_low =(Button) findViewById(R.id.btn_plus_low);

        up_sound = (CheckBox)findViewById(R.id.up_sound);
        up_vib = (CheckBox)findViewById(R.id.up_vib);
        low_vib = (CheckBox)findViewById(R.id.low_vib);
        low_sound = (CheckBox)findViewById(R.id.low_sound);

        editText_up = (EditText)findViewById(R.id.editText_up);
        editText_low = (EditText)findViewById(R.id.editText_low);

        up_vib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Up_Vib", b );
                editor.apply();
            }
        });
        low_vib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Low_Vib", b );
                editor.apply();
            }
        });
        up_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Up_Sound", b );
                editor.apply();
            }
        });
        low_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Low_Sound", b );
                editor.apply();
            }
        });


        btn_minus_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low_counter--;
                editText_low.setText(String.valueOf(low_counter));
            }
        });
        btn_plus_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low_counter++;
                editText_low.setText(String.valueOf(low_counter));
            }
        });

        btn_minus_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_counter--;
                editText_up.setText(String.valueOf(up_counter));
            }
        });
        btn_plus_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                up_counter++;
                editText_up.setText(String.valueOf(up_counter));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putInt("UpLimit",up_counter);
        editor.putInt("LowLimit",low_counter);
        editor.commit();

    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        editText_up.setText(String.valueOf(sharedPreferences.getInt("UpLimit", 20)));
        editText_low.setText(String.valueOf(sharedPreferences.getInt("LowLimit", 20)));
        up_vib.setChecked(sharedPreferences.getBoolean("Up_Vib", false));
        low_vib.setChecked(sharedPreferences.getBoolean("Low_Vib", false));
        up_sound.setChecked(sharedPreferences.getBoolean("Up_Sound", false));
        low_sound.setChecked(sharedPreferences.getBoolean("Low_Sound", false));

    }
}