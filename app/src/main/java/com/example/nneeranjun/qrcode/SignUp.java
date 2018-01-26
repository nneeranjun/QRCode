package com.example.nneeranjun.qrcode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText password;
    EditText email;
    ProgressBar spinner;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        spinner = findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        alertDialog = new AlertDialog.Builder(getApplicationContext());





    }
    public void signUp(View view){
        spinner.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            spinner.setVisibility(View.GONE);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),UserCreation.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            spinner.setVisibility(View.GONE);
                            alertDialog.setTitle("Error").setMessage("Unable to signup").create();

                        }

                        // ...
                    }
                });
    }
    public void goToLogin(View view){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }
}
