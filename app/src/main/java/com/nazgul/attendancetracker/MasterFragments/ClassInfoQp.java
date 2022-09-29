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
import android.widget.TextView;

import com.nazgul.attendancetracker.AdminInfoCards.FileCard;
import com.nazgul.attendancetracker.AdminInfoCards.QuestionPaperCard;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.MasterAdapters.QuestionPaperAdapter;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherAdapters.AssignmentInfoAdapter;
import com.nazgul.attendancetracker.TeacherInfoCards.AssignmentCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ClassInfoQp extends Fragment {

    String db_url;

    String class_id;
    String teacher_id;
    int tch_id_len;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<QuestionPaperCard> qpCards = new ArrayList<>();

    TextView txt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db_url = new DbUrl().getUrl();

        assert this.getArguments() != null;
        class_id = this.getArguments().getString("class_id");
        teacher_id = this.getArguments().getString("tch_id");
        tch_id_len = teacher_id.length();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_class_info_qp, container, false);

        recyclerView = v.findViewById(R.id.class_info_qp_recycler);
        txt = v.findViewById(R.id.class_info_qp_text);
        layoutManager = new LinearLayoutManager(getContext());

        new GetTchInfo().execute(teacher_id);
        new GetQP().execute(class_id);

        return v;
    }

    private class GetTchInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String tid = params[0];

            String query = "?tid=" + tid;

            try {
                URL url = new URL(db_url + "/admin/teacher_info.php" + query);
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

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String res) {
            try {
                JSONArray jsonArray = new JSONArray(res);
                JSONObject jsonObject = null;

                for(int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String teacherName = jsonObject.getString("teacherName");
                    String teacherDept = jsonObject.getString("teacherDept");

                    String result = "ID : " + teacher_id + "\n"
                            + "Name : " + teacherName + "\n"
                            + "Dept : " + teacherDept + "\n";

                    txt.setText(result);
                }
            } catch(Exception e) {
                txt.setText("No Teacher Info");
            }
        }
    }


    private class GetQP extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                //Try connection and store result
                URL url = new URL(db_url + "/firebase/get_qp.php" + "?id=" + id);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
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
            qpCards.clear();

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = null;
                int begin_index = 12 + tch_id_len + 1;
                for(int i = 0; i < jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String fname = name.substring(begin_index).trim();
                    String token = jsonObject.getString("token").trim();
                    Log.d("file_info", fname + " " + token + " " + class_id);

                    qpCards.add(new QuestionPaperCard(fname, token, class_id, R.drawable.ic_download_foreground));
                }
            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }

            adapter = new QuestionPaperAdapter(qpCards);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
