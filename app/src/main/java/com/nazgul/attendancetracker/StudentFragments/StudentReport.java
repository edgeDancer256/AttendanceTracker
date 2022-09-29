package com.nazgul.attendancetracker.StudentFragments;

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

public class StudentReport extends Fragment {

    String db_url;

    ListView report;

    String sid;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        assert this.getArguments() != null;
        sid = this.getArguments().getString("uid");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_report, container, false);
        report = v.findViewById(R.id.student_report_list);
        //recyclerView = v.findViewById(R.id.student_report_main_recycler);

        new GetFullReport().execute(sid);

        return v;
    }


    public class GetFullReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String s_id = params[0];
            String query = "?sid=" + s_id;

            try {
                URL url = new URL(db_url + "/students/full_report.php" + query);
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
                    String class_name = jsonObject.getString("class_id");
                    String present = jsonObject.getString("Present");
                    String absent = jsonObject.getString("Absent");
                    int tot_class = Integer.parseInt(present) + Integer.parseInt(absent);
                    double att_perc = 0.0;
                    if(tot_class != 0) {
                        att_perc = (Integer.parseInt(present) / (float)tot_class) * 100;
                    }

                    String result = "\nClass : " + class_name
                            + "\nTotal Classes : " + tot_class
                            + "\nPresent : " + present
                            + "\nAbsent :" + absent
                            + "\nAttendance Percentage : " + new DecimalFormat("#.##").format(att_perc) + "\n";
                    att_report.add(result);
                    Log.d("res", result);
                }

            } catch(JSONException e){
                Log.d("err", e.getMessage());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.list_view_text,
                    att_report);

            report.setAdapter(arrayAdapter);
        }
    }
}
