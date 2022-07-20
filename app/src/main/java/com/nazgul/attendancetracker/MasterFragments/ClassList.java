package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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
    //private static final String url = "http://192.168.0.105/att_tracker/class_list.php";
    private static final String url = "http://192.168.0.140/att_tracker/class_list.php";

    RecyclerView recView;
    RecyclerView.Adapter recAdapter;
    RecyclerView.LayoutManager recLayout;

    //List of the result rows
    ArrayList<ClassInfoCard> classInfoCards = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Asserting existence of arguments and retrieving them
        assert this.getArguments() != null;
        String cName = this.getArguments().getString("course");

        View v = inflater.inflate(R.layout.fragment_class_list, container, false);
        Context context = container.getContext();

        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        layparams.setMargins(10, 10, 10, 100);

        recView = v.findViewById(R.id.recycle1);
        recLayout = new LinearLayoutManager(getContext());

        //Call to Async method to query DB
        new ListDisp().execute(url, cName);
        return v;
    }

    public class ListDisp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String cName = params[1];
            try {
                //Try connection and store result
                String query = "?course="+cName;
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
                            + "Teacher ID : " + teacher_id +"\n";

                    classInfoCards.add(new ClassInfoCard(R.drawable.ic_delete, result, jObj.getString("course_id")));
                }

            } catch(Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), res, Toast.LENGTH_SHORT).show();

            }
            recAdapter = new ClassInfoAdapter(classInfoCards);
            recView.setLayoutManager(recLayout);
            recView.setAdapter(recAdapter);
            recAdapter.notifyDataSetChanged();
        }
    }

}
