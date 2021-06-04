package com.pass.gamesource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_fondo_logoG, iv_fondo_logoS;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextInputEditText et_emailRegister;
    private TextInputEditText et_passRegister;
    private TextInputEditText et_rePassRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         * implements and starts animations
         */
        iv_fondo_logoG = findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS = findViewById(R.id.iv_fondo_logoS);
        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftlogin);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moverightlogin);
        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);

        findViewById(R.id.btn_registrarse).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        et_emailRegister = findViewById(R.id.et_emailRegister);
        et_passRegister = findViewById(R.id.et_passRegister);
        et_rePassRegister = findViewById(R.id.et_rePassRegister);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    //<---------------------------------Login Normal-----------------------------------------------<
    private void crearUsuario(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("prueba", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("prueba", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }


    //>---------------------------------Fin Normal------------------------------------------------->
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registrarse:
                String correolleno = et_emailRegister.getText().toString();
                String passlleno = et_passRegister.getText().toString();
                String repasslleno = et_rePassRegister.getText().toString();
                if (currentUser == null) {

                    if (!correolleno.equals("") && !passlleno.equals("") && !repasslleno.equals("")) {


                        if (passlleno.length() >= 6 && repasslleno.length() >= 6) {
                            if (passlleno.equals(repasslleno)) {
                                crearUsuario(correolleno, passlleno);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "La contraseña debe tener una longitud superior a 6 caracteres",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email y contraseña no pueden estar vacios",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Ya se ha iniciado sesion con " + currentUser.getEmail() ,
                            Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_back:
                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}