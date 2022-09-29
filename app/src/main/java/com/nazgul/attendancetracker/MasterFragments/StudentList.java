package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.MasterAdapters.StudentInfoAdapter;
import com.nazgul.attendancetracker.AdminInfoCards.StudentInfoCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class StudentList extends Fragment {

    String db_url;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    String course;
    Button add_student;
    String res;
    String std_id;

    String student_id;
    String student_name;
    String student_email;
    String student_phone;
    String student_course;
    String student_semester;

    ArrayList<StudentInfoCard> studentInfoCardArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        //Assert existence of argument bundle
        assert this.getArguments() != null;
        course = this.getArguments().getString("course");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_list, container, false);
        Context context = v.getContext();
        recyclerView = v.findViewById(R.id.student_recycler);
        layoutManager = new LinearLayoutManager(context);
        add_student = v.findViewById(R.id.add_student);

        new StudentListDatabase().execute(db_url + "/admin/student_list.php", course);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.refreshDrawableState();
        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("course", course);
                AddStudent addStudent = new AddStudent();
                addStudent.setArguments(bundle);
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, addStudent)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    public class StudentListDatabase extends AsyncTask<String,Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String courseName = params[1];

            try {
                //Try connection and store result
                String query = "?course=" + courseName;
                URL url = new URL(params[0] + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }
                br.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch(Exception e) {
                Log.d("err", e.toString());
                return e.getMessage();
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String s) {
            studentInfoCardArrayList.clear();

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jObj = null;
                for(int i = 0; i < jsonArray.length(); i++) {
                    jObj = jsonArray.getJSONObject(i);
                    String std_name = jObj.getString("student_name");
                    String std_id = jObj.getString("student_id");
                    String std_email = jObj.getString("student_email");
                    String std_ph_no = jObj.getString("student_phone");
                    String std_sem = jObj.getString("semester");
                    String std_course = jObj.getString("student_course");

                    String result = "Name : " + std_name + "\n"
                            + "ID : " + std_id + "\n"
                            + "Email : " + std_email + "\n"
                            + "Ph. No. : " + std_ph_no + "\n"
                            + "Semester : " + std_sem + "\n";

                    studentInfoCardArrayList.add(new StudentInfoCard(R.drawable.ic_delete,
                            result,
                            std_id,
                            std_course,
                            std_sem));
                }
            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(),
                        "Something went wrong :(",
                        Toast.LENGTH_SHORT).show();
            }

            adapter = new StudentInfoAdapter(studentInfoCardArrayList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public class AddStudentFirebase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            student_id = params[0];
            student_name = params[1];
            student_email = params[2];
            student_phone = params[3];
            student_course = params[4];
            student_semester = params[5];
            String query = "?uid=" + student_id
                    + "&email=" + student_email
                    + "&phoneNumber=" + student_phone
                    + "&password=" + student_id
                    + "&displayName=" + student_name;

            try {
                URL url = new URL(db_url + "/firebase/create_user.php" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }

                res = "Name : " + student_name + "\n"
                        + "ID : " + student_id + "\n"
                        + "Email : " + student_email + "\n"
                        + "Ph. No. : " + student_phone + "\n";
                br.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch(Exception e) {
                Log.d("err_conn", e.toString());
                Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            new AddStudentDatabase().execute(student_id, student_name, student_email, student_phone, student_course, student_semester);

            studentInfoCardArrayList.add(new StudentInfoCard(R.drawable.ic_delete,
                    res,
                    student_id,
                    student_course,
                    student_semester));
            Log.d("res", "User added successfully");
        }
    }

    public class AddStudentDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String student_id = params[0];
            String student_name = params[1];
            String student_email = params[2];
            String student_phone = params[3];
            String student_course = params[4];
            String student_sem = params[5];

            String query = "?student_id=" + student_id
                    + "&student_name=" + student_name
                    + "&student_email=" + student_email
                    + "&student_phone=" + student_phone
                    + "&student_course=" + student_course
                    + "&student_semester=" + student_sem;

            try {
                URL url = new URL(db_url + "/admin/add_students.php" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }
                br.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch(Exception e) {
                Log.d("err_conn", e.toString());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("res", s);
        }
    }
}
