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

public class RegisterActivity extends AppCompatActivity {

    private TextView loginButton;
    private EditText registerUsername, registerPhone, registerEmail, registerPassword;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();

        firebaseAuth= FirebaseAuth.getInstance();
        registerUsername= findViewById(R.id.registerUsername);
        registerPhone= findViewById(R.id.registerPhone);
        registerEmail= findViewById(R.id.registerEmail);
        registerPassword= findViewById(R.id.registerPassword);
        registerButton= findViewById(R.id.registerButtonRegistration);
        loginButton= findViewById(R.id.loginButtonRegistration);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void RegisterUser() {
        String username= registerUsername.getText().toString().trim();
        String phone= registerPhone.getText().toString().trim();
        String email= registerEmail.getText().toString().trim();
        String password= registerPassword.getText().toString().trim();

        if (username.isEmpty()){
            registerUsername.setError("Please fill in your username!");
        }
        else if (phone.isEmpty()){
            registerPhone.setError("Please fill in your phone number!");
        }
        else if (email.isEmpty()) {
            registerEmail.setError("Please fill in your email!");
        }
        else if (password.isEmpty()) {
            registerPassword.setError("Please fill in your password!");
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "You're now registered!", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Couldn't register!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}