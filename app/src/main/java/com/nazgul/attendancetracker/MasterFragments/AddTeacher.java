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

import java.util.regex.Pattern;


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
                teacherID = teacher_id.getText().toString().trim();
                teacherName = teacher_name.getText().toString().trim();
                teacherEmail = teacher_email.getText().toString().trim();
                teacherPhone = teacher_phone.getText().toString().trim();
                teacherDept = teacher_dept.getText().toString().trim();

                if(teacherID.startsWith("TCH") && isEmail(teacherEmail) && isPhone(teacherPhone)) {
                    MasterTeachers mt = new MasterTeachers();

                    mt.new AddTeacherFirebase().execute(teacherID, teacherName, teacherEmail, teacherPhone, teacherDept);

                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    teacher_id.setText("");
                    teacher_name.setText("");
                    teacher_email.setText("");
                    teacher_phone.setText("");
                    teacher_dept.setText("");
                    Toast.makeText(getContext(), "Please enter valid information", Toast.LENGTH_SHORT).show();
                    teacher_id.requestFocus();
                }


            }
        });
    }


    public static boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isPhone(String phone) {
        String phRegex = "^[6-9]+d{9}";
        Pattern pattern = Pattern.compile(phRegex);
        if(phone == null)
            return false;
        return pattern.matcher(phone).matches();
    }
}
