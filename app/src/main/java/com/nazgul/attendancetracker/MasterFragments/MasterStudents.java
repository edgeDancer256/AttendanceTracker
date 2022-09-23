package com.nazgul.attendancetracker.MasterFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nazgul.attendancetracker.R;


public class MasterStudents extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_students, container, false);
        v.refreshDrawableState();

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //MSc
        CardView cd = view.findViewById(R.id.student_course1);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //Sending to Student List
                Bundle bundle = new Bundle();
                bundle.putString("course", "MSC");
                StudentList sl = new StudentList();
                sl.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, sl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BSc
        cd = view.findViewById(R.id.student_course2);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("course", "BSC");
                StudentList sl = new StudentList();
                sl.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, sl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BCom
        cd = view.findViewById(R.id.student_course3);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("course", "BCOM");
                StudentList sl = new StudentList();
                sl.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, sl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BCA
        cd = view.findViewById(R.id.student_course4);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("course", "BCA");
                StudentList sl = new StudentList();
                sl.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, sl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BA
        cd = view.findViewById(R.id.student_course5);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("course", "BA");
                StudentList sl = new StudentList();
                sl.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, sl)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}
