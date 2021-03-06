package com.pass.gamesource;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_fondo_logoG, iv_fondo_logoS;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final String TAG = "SignInActivity";
    private final int RC_SIGN_IN = 9001;
    public static String userEmail;
    private TextView mStatusTextView;
    private TextInputEditText correo;
    private TextInputEditText pass;
    private TextView register;
    private FirebaseUser user;

    //    Normal Login
    public static FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        /*
         * implements and starts animations
         */
        iv_fondo_logoG = findViewById(R.id.iv_fondo_logoG);
        iv_fondo_logoS = findViewById(R.id.iv_fondo_logoS);
        Animation animLeftlogoG = AnimationUtils.loadAnimation(this, R.anim.moveleftlogin);
        Animation animRightlogoS = AnimationUtils.loadAnimation(this, R.anim.moverightlogin);
        iv_fondo_logoG.startAnimation(animLeftlogoG);
        iv_fondo_logoS.startAnimation(animRightlogoS);
        register = findViewById(R.id.tv_Register);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_Register).setOnClickListener(this);
        findViewById(R.id.btn_Google).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button_Firebase).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_login_logueado).setOnClickListener(this);
        findViewById(R.id.tv_Register).setOnClickListener(this);

        mStatusTextView = findViewById(R.id.status);
        correo = findViewById(R.id.et_email);
        pass = findViewById(R.id.et_Password);
        //Configuracion de login con Google.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Cliente
        // Cree un GoogleSignInClient con las opciones especificadas por gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    //<---------------------------------Login Normal---------------------------------------------------<
    public void login(String email, String password) {
        Log.d("prueba", email + " : " + password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            userEmail = email;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("prueba", "signInWithEmail:success");
                            updateUIF(currentUser);
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("prueba", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIF(null);

                        }
                    }
                });
    }

    /**
     * Compruebe la cuenta de inicio de sesi??n de Google existente, si el usuario ya ha iniciado sesi??n
     */
    @Override
    public void onStart() {
        super.onStart();
        // INICIO on_start_sign_in
        // Compruebe la cuenta de inicio de sesi??n de Google existente, si el usuario ya ha iniciado sesi??n
        // GoogleSignInAccount no ser?? nulo.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && currentUser == null) {
            updateUI(account);
        } else {
            updateUIF(currentUser);
        }
        // END on_start_sign_in
    }

    /**
     * Conecta con Google y muestra las cuentas del usuario
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    // INICIO en Resultado de actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado devuelto al iniciar Intent desde GoogleSignInClient.getSignInIntent (...);
        if (requestCode == RC_SIGN_IN) {
            // La tarea devuelta de esta llamada siempre se completa, no es necesario adjuntar
            // un oyente
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    //FIN del resultado de la actividad

    /**
     * @param completedTask devuele true o false si se registra con exito
     */
    //INICIO manejar Inicio sesi??n del Resultado
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Se registr?? correctamente, muestra la interfaz de usuario autenticada.
            updateUI(account);
        } catch (ApiException e) {
            // El c??digo de estado de ApiException indica el motivo detallado del error.
            // Consulte la referencia de la clase GoogleSignInStatusCodes para obtener m??s informaci??n.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // Final

    /**
     * inicia la sesion de google
     */
    // Inico signIn
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // Final signIn

    /**
     * Cierra sesion de google
     */
    // Inicio acceso revocado
    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Inicio Excluido
                        updateUI(null);
                        // Fin Excluido
                    }
                });
    }
    // Fin acceso revocado

    /**
     * Cierra sesion de Firebase
     */
    // Inicio acceso revocado
    public void revokeAccessLoginNormal() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mAuth.signOut();
                        updateUIF(null);
                        // Fin Excluido
                    }
                });
    }

    // Fin acceso revocado
    private void updateUIF(FirebaseUser user) {
        if (user != null) {
            userEmail = user.getEmail();
            Toast.makeText(this, getString(R.string.signinwelcome, currentUser.getEmail()),
                    Toast.LENGTH_SHORT).show();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, user.getDisplayName()));
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
            findViewById(R.id.btn_Google).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.GONE);
            findViewById(R.id.btn_login_logueado).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button_Firebase).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.GONE);
            findViewById(R.id.et_email).setVisibility(View.GONE);
            findViewById(R.id.et_Password).setVisibility(View.GONE);
            findViewById(R.id.img_Google).setVisibility(View.GONE);
            findViewById(R.id.img_GameSource_encadenadas).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_Register).setEnabled(false);
            register.setTextColor(getResources().getColor(R.color.grayligh_GameSource));


        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.btn_Google).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.GONE);
            findViewById(R.id.disconnect_button_Firebase).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
            findViewById(R.id.et_email).setVisibility(View.VISIBLE);
            findViewById(R.id.et_Password).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_login_logueado).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
            findViewById(R.id.img_GameSource_encadenadas).setVisibility(View.GONE);
            findViewById(R.id.img_Google).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_Register).setEnabled(true);
            register.setTextColor(getResources().getColor(R.color.white_GameSource));

        }
    }

    /**
     * @param account le pasa los datos a ala cuenta si se ha inciado o no la sesion.
     *                comparamos resultado y realizamos acciones segun necesitemos
     */
    //Comprueba si estas conectado
    public void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, getString(R.string.signinwelcome, account.getEmail()),
                    Toast.LENGTH_SHORT).show();
            userEmail = account.getEmail();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_in);
            findViewById(R.id.btn_Google).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.GONE);
            findViewById(R.id.btn_login_logueado).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button_Firebase).setVisibility(View.GONE);
            findViewById(R.id.et_email).setVisibility(View.GONE);
            findViewById(R.id.et_Password).setVisibility(View.GONE);
            findViewById(R.id.img_GameSource_encadenadas).setVisibility(View.GONE);
            findViewById(R.id.tv_Register).setEnabled(false);
            register.setTextColor(getResources().getColor(R.color.grayligh_GameSource));

        } else {
            mStatusTextView.setText(R.string.signed_out);

            findViewById(R.id.btn_Google).setVisibility(View.VISIBLE);
            findViewById(R.id.disconnect_button).setVisibility(View.GONE);
            findViewById(R.id.disconnect_button_Firebase).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.status)).setText(R.string.signed_out);
            findViewById(R.id.et_email).setVisibility(View.VISIBLE);
            findViewById(R.id.et_Password).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_login_logueado).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
            findViewById(R.id.img_GameSource_encadenadas).setVisibility(View.GONE);
            findViewById(R.id.img_Google).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_Register).setEnabled(true);
            register.setTextColor(getResources().getColor(R.color.white_GameSource));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Register:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_Google:
                signIn();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
            case R.id.disconnect_button_Firebase:
                revokeAccessLoginNormal();
                break;
            case R.id.btn_login:
                String correolleno = correo.getText().toString();
                String passlleno = pass.getText().toString();
                if (!correolleno.equals("") && !passlleno.equals("")) {
                    if (passlleno.length() >= 6) {
                        login(correolleno, passlleno);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.valuempty),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login_logueado:
                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
    }
}