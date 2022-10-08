package com.nazgul.attendancetracker.TeacherFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherAdapters.AttendanceInfoAdapter;
import com.nazgul.attendancetracker.TeacherInfoCards.StudentAttInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class Attendance extends Fragment {

    String db_url;

    String class_id;
    String course_name;
    String semester;
    TextView class_id_txt;
    TextView date_pick;
    String date;
    TextView time_pick;
    String time;
    Button att_submit;
    ProgressDialog pd;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<StudentAttInfo> studentAttInfos = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        // Inflate the layout for this fragment

        assert this.getArguments() != null;
        class_id = this.getArguments().getString("id");
        course_name = this.getArguments().getString("cid");
        semester = this.getArguments().getString("sid");


        View v = inflater.inflate(R.layout.fragment_attendance, container, false);

        class_id_txt = v.findViewById(R.id.class_info_att_text);
        date_pick = v.findViewById(R.id.att_date_picker);
        time_pick = v.findViewById(R.id.att_time_picker);
        att_submit = v.findViewById(R.id.att_submit);
        rv = v.findViewById(R.id.att_list_recycler);
        layoutManager = new LinearLayoutManager(getContext());

        pd = new ProgressDialog(getContext());

        date = (String) date_pick.getText();
        time = (String) time_pick.getText();

        class_id_txt.setText(class_id);

        new GetStudentList().execute(course_name, semester);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //Date Picker
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


        //Time Picker
        time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        timeSetListener,
                        hour,
                        minute,
                        true);
                timePickerDialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                time_pick.setText(time);
            }
        };


        att_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(date, "Select Date") || Objects.equals(time, "Select Time")) {
                    Toast.makeText(getContext(), "Please select Date and Time", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Att Info")
                            .setMessage("Do you want to submit?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pd.setTitle("Marking Attendance");
                                    pd.setMessage("Processing");
                                    pd.show();
                                    for(StudentAttInfo sai : studentAttInfos) {
                                        new PutAttendance().execute(class_id, sai.getStudent_id(), date, time, sai.getStd_status());
                                    }
                                    pd.dismiss();
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }
        });
    }


    public class GetStudentList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cid = params[0];
            String sid = params[1];

            String query = "?cid=" + cid + "&sid=" + sid;

            try {
                URL url = new URL(db_url + "/teacher/student_list.php" + query);
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
            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String student_name = jObj.getString("student_name");
                    String student_id = jObj.getString("student_id");
                    studentAttInfos.add(new StudentAttInfo(student_name, "Present", student_id));
                }

                adapter = new AttendanceInfoAdapter(studentAttInfos);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(), "Something went wrong...take a guess what...", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class PutAttendance extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cid = params[0];
            String sid = params[1];
            String date_php = params[2];
            String time_php = params[3];
            String status_php = params[4];

            String query = "?cid=" + cid
                    + "&sid=" + sid
                    + "&date=" + date_php
                    + "&time=" + time_php
                    + "&status=" + status_php;

            try {
                URL url = new URL(db_url + "/teacher/put_attendance.php" + query);
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
    }

}
