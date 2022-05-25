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
import com.nazgul.attendancetracker.TeacherFragments.TeacherHome;
import com.nazgul.attendancetracker.TeacherFragments.TeacherNotif;
import com.nazgul.attendancetracker.TeacherFragments.TeacherProfile;
import com.nazgul.attendancetracker.TeacherFragments.TeacherReport;

public class TeacherMenu extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectFragment;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        selectFragment = new TeacherHome();
                        break;
                    case R.id.nav_report:
                        selectFragment = new TeacherReport();
                        break;
                    case R.id.nav_notif:
                        selectFragment = new TeacherNotif();
                        break;
                    case R.id.nav_profile:
                        selectFragment = new TeacherProfile();
                        break;
                }

                if(selectFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                }

                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TeacherHome()).commit();

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
