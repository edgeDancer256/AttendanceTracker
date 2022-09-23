package com.nazgul.attendancetracker.StudentFragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.StudentAdapters.StudentHomeInfoAdapter;
import com.nazgul.attendancetracker.StudentInfoCards.StudentHomeCard;
import com.nazgul.attendancetracker.TeacherAdapters.TeacherHomeInfoAdapter;
import com.nazgul.attendancetracker.TeacherFragments.TeacherHome;
import com.nazgul.attendancetracker.TeacherInfoCards.HomeCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class StudentHome extends Fragment {

    //Credentials for server access
    //edgeDancer
    private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    //private static final String db_url = "http://192.168.1.11/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.39.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<StudentHomeCard> studentHomeCardArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        String uid = this.getArguments().getString("uid");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_home, container, false);

        recyclerView = v.findViewById(R.id.student_home_recycler);
        layoutManager = new LinearLayoutManager(getContext());

        new StudentHomeInfo().execute(uid);

        return v;
    }

    public class StudentHomeInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String sid = params[0];

            String query = "?sid=" + sid;

            try {
                URL url = new URL(db_url + "/students/get_class_list.php" + query);
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
            studentHomeCardArrayList.clear();
            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String subject = jObj.getString("subject");
                    String semester = jObj.getString("semester");
                    String course = jObj.getString("course_name");
                    String course_id = jObj.getString("course_id");

                    String result = "\nSubject : " + subject + "\n";

                    studentHomeCardArrayList.add(new StudentHomeCard(result, course, semester,course_id));
                }
            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(), "Something went wrong...take a guess what...", Toast.LENGTH_SHORT).show();
            }

            adapter = new StudentHomeInfoAdapter(studentHomeCardArrayList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
