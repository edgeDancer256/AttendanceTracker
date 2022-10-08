package com.nazgul.attendancetracker.StudentFragments;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class StudentReportDaily extends Fragment {

    String db_url;

    String c_name;
    String c_id;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView date_select;
    TextView class_name;
    ListView class_status;
    MaterialButton get_info;
    String date;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        assert this.getArguments() != null;
        c_id = this.getArguments().getString("id");
        c_name = this.getArguments().getString("class");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_report_daily, container, false);

        class_name = v.findViewById(R.id.daily_info_class_name);
        class_status = v.findViewById(R.id.daily_info_class_status);
        date_select = v.findViewById(R.id.daily_select_date);
        get_info = v.findViewById(R.id.daily_info_submit);
        date = (String) date_select.getText();

        class_name.setText(c_name);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        date_select.setOnClickListener(new View.OnClickListener() {
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
                date_select.setText(date);
            }
        };

        get_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(date, "Select Date")) {
                    Toast.makeText(getContext(), "Please select date", Toast.LENGTH_SHORT).show();
                } else {
                    new GetAttInfo().execute(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(),
                            c_id,
                            date);
                }
            }
        });
    }

    public class GetAttInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cid = params[1];
            String sid = params[0];
            String the_date = params[2];

            Log.d("stuff", cid + sid + the_date);

            String query = "?cid=" + cid
                    + "&sid=" + sid
                    + "&date=" + the_date;

            try {
                URL url = new URL(db_url + "/students/daily_report.php" + query);
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
        protected void onPostExecute(String s) {
            ArrayList<String> classStatus = new ArrayList<>();
            int j = 1;
            try{
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = null;
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    jsonObject = jsonArray.getJSONObject(i);
                    String status = jsonObject.getString("status");
                    String result = "\nClass : " + String.valueOf(j)
                            + "\nStatus : " + status;
                    j++;
                    classStatus.add(result);
                    Log.d("res", result);
                }

            } catch(JSONException e){
                Log.d("err", e.getMessage());
                classStatus.add("No class was held on this day...");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                    R.layout.list_view_text,
                    classStatus);

            class_status.setAdapter(arrayAdapter);

        }
    }
}
