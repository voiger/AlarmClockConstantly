package com.example.alarmclockconstantly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InterfaceMainActivityAdapter {

    RecyclerView recyclerView;
    AlarmAdapter alarmAdapter;
    List<ItemAlarmArrayList> list;
    final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.RecyclerView);

        list = (List<ItemAlarmArrayList>) loadAlarm();

        if (list == null){
            list = new ArrayList<>();
        }

        alarmAdapter = new AlarmAdapter(this);

        alarmAdapter.setAlarm(list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(alarmAdapter);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        ConstraintLayout constraintLayout = findViewById(R.id.rootActivityLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void setAlarm(View view) {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Выбирите время для будильника")
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
            calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntent());

            alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

            ItemAlarmArrayList it = new ItemAlarmArrayList();
            it.setWasGone("0");
            it.setEndTime(simpleDateFormat.format(calendar.getTime()));
            list.add(it);
            alarmAdapter.setAlarm(list);
        });
        materialTimePicker.show(getSupportFragmentManager(), "PickerFragment");
    }

    private PendingIntent getAlarmInfoPendingIntent() {
        Intent alarmInfoIntent = new Intent(this, MainActivity.class);
        alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getAlarmActionPendingIntent() {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void temp(View view) {
        startActivity(new Intent(this, AlarmActivity.class));
    }

    private void saveAlarm(List<ItemAlarmArrayList> itemAlarmArrayList) {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putString("ListAlarm", gson.toJson(itemAlarmArrayList,ArrayList.class));
        ed.apply();
    }

    private List<ItemAlarmArrayList> loadAlarm() {
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);

        String load = sPref.getString("ListAlarm", "");
        Type type = new TypeToken<ArrayList<ItemAlarmArrayList>>() {}.getType();//
        return gson.fromJson(load, type);
    }


    @Override
    public void saveList(SortedList<ItemAlarmArrayList> list) {
        saveAlarm(fromSortedListToList(list));
        this.list = loadAlarm();
        alarmAdapter.setAlarm(this.list);
    }

    private List<ItemAlarmArrayList> fromSortedListToList(SortedList<ItemAlarmArrayList> sortedList){
        List<ItemAlarmArrayList> list = new ArrayList<>();
        for (int i = 0; sortedList.size() > i;i++){
            list.add(sortedList.get(i));
        }
        return list;
    }
}