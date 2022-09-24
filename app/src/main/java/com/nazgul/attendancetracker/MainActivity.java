package com.nazgul.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.nazgul.attendancetracker.LoginActivities.AdminLogin;
import com.nazgul.attendancetracker.LoginActivities.StudentLogin;
import com.nazgul.attendancetracker.LoginActivities.TeacherLogin;
import com.nazgul.attendancetracker.MenuActivities.AdminMenu;
import com.nazgul.attendancetracker.MenuActivities.StudentMenu;
import com.nazgul.attendancetracker.MenuActivities.TeacherMenu;

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
            if(mAuth.getCurrentUser().getUid().startsWith("ADMIN")) {
                if(mAuth.getCurrentUser().isEmailVerified()) {
                    Intent in = new Intent(MainActivity.this, AdminMenu.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in = new Intent(MainActivity.this, AdminLogin.class);
                    startActivity(in);
                    Toast.makeText(this, "Please verify email.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if(mAuth.getCurrentUser().getUid().startsWith("TCH")) {
                if(mAuth.getCurrentUser().isEmailVerified()) {
                    Intent in = new Intent(MainActivity.this, TeacherMenu.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in = new Intent(MainActivity.this, TeacherLogin.class);
                    startActivity(in);
                    Toast.makeText(this, "Please verify email.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if(mAuth.getCurrentUser().getUid().startsWith("STD")) {
                if(mAuth.getCurrentUser().isEmailVerified()) {
                    Intent in = new Intent(MainActivity.this, StudentMenu.class);
                    startActivity(in);
                    finish();
                } else {
                    Intent in = new Intent(MainActivity.this, StudentLogin.class);
                    startActivity(in);
                    Toast.makeText(this, "Please verify email.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Login button for admin (Master)
        admin_login = findViewById(R.id.masterBtn);
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
        teacher_login = findViewById(R.id.teacherbtn);
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
        student_login = findViewById(R.id.studentBtn);
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
