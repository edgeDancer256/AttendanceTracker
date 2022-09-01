package com.nazgul.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Buttons for Logging in for different user roles
    Button admin_login;
    Button teacher_login;
    Button student_login;
    private FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null) {
            if(mAuth.getCurrentUser().getUid().startsWith("A6Pm")) { //REPLACE WITH "ADMIN" LATER!!!!!!!!
                Intent in = new Intent(MainActivity.this, AdminMenu.class);
                startActivity(in);
                finish();
            } else if(mAuth.getCurrentUser().getUid().startsWith("TCH")) {
                Intent in = new Intent(MainActivity.this, TeacherMenu.class);
                startActivity(in);
                finish();
            } else if(mAuth.getCurrentUser().getUid().startsWith("STD")) {
                Intent in = new Intent(MainActivity.this, StudentMenu.class);
                startActivity(in);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login button for admin (Master)
        admin_login = (Button) findViewById(R.id.masterBtn);
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sending to login screen for admin (Master)
                Intent in = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(in);

                finish();
            }
        });

        //Login button for Teacher
        teacher_login = (Button) findViewById(R.id.teacherbtn);
        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sending to login screen for Teacher
                Intent in = new Intent(MainActivity.this, TeacherLogin.class);
                startActivity(in);

                finish();
            }
        });

        //Login button for Student
        student_login = (Button) findViewById(R.id.studentBtn);
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sending to login screen for Student
                Intent in = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(in);

                finish();
            }
        });
    }
}
