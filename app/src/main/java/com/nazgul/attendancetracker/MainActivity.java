package com.nazgul.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Buttons for Logging in for different user roles
    Button admin_login;
    Button teacher_login;
    Button student_login;

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