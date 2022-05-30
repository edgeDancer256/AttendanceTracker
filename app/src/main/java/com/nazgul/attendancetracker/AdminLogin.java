package com.nazgul.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.adminUsrName);
        password = (EditText) findViewById(R.id.adminPassword);
        login = (Button) findViewById(R.id.adminLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {

            startActivity(new Intent(AdminLogin.this, AdminMenu.class));
        }
    }

    private void signIn() {
        String emailID = email.getText().toString();
        String pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(emailID, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        startActivity(new Intent(AdminLogin.this, AdminMenu.class));
                    } else {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                        Toast.makeText(AdminLogin.this, "Please verify Email", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(AdminLogin.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}