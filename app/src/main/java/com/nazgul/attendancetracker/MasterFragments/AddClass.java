package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.MasterFragments.ClassList.InsertClass;
import com.nazgul.attendancetracker.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import kotlin.collections.ArrayDeque;


public class AddClass extends Fragment {

    String course_id;
    String cName;
    String semester;
    String subject;
    String teacher_id;

    TextView tv1;
    EditText class_id;
    EditText class_subject;
    EditText class_teacher_id;
    Spinner lv1;
    Button insert_class;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        cName = this.getArguments().getString("course_name");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_class, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tv1 = view.findViewById(R.id.add_class_course_name);
        class_id = view.findViewById(R.id.add_class_id_text);
        class_subject = view.findViewById(R.id.add_class_sub_name);
        class_teacher_id = view.findViewById(R.id.add_class_teacher_id);

        tv1.setText(cName);

        lv1 = view.findViewById(R.id.add_class_semester_spinner);

        String[] list = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8"
        };

        List<String> l1 = new ArrayDeque<String>(Arrays.asList(list));

        ArrayAdapter<String> ap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, l1);

        lv1.setAdapter(ap);

        lv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                semester = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Nothing selected yet", Toast.LENGTH_SHORT).show();
            }
        });


        insert_class = view.findViewById(R.id.add_class_insert);
        insert_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                course_id = class_id.getText().toString();
                subject = class_subject.getText().toString();
                teacher_id = class_teacher_id.getText().toString();

                ClassList cl = new ClassList();

                cl.new InsertClass().execute("/add_class.php", course_id, cName, semester, subject, teacher_id);

                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }
}
