package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nazgul.attendancetracker.R;

import java.util.Objects;


public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Handle click for Classes Card
        CardView cd = view.findViewById(R.id.cardview_home1);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send to MasterClasses
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MasterClasses())
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //Handle click for Teachers Card
        CardView cd2 = view.findViewById(R.id.cardview_home2);
        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send to MasterTeachers
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MasterTeachers())
                        .addToBackStack("tag")
                        .commit();
            }
        });


        //Handle click for Students Card
        CardView cd1 = view.findViewById(R.id.cardview_home3);
        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send to MasterClasses
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MasterStudents())
                        .addToBackStack("tag")
                        .commit();
            }
        });


        CardView cd3 = view.findViewById(R.id.file_retrieval);
        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new FileDisplay())
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}
