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

    Button admin_login;
    Button teacher_login;
    Button student_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        admin_login = (Button) findViewById(R.id.masterBtn);
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(in);
            }
        });

        teacher_login = (Button) findViewById(R.id.teacherbtn);
        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, TeacherLogin.class);
                startActivity(in);
            }
        });

        student_login = (Button) findViewById(R.id.studentBtn);
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(in);
            }
        });
    }
}