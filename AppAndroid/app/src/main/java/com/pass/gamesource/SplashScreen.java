package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private MediaPlayer mediaplayer;
    private ImageView iv_fondo_logoG;
    private ImageView iv_fondo_logoS;
    private ImageView iv_fondo_G;
    private ImageView iv_fondo_S;
    private ImageView iv_encadenadas;
    private ImageView iv_game;
    private ImageView iv_sorce;
    private ImageView iv_gameSourcer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mediaplayer = MediaPlayer.create(this, R.raw.mystery);


        //implements and starts animations
        iv_fondo_logoG= findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS= findViewById(R.id.iv_fondo_logoS);
        iv_fondo_G= findViewById(R.id.iv_G);
        iv_fondo_S= findViewById(R.id.iv_S);
        iv_encadenadas = findViewById(R.id.iv_encadenadas);
        iv_game = findViewById(R.id.iv_game);
        iv_sorce = findViewById(R.id.iv_source);
        iv_gameSourcer = findViewById(R.id.iv_gameSource);


        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftt);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moveright);
        Animation animUplogoG = AnimationUtils.loadAnimation(this, R.anim.moveup);
        Animation animDownlogoS = AnimationUtils.loadAnimation(this, R.anim.movedown);
        Animation animAparecerEncadenadas = AnimationUtils.loadAnimation(this, R.anim.alphaencadenadas);
        Animation animReboteGame = AnimationUtils.loadAnimation(this, R.anim.rebotegame);
        Animation animReboteSource = AnimationUtils.loadAnimation(this, R.anim.rebotesource);
        Animation animGameSourceAparece = AnimationUtils.loadAnimation(this, R.anim.aphagamesourcer);


        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);
        iv_fondo_G.startAnimation(animUplogoG);
        iv_fondo_S.startAnimation(animDownlogoS);
        iv_encadenadas.startAnimation(animAparecerEncadenadas);
        iv_game.startAnimation(animReboteGame);
        iv_sorce.startAnimation(animReboteSource);
        iv_gameSourcer.startAnimation(animGameSourceAparece);


       //
        openApp(true);
        //openApp2(true);
        mediaplayer.start();
    }


    private void openApp(boolean locationPermission) {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen
                        .this, MainActivity.class); //Cambiar aqui para que pase a la nueva pantalla
                startActivity(intent);
                finish();

            }
        }, 4000);

    }


}