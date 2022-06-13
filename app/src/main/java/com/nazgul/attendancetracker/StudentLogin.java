package com.nazgul.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

            mAuth = FirebaseAuth.getInstance();

            email = (EditText) findViewById(R.id.studentUsrName);
            password = (EditText) findViewById(R.id.studentPassword);
            login = (Button) findViewById(R.id.studentLogin);

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

                startActivity(new Intent(StudentLogin.this, StudentMenu.class));
                finish();
            }
        }

        private void signIn() {
            String emailID = email.getText().toString();
            String pass = password.getText().toString();

            mAuth.signInWithEmailAndPassword(emailID, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        startActivity(new Intent(StudentLogin.this, StudentMenu.class));
                        finish();
                    } else {
                        Toast.makeText(StudentLogin.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}