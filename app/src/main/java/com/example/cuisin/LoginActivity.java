package com.example.cuisin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText loginEmail, loginPassword;
    private TextView registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        firebaseAuth= FirebaseAuth.getInstance();
        loginEmail= findViewById(R.id.loginEmail);
        loginPassword= findViewById(R.id.loginPassword);
        registerButton= findViewById(R.id.registerButtonLogin);
        loginButton= findViewById(R.id.loginButtonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void LoginUser() {
        String email= loginEmail.getText().toString().trim();
        String password= loginPassword.getText().toString().trim();

        if (email.isEmpty()) {
            loginEmail.setError("Please fill in your email!");
        }
        else if (password.isEmpty()) {
            loginPassword.setError("Please fill in your password!");
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "You're now logged in!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Check your credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}