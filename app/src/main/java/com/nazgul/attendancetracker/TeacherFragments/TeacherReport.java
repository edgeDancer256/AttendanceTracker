package com.nazgul.attendancetracker.TeacherFragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TeacherReport extends Fragment {

    String db_url;
    String tid;
    ListView report;

    PieChart pieChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db_url = new DbUrl().getUrl();

        assert this.getArguments() != null;
        tid = this.getArguments().getString("uid");
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_teacher_report, container, false);
        report = v.findViewById(R.id.teacher_report_list);
        pieChart = v.findViewById(R.id.tch_report_pie);

        new GetTeacherReport().execute(tid);

        return v;
    }

    public class GetTeacherReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String uid = strings[0];
            String query = "?tid=" + uid;

            try {
                URL url = new URL(db_url + "/teacher/full_report.php" + query);
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
                    String subject = jsonObject.getString("subject");
                    String count = jsonObject.getString("count");
                    String present = jsonObject.getString("present");
                    String absent = jsonObject.getString("absent");
                    int tot_class = (Integer.parseInt(present) + Integer.parseInt(absent)) / Integer.parseInt(count);
                    double att_perc_p = 0.0;
                    double att_perc_a = 0.0;
                    if(tot_class != 0) {
                        att_perc_p = (Integer.parseInt(present) / ((float)tot_class) / Integer.parseInt(count)) * 100;
                        att_perc_a = 100 - att_perc_p;

                        int pie_p = Integer.parseInt(String.valueOf(new DecimalFormat("#").format(att_perc_p)));
                        int pie_a = Integer.parseInt(String.valueOf(new DecimalFormat("#").format(att_perc_a)));

                        pieChart.addPieSlice(new PieModel("Absent",
                                pie_a,
                                Color.parseColor("#66BB6A")));

                        pieChart.addPieSlice(new PieModel("Present",
                                pie_p,
                                Color.parseColor("#FFA726")));
                    }


                    String pres = "\nPresent % : " + new DecimalFormat("#.##").format(att_perc_p);
                    String abs = "\nAbsent % : " + new DecimalFormat("#.##").format(att_perc_a);

                    Spannable spannable = new SpannableString(pres);
                    spannable.setSpan(Color.parseColor("#FFA726"), pres.length(),pres.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    Spannable spannable1 = new SpannableString(abs);
                    spannable1.setSpan(Color.parseColor("#66BB6A"), abs.length(),abs.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                    String result = "\nSubject : " + subject
                            + "\nNo. of students : " + count
                            + "\nTotal classes conducted : " + tot_class
                            + pres + abs + "\n";

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
