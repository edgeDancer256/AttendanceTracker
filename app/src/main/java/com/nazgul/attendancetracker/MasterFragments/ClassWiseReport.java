package com.nazgul.attendancetracker.MasterFragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nazgul.attendancetracker.AdminInfoCards.ReportClassListCard;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ClassWiseReport extends Fragment {

    String db_url;


    String course_name;
    String semester;
    String course_id;
    String tch_id;
    String subject;

    TextView report_topside;
    ListView classReport;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db_url = new DbUrl().getUrl();
        assert this.getArguments() != null;
        course_id = this.getArguments().getString("class_id");
        course_name = this.getArguments().getString("course_name");
        semester = this.getArguments().getString("sem");
        tch_id = this.getArguments().getString("tch_id");
        subject = this.getArguments().getString("subject");

        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_class_wise_report, container, false);
        report_topside = v.findViewById(R.id.report_topside_text);
        classReport = v.findViewById(R.id.class_report_list);

        new ReportTopView().execute(course_name, course_id, semester, tch_id);

        new ReportListView().execute(course_id, course_name, semester);

        return  v;
    }

    public class ReportTopView extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cName = params[0];
            String cId = params[1];
            String sem = params[2];
            String tId = params[3];

            String query = "?cname=" + cName + "&cid=" + cId + "&sem=" + sem + "&tid=" + tId;

            try {
                URL url = new URL(db_url + "/admin/report_topside.php" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = bufferedReader.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("db", row);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();
                return sb.toString();

            } catch(Exception e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String res) {
            String result = "";
            try{
                JSONArray jsonArray = new JSONArray(res);
                JSONObject jsonObject = null;
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.getJSONObject(i);
                    String tname = jsonObject.getString("tname");
                    String class_count = jsonObject.getString("class");
                    String std = jsonObject.getString("std");

                    int tot_class;
                    if(Integer.parseInt(std) != 0) {
                        tot_class = Integer.parseInt(class_count) / Integer.parseInt(std);
                    } else {
                        tot_class = 0;
                    }
                    result = "\nTeacher : " + tname
                            + "\nNumber of students : " + std
                            + "\nNumber of classes held : " + String.valueOf(tot_class) + "\n";
                }

            } catch(JSONException e){
                Log.d("err", e.getMessage());
                result = "Error retrieving information";
            }

            report_topside.setText(result);
        }
    }




    public class ReportListView extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String c_id = params[0];
            String c_name = params[1];
            String sem = params[2];

            String query = "?cname=" + c_name + "&cid=" + c_id + "&sem=" + sem;

            try {
                URL url = new URL(db_url + "/admin/report.php" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = bufferedReader.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("db", row);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();
                return sb.toString();

            } catch(Exception e) {
                Log.d("err", e.getMessage());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String res) {
            ArrayList<String> att_report = new ArrayList<>();
            try{
                JSONArray jsonArray = new JSONArray(res);
                JSONObject jsonObject = null;
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String present = jsonObject.getString("present");
                    String absent = jsonObject.getString("absent");
                    int tot_class = Integer.parseInt(present) + Integer.parseInt(absent);
                    double att_perc_p = 0.0;
                    if(tot_class != 0) {
                        att_perc_p = (Integer.parseInt(present) / (float)tot_class)  * 100;
                    }

                    String result = "\nStudent : " + name
                            + "\nAttendance % : " + new DecimalFormat("#.##").format(att_perc_p) + "\n";
                    att_report.add(result);
                    Log.d("res", result);
                }

            } catch(JSONException e){
                Log.d("err", e.getMessage());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.list_view_text,
                    att_report);

            classReport.setAdapter(arrayAdapter);
        }
    }
}
