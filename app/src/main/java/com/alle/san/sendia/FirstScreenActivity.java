package com.alle.san.sendia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alle.san.sendia.utils.PreferenceKeys;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Timer;
import java.util.TimerTask;

public class FirstScreenActivity extends AppCompatActivity {

    Button nButton;
    ImageView nImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        nButton = findViewById(R.id.btnLogin);
        nImageView = findViewById(R.id.ivAlle3);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstTime = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);
        if (isFirstTime){
            nButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startNext();
                    finish();
                }
            });
        }else{
            nButton.setVisibility(View.INVISIBLE);
            nImageView.setVisibility(View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.alle3);
            Glide.with(this)
                    .load(R.drawable.alle3)
                    .apply(requestOptions)
                    .into(nImageView);
            new Timer().schedule(new TimerTask(){
                @Override
                public void run(){
                    startNext();
                    finish();
                }
            }, 3000);
        }


    }
    private void startNext(){
        Intent intent = new Intent(FirstScreenActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
}