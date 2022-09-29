package com.nazgul.attendancetracker.TeacherFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherAdapters.DailyReportAdapter;
import com.nazgul.attendancetracker.TeacherAdapters.TeacherHomeInfoAdapter;
import com.nazgul.attendancetracker.TeacherInfoCards.DailyReportCard;
import com.nazgul.attendancetracker.TeacherInfoCards.HomeCard;
import com.nazgul.attendancetracker.TeacherInfoCards.StudentAttInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class ViewDailyReport extends Fragment {

    String db_url;

    String class_id;
    String course_name;
    String semester;
    TextView class_name_txt;
    TextView date_pick;
    String date;
    Button att_get_info;
    DatePickerDialog.OnDateSetListener dateSetListener;
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<DailyReportCard> dailyReportCards = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db_url = new DbUrl().getUrl();

        // Inflate the layout for this fragment
        assert this.getArguments() != null;
        class_id = this.getArguments().getString("id");
        course_name = this.getArguments().getString("cid");
        semester = this.getArguments().getString("sid");

        View v = inflater.inflate(R.layout.fragment_view_daily_report, container, false);
        class_name_txt = v.findViewById(R.id.tch_att_rep_class_text);
        date_pick = v.findViewById(R.id.att_rep_date_picker);
        att_get_info = v.findViewById(R.id.tch_att_rep_get);
        rv = v.findViewById(R.id.att_rep_recycler);
        layoutManager = new LinearLayoutManager(getContext());

        class_name_txt.setText(class_id);
        date = (String) date_pick.getText();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener,
                        year,
                        month,
                        day);
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                date = day + "/" + month +"/" + year;
                date_pick.setText(date);
            }
        };


        att_get_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(date, "Select Date")) {
                    Toast.makeText(getContext(), "Please select Date", Toast.LENGTH_SHORT).show();
                } else {
                    new GetDailyReport().execute(course_name, class_id, semester, date);
                }
            }
        });
    }

    public class GetDailyReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String c_name = params[0];
            String c_id = params[1];
            String sem = params[2];
            String date = params[3];

            String query = "?cid=" + c_name
                    + "&cl_id=" + c_id
                    + "&sid=" + sem
                    + "&date=" + date;

            try {
                URL url = new URL(db_url + "/teacher/daily_report.php" + query);
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
            dailyReportCards.clear();
            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String std_name = jObj.getString("s_name");
                    String time = jObj.getString("time");
                    String status = jObj.getString("status");

                    String result = "Student : " + std_name + "\n"
                            + "Time : " + time + "\n"
                            + "Status : " + status + "\n";

                    dailyReportCards.add(new DailyReportCard(result));
                }
            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                dailyReportCards.add(new DailyReportCard("No class was held on this date"));
            }

            adapter = new DailyReportAdapter(dailyReportCards);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
