package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);

    }
    /**
     *navigation Bar
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        case R.id.img_Home_Logo:
        Intent intent8 = new Intent(CalendarActivity.this, MainActivity.class);
        startActivity(intent8);
        break;
        case R.id.img_Search_Logo:
        Intent intent9 = new Intent(CalendarActivity.this, SearchRecycler.class);
        startActivity(intent9);
        break;
        case R.id.img_Historial_Logo:
        Intent intent10 = new Intent(CalendarActivity.this, MainActivity.class);
        startActivity(intent10);
        break;
        case R.id.img_Calendar_Logo:
        Intent intent11 = new Intent(CalendarActivity.this, CalendarActivity.class);
        startActivity(intent11);
        break;
    }

    }
}