package com.nazgul.attendancetracker.MasterFragments;

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

import com.nazgul.attendancetracker.AdminInfoCards.ClassInfoCard;
import com.nazgul.attendancetracker.AdminInfoCards.ReportClassListCard;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.MasterAdapters.ClassInfoAdapter;
import com.nazgul.attendancetracker.MasterAdapters.ReportListAdapter;
import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ReportClassList extends Fragment {

    String db_url;

    RecyclerView recView;
    RecyclerView.Adapter recAdapter;
    RecyclerView.LayoutManager recLayout;

    String course_name;
    String class_id;
    String semester;
    String subject;
    String tch_id;
    ArrayList<ReportClassListCard> reportClassLists = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db_url = new DbUrl().getUrl();
        assert this.getArguments() != null;
        course_name = this.getArguments().getString("course");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report_class_list, container, false);

        recView = v.findViewById(R.id.report_class_list_recycler);
        recLayout = new LinearLayoutManager(getContext());

        new DispList().execute(course_name);

        return v;
    }

    public class DispList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String cName = strings[0];
            try {
                //Try connection and store result
                String query = "?course=" + cName;
                URL url = new URL(db_url + "/admin/class_list.php" + query);
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
        protected void onPostExecute(String res) {
            reportClassLists.clear();

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

                    reportClassLists.add(new ReportClassListCard(subject,
                            semester,
                            course_id,
                            course_name,
                            teacher_id));
                }

            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }
            recAdapter = new ReportListAdapter(reportClassLists);
            recView.setLayoutManager(recLayout);
            recView.setAdapter(recAdapter);
            recAdapter.notifyDataSetChanged();
        }
    }
}
