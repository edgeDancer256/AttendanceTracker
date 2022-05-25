package com.nazgul.attendancetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nazgul.attendancetracker.MasterFragments.Home;
import com.nazgul.attendancetracker.MasterFragments.Notification;
import com.nazgul.attendancetracker.MasterFragments.Profile;
import com.nazgul.attendancetracker.MasterFragments.Report;
import com.nazgul.attendancetracker.StudentFragments.StudentHome;
import com.nazgul.attendancetracker.StudentFragments.StudentNotif;
import com.nazgul.attendancetracker.StudentFragments.StudentProfile;
import com.nazgul.attendancetracker.StudentFragments.StudentReport;

public class StudentMenu extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectFragment;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        selectFragment = new StudentHome();
                        break;
                    case R.id.nav_report:
                        selectFragment = new StudentReport();
                        break;
                    case R.id.nav_notif:
                        selectFragment = new StudentNotif();
                        break;
                    case R.id.nav_profile:
                        selectFragment = new StudentProfile();
                        break;
                }

                if(selectFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StudentHome()).commit();

        bottomNavigationView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if(i3 > 0 && bottomNavigationView.isShown()) {
                    bottomNavigationView.setVisibility(View.GONE);
                } else if(i3 < 0) {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}