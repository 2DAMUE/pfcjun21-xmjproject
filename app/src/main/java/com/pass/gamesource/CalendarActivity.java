package com.pass.gamesource;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener, ActualizarVideojuegosGratis {
    CompactCalendarView calendarView;
    CalendarActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AccesoFirebase.obtenerProximos(this, "");
        context = this;
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
        EditText searchCalendar = findViewById(R.id.searchCalendar);
        searchCalendar.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AccesoFirebase.obtenerProximos(context, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        calendarView = findViewById(R.id.calendarView);
        calendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());
        calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendarView.getEvents(dateClicked);
                ArrayList<Videojuego> videojuegosProximos = new ArrayList<Videojuego>();

                for (Event event : events) {
                    videojuegosProximos.add((Videojuego) event.getData());
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog alert = builder.create();

                View view2 = getLayoutInflater().inflate(R.layout.alert_dialog_calendar, null, false);

                alert.setView(view2);
                builder.setView(view2);

                RecyclerView recycler = view2.findViewById(R.id.recyclerAlertCalendario);
                TextView oops = view2.findViewById(R.id.tvOOPS);
                if (videojuegosProximos.size() > 0)
                    oops.setVisibility(View.GONE);

                RecyclerView.LayoutManager gestor = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                AdaptadorCalendario adaptador = new AdaptadorCalendario(videojuegosProximos, context);
                recycler.setLayoutManager(gestor);
                recycler.setAdapter(adaptador);

                alert.show();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
    }


    public void mostrarAlertDialog(Videojuego videojuego, CalendarActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        AlertDialog alert = builder.create();
        View view2 = getLayoutInflater().inflate(R.layout.alertdialogmain, null, false);

        alert.setView(view2);
        builder.setView(view2);

        TextView tvNombre = view2.findViewById(R.id.nombreAlert);
        TextView tvDescripcion = view2.findViewById(R.id.descripcionAlert);
        ImageView imagenAlert = view2.findViewById(R.id.imagenJuego);
        ImageView ivPlataforma = view2.findViewById(R.id.imgPlataforma);
        ivPlataforma.setVisibility(View.GONE);
        Button btnComparte = view2.findViewById(R.id.buttonComparteJuego);
        btnComparte.setVisibility(View.GONE);
        Button btnVerJuego = view2.findViewById(R.id.buttonVerJuego);
        btnVerJuego.setVisibility(View.GONE);
        ImageButton btnFavorito = view2.findViewById(R.id.btnFavorito);
        btnFavorito.setVisibility(View.GONE);

        btnVerJuego.setOnClickListener(vista -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videojuego.getUrl_origen()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            // intent.putExtra("URL", v.getUrl_origen());
            startActivity(intent);
        });
        btnComparte.setOnClickListener(vista -> {
            Intent compartir = new Intent(Intent.ACTION_SEND);
            compartir.setType("text/plain");
            compartir.putExtra(Intent.EXTRA_SUBJECT, "GameSource App");
            compartir.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_messageGame) + videojuego.getUrl_origen());
            startActivity(Intent.createChooser(compartir, "Compartir vía"));
        });

        Glide.with(view).load(videojuego.getImage_url()).centerCrop().into(imagenAlert);
        tvDescripcion.setText(videojuego.getDescripcion());
        tvNombre.setText(videojuego.getNombre());

        //builder.show();
        alert.show();
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
        calendarView.removeAllEvents();
        for (Videojuego videojuego : videojuegos) {
            try {
                long epoch = new java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(videojuego.getFecha_salida() + " 00:00:00").getTime();
                Event evento = new Event(R.color.teal_200, epoch, videojuego);
                Log.d("MENSAJE", evento.toString());
                calendarView.addEvent(evento);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //calendarView.addEvent(new Event(R.color.orange_GameSource, new Date(videojuego.getFecha_salida().split("/")[0])));
        }
    }
}