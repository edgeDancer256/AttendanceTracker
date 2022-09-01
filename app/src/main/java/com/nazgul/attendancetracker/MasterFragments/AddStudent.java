package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nazgul.attendancetracker.R;


public class AddStudent extends Fragment {


    EditText student_id;
    EditText student_name;
    EditText student_email;
    EditText student_phone;
    TextView student_course;
    Button add_student_btn;

    String studentID;
    String studentName;
    String studentEmail;
    String studentPhone;
    String course;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert this.getArguments() != null;
        course = this.getArguments().getString("course");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_student, container, false);

        student_id = v.findViewById(R.id.add_student_id_text);
        student_name = v.findViewById(R.id.add_student_name_text);
        student_email = v.findViewById(R.id.add_student_email_text);
        student_phone = v.findViewById(R.id.add_student_phone_text);
        student_course = v.findViewById(R.id.add_student_course_text);
        add_student_btn = v.findViewById(R.id.add_student_btn);

        student_course.setText(course);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        add_student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentID = student_id.getText().toString();
                studentName = student_name.getText().toString();
                studentEmail = student_email.getText().toString();
                studentPhone = student_phone.getText().toString();

                StudentList sl = new StudentList();
                sl.new AddStudentFirebase().execute(studentID, studentName, studentEmail, studentPhone, course);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
