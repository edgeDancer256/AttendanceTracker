package com.nazgul.attendancetracker.TeacherFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.nazgul.attendancetracker.R;


public class TchClassInfo extends Fragment {

    MaterialButton take_attendance;
    MaterialButton view_assignments;
    MaterialButton upload_qp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tch_class_info, container, false);

        take_attendance = v.findViewById(R.id.take_attendance);
        view_assignments = v.findViewById(R.id.view_assignments);
        upload_qp = v.findViewById(R.id.upload_qp);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewAssignments va = new ViewAssignments();
                Bundle bundle = new Bundle();
                bundle.putString("id", "meh");
                va.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, va)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        upload_qp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
