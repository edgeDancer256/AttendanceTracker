package com.nazgul.attendancetracker.TeacherFragments;

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

import com.nazgul.attendancetracker.AdminInfoCards.FileCard;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.MasterAdapters.FileInfoAdapter;
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


public class ViewAssignments extends Fragment {

    String db_url;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    int id_length;
    String id;

    ArrayList<AssignmentCard> assignmentCards = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        assert this.getArguments() != null;
        id = this.getArguments().getString("id");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_assignments, container, false);
        recyclerView = v.findViewById(R.id.assign_recycler);
        layoutManager = new LinearLayoutManager(getContext());

        id_length = id.length();

        new GetAssignments().execute(id);

        return v;
    }

    public class GetAssignments extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            try {
                //Try connection and store result
                URL url = new URL(db_url + "/firebase/get_assignments.php" + "?id=" + id);
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
            assignmentCards.clear();

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = null;
                int begin_index = 12 + id_length + 1;
                for(int i = 0; i < jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String fname = name.substring(begin_index).trim();
                    String token = jsonObject.getString("token").trim();

                    assignmentCards.add(new AssignmentCard(R.drawable.ic_download_foreground, fname, token, id));
                }
            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }

            adapter = new AssignmentInfoAdapter(assignmentCards);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
