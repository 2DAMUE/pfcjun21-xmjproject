package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        /**
         * appBar
         */
        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);
        findViewById(R.id.btn_fab).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notificacion:
                Intent intent2 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent2);
                break;
            case R.id.img_user:
                Intent intent3 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent3);
                break;
            case R.id.img_ayuda:
                Intent intent4 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent4);
                break;
            case R.id.tv_notificaciones:
                Intent intent5 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent5);
                break;
            case R.id.tv_cuenta:
                Intent intent6 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent6);
                break;
            case R.id.tv_ayuda:
                Intent intent7 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent7);
                break;
            case R.id.img_Home_Logo:
                Intent intent8 = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent8);
                break;
            case R.id.img_Search_Logo:
                Intent intent9 = new Intent(CalendarActivity.this, SearchRecycler.class);
                startActivity(intent9);
                break;
            case R.id.img_Historial_Logo:
                Intent intent10 = new Intent(CalendarActivity.this, FavoritosActivity.class);
                startActivity(intent10);
                break;
            case R.id.img_Calendar_Logo:
                Intent intent11 = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent11);
                break;
            case R.id.btn_fab:
                Intent intent25 = new Intent(CalendarActivity.this, OptionActivity.class);
                startActivity(intent25);
                break;

        }

    }

}