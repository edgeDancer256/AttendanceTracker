package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherInfoAdapter;
import com.nazgul.attendancetracker.TeacherInfoCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MasterTeachers extends Fragment {

    //Credentials for server access
    //edgeDancer
    //private static final String url_get_teacher = "http://192.168.0.105/att_tracker/firebase/get_teachers.php";
    //l1ght
    //private static final String url_get_teacher = "http://192.168.1.19/att_tracker/firebase/get_teachers.php";
    //l1ght hotspot
    //private static final String url_get_teacher = "http://192.168.57.104/att_tracker/firebase/get_teachers.php";
    //College
    private static final String url_get_teacher = "http://192.168.0.140/att_tracker/firebase/get_teachers.php";

    //Credentials for server access
    //edgeDancer
    //private static final String url_create_teacher = "http://192.168.0.105/att_tracker/firebase/create_teacher.php";
    //l1ght
    //private static final String url_create_teacher = "http://192.168.1.19/att_tracker/firebase/create_teacher.php";
    //l1ght hotspot
    //private static final String url_create_teacher = "http://192.168.57.104/att_tracker/firebase/create_teacher.php";
    //College
    private static final String url_create_teacher = "http://192.168.0.140/att_tracker/firebase/create_teacher.php";

    RecyclerView recView;
    RecyclerView.Adapter recAdapter;
    RecyclerView.LayoutManager recLayout;
    Button add_teacher;

    String newTeacher;
    String teacher_ID;

    ArrayList<TeacherInfoCard> teacherInfoCardArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_teachers, container, false);

        recView = v.findViewById(R.id.add_teacher_recycler);
        recLayout = new LinearLayoutManager(getContext());
        add_teacher = v.findViewById(R.id.add_teacher);

        new TeacherList().execute(url_get_teacher);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddTeacher())
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    public class TeacherList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
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
                return e.getMessage();
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String res) {
            teacherInfoCardArrayList.clear();

            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String teacher_name = jObj.getString("displayName");
                    String teacher_id = jObj.getString("uid");
                    String teacher_email = jObj.getString("email");
                    String teacher_ph_no = jObj.getString("phoneNumber");

                    String result = "Name : " + teacher_name + "\n"
                            + "ID : " + teacher_id + "\n"
                            + "Email : " + teacher_email + "\n"
                            + "Ph. No. : " + teacher_ph_no + "\n";

                    teacherInfoCardArrayList.add(new TeacherInfoCard(R.drawable.ic_delete, result, teacher_id));
                }
            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
            }

            recAdapter = new TeacherInfoAdapter(teacherInfoCardArrayList);
            recView.setLayoutManager(recLayout);
            recView.setAdapter(recAdapter);
            recAdapter.notifyDataSetChanged();
        }
    }

    public class AddTeacherFirebase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            teacher_ID = params[0];
            String teacherID = params[0];
            String teacherName = params[1];
            String teacherEmail = params[2];
            String teacherPhone = params[3];
            String query = "?uid=" + teacherID
                    + "&email=" + teacherEmail
                    + "&phoneNumber=" + teacherPhone
                    + "&password=" + teacherID
                    + "&displayName=" + teacherName;

            try {
                URL url = new URL(url_create_teacher + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }

                newTeacher = "Name : " + teacherName + "\n"
                        + "ID : " + teacherID + "\n"
                        + "Email : " + teacherEmail + "\n"
                        + "Ph. No. : " + teacherPhone + "\n";
                br.close();
                httpURLConnection.disconnect();
                return sb.toString();
            } catch(Exception e) {
                Log.d("err_conn", e.toString());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            teacherInfoCardArrayList.add(new TeacherInfoCard(R.drawable.ic_delete, newTeacher, teacher_ID));
        }
    }
}
