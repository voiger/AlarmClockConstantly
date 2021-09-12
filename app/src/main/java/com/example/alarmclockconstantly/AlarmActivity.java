package com.example.alarmclockconstantly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;

public class AlarmActivity extends AppCompatActivity {

    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ConstraintLayout constraintLayout = findViewById(R.id.alarmActivityLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

//        Uri notificationURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        ringtone = RingtoneManager.getRingtone(this, notificationURI);
//        if (ringtone == null){
//            notificationURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//            ringtone = RingtoneManager.getRingtone(this, notificationURI);
//        }
//        if (ringtone != null){
//            ringtone.play();
//        }
    }

    @Override
    protected void onDestroy() {
        if (ringtone != null && ringtone.isPlaying()){
            ringtone.stop();
        }
        super.onDestroy();

    }
}