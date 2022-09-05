package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;

import java.util.Arrays;
import java.util.List;

import kotlin.collections.ArrayDeque;


public class AddStudent extends Fragment {


    EditText student_id;
    EditText student_name;
    EditText student_email;
    EditText student_phone;
    TextView student_course;
    Spinner sp1;
    Button add_student_btn;

    String studentID;
    String studentName;
    String studentEmail;
    String studentPhone;
    String course;
    String semester;

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
        sp1 = v.findViewById(R.id.add_student_semester_val);
        add_student_btn = v.findViewById(R.id.add_student_btn);

        student_course.setText(course);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] list = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8"
        };

        List<String> l1 = new ArrayDeque<String>(Arrays.asList(list));

        ArrayAdapter<String> ap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, l1);

        sp1.setAdapter(ap);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semester = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Nothing selected yet", Toast.LENGTH_SHORT).show();
            }
        });

        add_student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentID = student_id.getText().toString().trim();
                studentName = student_name.getText().toString().trim();
                studentEmail = student_email.getText().toString().trim();
                studentPhone = student_phone.getText().toString().trim();

                StudentList sl = new StudentList();
                sl.new AddStudentFirebase().execute(studentID, studentName, studentEmail, studentPhone, course, semester);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
