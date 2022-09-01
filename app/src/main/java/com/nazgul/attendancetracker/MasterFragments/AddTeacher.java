package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;


public class AddTeacher extends Fragment {

    EditText teacher_id;
    EditText teacher_name;
    EditText teacher_email;
    EditText teacher_phone;
    EditText teacher_dept;
    Button add_teacher_submit;

    String teacherID;
    String teacherName;
    String teacherEmail;
    String teacherPhone;
    String teacherDept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_teacher, container, false);

        teacher_id = v.findViewById(R.id.add_teacher_id_text);
        teacher_name = v.findViewById(R.id.add_teacher_name_text);
        teacher_email = v.findViewById(R.id.add_teacher_email_text);
        teacher_phone = v.findViewById(R.id.add_teacher_phone_text);
        teacher_dept = v.findViewById(R.id.add_teacher_dept_text);
        add_teacher_submit = v.findViewById(R.id.add_teacher_btn);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        add_teacher_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacherID = teacher_id.getText().toString();
                teacherName = teacher_name.getText().toString();
                teacherEmail = teacher_email.getText().toString();
                teacherPhone = teacher_phone.getText().toString();
                teacherDept = teacher_dept.getText().toString();

                MasterTeachers mt = new MasterTeachers();

                mt.new AddTeacherFirebase().execute(teacherID, teacherName, teacherEmail, teacherPhone, teacherDept);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}
