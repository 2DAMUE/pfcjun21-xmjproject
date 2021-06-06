package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosGratis {
    CompactCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AccesoFirebase.obtenerProximos(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        /*
         * appBar
         */
        findViewById(R.id.img_Home_Logo).setOnClickListener(this);
        findViewById(R.id.img_Search_Logo).setOnClickListener(this);
        findViewById(R.id.img_Historial_Logo).setOnClickListener(this);
        findViewById(R.id.img_Calendar_Logo).setOnClickListener(this);
        findViewById(R.id.btn_fab).setOnClickListener(this);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnClickListener(v -> {
            // calendarView.get
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_notificacion:
                Intent intent2 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent2);
                break;
            case R.id.tv_notificaciones:
                Intent intent5 = new Intent(CalendarActivity.this, SplashScreen.class);
                startActivity(intent5);
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
                Toast.makeText(this, getString(R.string.here),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_fab:
                Intent intent25 = new Intent(CalendarActivity.this, OptionActivity.class);
                startActivity(intent25);
                break;

        }

    }

    @Override
    public void recuperarVideojuegos(ArrayList<Videojuego> videojuegos) {
        for (Videojuego videojuego : videojuegos) {
            try {
                long epoch = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(videojuego.getFecha_salida() + " 00:00:00").getTime();
                Event evento = new Event(R.color.orange_GameSource, epoch);
                Log.d("MENSAJE", evento.toString());
                calendarView.addEvent(evento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //calendarView.addEvent(new Event(R.color.orange_GameSource, new Date(videojuego.getFecha_salida().split("/")[0])));
        }
    }
}