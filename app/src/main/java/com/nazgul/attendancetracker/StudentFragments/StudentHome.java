package com.nazgul.attendancetracker.StudentFragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nazgul.attendancetracker.MasterFragments.MasterClasses;
import com.nazgul.attendancetracker.R;


public class StudentHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Handle click for file upload Card
        CardView cd = view.findViewById(R.id.cardview_file_upload);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send to MasterClasses
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StudentFileUpload())
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}