package com.nazgul.attendancetracker.MenuActivities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherFragments.TeacherHome;
import com.nazgul.attendancetracker.TeacherFragments.TeacherNotif;
import com.nazgul.attendancetracker.TeacherFragments.TeacherProfile;
import com.nazgul.attendancetracker.TeacherFragments.TeacherReport;

public class TeacherMenu extends AppCompatActivity {

    //Bottom nav bar
    private BottomNavigationView bottomNavigationView;
    //Fragment to be displayed
    private Fragment selectFragment;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);

        String uid = mAuth.getCurrentUser().getUid();

        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);

        //Init bottom nav bar
        bottomNavigationView = findViewById(R.id.bottom_nav);

        //Item select listener for the bottom nav bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_home:
                        //Home fragment
                        selectFragment = new TeacherHome();
                        break;
                    case R.id.nav_report:
                        //Report fragment
                        selectFragment = new TeacherReport();
                        break;

                    case R.id.nav_profile:
                        //Profile fragment
                        selectFragment = new TeacherProfile();
                        break;
                }

                if(selectFragment != null) {
                    //Inflate selected fragment
                    selectFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).addToBackStack("tag").commit();
                }

                return true;
            }
        });

        //Load home fragment by default
        TeacherHome th = new TeacherHome();
        th.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, th).commit();
    }
}
