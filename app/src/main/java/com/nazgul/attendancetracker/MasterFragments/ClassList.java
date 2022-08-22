package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.ClassInfoAdapter;
import com.nazgul.attendancetracker.ClassInfoCard;
import com.nazgul.attendancetracker.R;

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
import java.util.List;
import java.util.Objects;

public class ClassList extends Fragment {

    //Credentials for server access
    //edgeDancer
    private static final String url = "http://192.168.0.105/att_tracker/class_list.php";
    //l1ght
    //private static final String url = "http://192.168.1.19/att_tracker/class_list.php";
    //l1ght hotspot
    //private static final String url = "http://192.168.57.104/att_tracker/class_list.php";
    //College
    //private static final String url = "http://192.168.0.140/att_tracker/class_list.php";

    RecyclerView recView;
    RecyclerView.Adapter recAdapter;
    RecyclerView.LayoutManager recLayout;
    Button add_class;
    String cName;
    String res;
    String c_id;

    //List of the result rows
    ArrayList<ClassInfoCard> classInfoCards = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Asserting existence of arguments and retrieving them
        assert this.getArguments() != null;
        cName = this.getArguments().getString("course");

        View v = inflater.inflate(R.layout.fragment_class_list, container, false);
        Context context = container.getContext();

        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        layparams.setMargins(10, 10, 10, 100);

        recView = v.findViewById(R.id.recycle1);
        recLayout = new LinearLayoutManager(getContext());
        add_class = v.findViewById(R.id.add_class);

        //Call to Async method to query DB
        new ListDisp().execute(url, cName);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddClass addClass = new AddClass();
                Bundle bundle = new Bundle();
                bundle.putString("course_name", cName);
                addClass.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, addClass)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    public class ListDisp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cName = params[1];
            try {
                //Try connection and store result
                String query = "?course=" + cName;
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
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String res) {
            classInfoCards.clear();

            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String course_id = jObj.getString("course_id");
                    String course_name = jObj.getString("course_name");
                    String semester = jObj.getString("semester");
                    String subject = jObj.getString("subject");
                    String teacher_id = jObj.getString("teacher_id");

                    String result = "Course ID : " + course_id + "\n"
                            + "Subject : " + subject + "\n"
                            + "Course Name : " + course_name + "\n"
                            + "Semester : " + semester + "\n"
                            + "Teacher ID : " + teacher_id + "\n";

                    Log.d("info", result);

                    classInfoCards.add(new ClassInfoCard(R.drawable.ic_delete, result, jObj.getString("course_id")));
                }

            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }
            recAdapter = new ClassInfoAdapter(classInfoCards);
            recView.setLayoutManager(recLayout);
            recView.setAdapter(recAdapter);
            recAdapter.notifyDataSetChanged();

        }
    }

    public  class InsertClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            c_id = params[1];
            String course_id = params[1];
            String course_name = params[2];
            String semester = params[3];
            String subject = params[4];
            String teacher_id = params[5];

            String query = "?course_id=" + course_id
                    + "&course_name=" + course_name
                    + "&semester=" + semester
                    + "&subject=" + subject
                    + "&teacher_id=" + teacher_id;

            res = "Course ID : " + course_id + "\n"
                    + "Subject : " + subject + "\n"
                    + "Course Name : " + course_name + "\n"
                    + "Semester : " + semester + "\n"
                    + "Teacher ID : " + teacher_id + "\n";

            try {
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
            classInfoCards.add(new ClassInfoCard(R.drawable.ic_delete, res, c_id));
        }
    }
}
