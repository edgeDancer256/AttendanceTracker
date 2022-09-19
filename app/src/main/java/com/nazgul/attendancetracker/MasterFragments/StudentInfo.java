package com.nazgul.attendancetracker.MasterFragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class StudentInfo extends Fragment {


    //Credentials for server access
    //edgeDancer
    //private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    private static final String db_url = "http://192.168.1.11/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.39.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    String student_id;
    String course;
    String semester;
    TextView student_data;
    ListView student_classes;

    ArrayList<String> class_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        student_id = this.getArguments().getString("id");
        course = this.getArguments().getString("course");
        semester = this.getArguments().getString("sem");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_info, container, false);
        student_data = v.findViewById(R.id.student_info_data);
        student_classes = v.findViewById(R.id.student_info_classes);

        new GetStudentInfo().execute(student_id);
        new GetStudentClasses().execute(course, semester);

        return v;
    }



    public class GetStudentInfo extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            pd.setTitle("Loading Student Info...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String id = params[0];

            String query = "/admin/student_info.php?sid=" + id;

            try {
                URL url = new URL(db_url + query);
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

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();

            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("student_name");
                    String course = jsonObject.getString("student_course");
                    String sem = jsonObject.getString("semester");

                    String res = "Name : " + name + "\n"
                            + "Course : " + course + "\n"
                            + "Semester : " + sem + "\n";

                    student_data.setText(res);
                }
            } catch(Exception e) {
                student_data.setText(e.getMessage());
            }
        }
    }


    public class GetStudentClasses extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String course = params[0];
            String semseter = params[1];

            String query = "/admin/student_classes.php?course=" + course + "&semester=" + semseter;

            try {
                URL url = new URL(db_url + query);
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

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String subject = jsonObject.getString("subject");
                    String teacher_id = jsonObject.getString("teacher_id");

                    String res = "\nSubject : " + subject + "\n"
                            + "Teacher ID : " + teacher_id + "\n";

                    class_list.add(res);
                }
            } catch(Exception e) {
                class_list.add("No classes for this student?");
            }

            ArrayAdapter<String> ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, class_list);
            student_classes.setAdapter(ap);
        }
    }
}
