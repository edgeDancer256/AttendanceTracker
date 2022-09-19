package com.nazgul.attendancetracker.StudentFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.nazgul.attendancetracker.R;


public class StudentHomeInfo extends Fragment {

    MaterialButton attendance;
    MaterialButton assignment;
    String c_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert this.getArguments() != null;
        c_id = this.getArguments().getString("id");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_home_info, container, false);

        attendance = v.findViewById(R.id.student_attendance);
        assignment = v.findViewById(R.id.student_assignment);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentAssignment studentAssignment = new StudentAssignment();
                Bundle bundle = new Bundle();
                bundle.putString("id", c_id);
                studentAssignment.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, studentAssignment)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}
