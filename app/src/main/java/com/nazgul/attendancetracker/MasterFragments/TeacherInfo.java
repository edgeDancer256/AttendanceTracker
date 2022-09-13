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


public class TeacherInfo extends Fragment {

    //Credentials for server access
    //edgeDancer
    private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    //private static final String db_url = "http://192.168.1.19/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.39.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

   String teacherID;
   TextView data;
   ListView classList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        teacherID = this.getArguments().getString("teacher_id");

        View v = inflater.inflate(R.layout.fragment_teacher_info, container, false);
        data = v.findViewById(R.id.teacher_info_data);
        classList = v.findViewById(R.id.teacher_info_classes);

        new GetTeacherInfo().execute(db_url, teacherID);
        new GetTeacherClasses().execute(db_url, teacherID);

        // Inflate the layout for this fragment
        return v;
    }


    //Async Method to get Teacher Info(Dept, Name)
    public class GetTeacherInfo extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            pd.setTitle("Loading Teacher Info...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String tID = params[1];
            //Log.d("uid", tID);

            String query = "?tid=" + tID;

            try {
                URL url = new URL(params[0] + "/admin/teacher_info.php" + query);
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
        protected void onPostExecute(String res) {
            pd.dismiss();

            try {
                JSONArray jsonArray = new JSONArray(res);
                JSONObject jsonObject = null;

                for(int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String teacherName = jsonObject.getString("teacherName");
                    String teacherDept = jsonObject.getString("teacherDept");

                    String result = teacherName + "\n" + teacherDept + "\n";

                    data.setText(result);
                }
            } catch(Exception e) {
                data.setText(e.toString());
            }
        }
    }


    //Async Method to get the classes that the Teacher teaches
    public class GetTeacherClasses extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String tID = params[1];
            //Log.d("uid", tID);

            String query = "?tid=" + tID;

            try {
                URL url = new URL(params[0] + "/admin/teacher_classes.php" + query);
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
        protected void onPostExecute(String res) {
            ArrayList<String> class_list = new ArrayList<String>();
            try {
                JSONArray jsonArray = new JSONArray(res);
                JSONObject jsonObject = null;

                for(int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String courseID = jsonObject.getString("course_id");
                    String courseName = jsonObject.getString("course_name");
                    String subject = jsonObject.getString("subject");
                    String semester = jsonObject.getString("semester");

                    String result = "\nCourseID : " + courseID + "\n"
                            + "Course Name : " + courseName + "\n"
                            + "Subject : " + subject + "\n"
                            + "Semester : " + semester + "\n";

                    class_list.add(result);
                }

            } catch(Exception e) {
                class_list.add("No classes are handled by this teacher yet");
            }
            ArrayAdapter<String> ap = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, class_list);

            classList.setAdapter(ap);
        }
    }
}
