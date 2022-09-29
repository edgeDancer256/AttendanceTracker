package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.MasterAdapters.TeacherInfoAdapter;
import com.nazgul.attendancetracker.AdminInfoCards.TeacherInfoCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MasterTeachers extends Fragment {

    String db_url;

    RecyclerView recView;
    RecyclerView.Adapter recAdapter;
    RecyclerView.LayoutManager recLayout;
    Button add_teacher;

    String newTeacher;
    String teacher_ID;
    String teacher_Name;
    String teacher_Email;
    String teacher_Phone;
    String teacher_Dept;
    Context context;

    ArrayList<TeacherInfoCard> teacherInfoCardArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_teachers, container, false);
        context = v.getContext();

        recView = v.findViewById(R.id.add_teacher_recycler);
        recLayout = new LinearLayoutManager(getContext());
        add_teacher = v.findViewById(R.id.add_teacher);

        new TeacherList().execute(db_url + "/firebase/get_teachers.php");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        add_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddTeacher())
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }



    public class TeacherList extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            pd.setTitle("Loading Teachers...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
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
            pd.dismiss();
            teacherInfoCardArrayList.clear();

            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String teacher_name = jObj.getString("displayName");
                    String teacher_id = jObj.getString("uid");
                    String teacher_email = jObj.getString("email");
                    String teacher_ph_no = jObj.getString("phoneNumber");

                    String result = "Name : " + teacher_name + "\n"
                            + "ID : " + teacher_id + "\n"
                            + "Email : " + teacher_email + "\n"
                            + "Ph. No. : " + teacher_ph_no + "\n";

                    teacherInfoCardArrayList.add(new TeacherInfoCard(R.drawable.ic_delete, result, teacher_id));
                }
            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            recAdapter = new TeacherInfoAdapter(teacherInfoCardArrayList);
            recView.setLayoutManager(recLayout);
            recView.setAdapter(recAdapter);
            recAdapter.notifyDataSetChanged();
        }
    }

    public class AddTeacherFirebase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            teacher_ID = params[0];
            teacher_Name = params[1];
            teacher_Email = params[2];
            teacher_Phone = params[3];
            teacher_Dept = params[4];
            String query = "?uid=" + teacher_ID
                    + "&email=" + teacher_Email
                    + "&phoneNumber=" + teacher_Phone
                    + "&password=" + teacher_ID
                    + "&displayName=" + teacher_Name;

            try {
                URL url = new URL(db_url + "/firebase/create_user.php" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }

                newTeacher = "Name : " + teacher_Name + "\n"
                        + "ID : " + teacher_ID + "\n"
                        + "Email : " + teacher_Email + "\n"
                        + "Ph. No. : " + teacher_Phone + "\n";
                br.close();
                httpURLConnection.disconnect();


                return sb.toString();
            } catch(Exception e) {
                Log.d("err_conn", e.toString());
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            new AddTeacherDatabase().execute(teacher_ID, teacher_Name, teacher_Email, teacher_Phone, teacher_Dept);

            teacherInfoCardArrayList.add(new TeacherInfoCard(R.drawable.ic_delete, newTeacher, teacher_ID));
            Log.d("res", "User added successfully");
        }
    }

    public class AddTeacherDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String teacher_id = params[0];
            String teacher_name = params[1];
            String teacher_email = params[2];
            String teacher_phone = params[3];
            String teacher_dept = params[4];

            String query = "?id=" + teacher_id
                    + "&name=" + teacher_name
                    + "&email=" + teacher_email
                    + "&phone=" + teacher_phone
                    + "&dept=" + teacher_dept;

            try {
                URL url = new URL(db_url + "/admin/add_teachers.php" + query);
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
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("res", s);
        }
    }
}
