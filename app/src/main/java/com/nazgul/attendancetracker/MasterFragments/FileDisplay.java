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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.nazgul.attendancetracker.AdminInfoCards.FileCard;
import com.nazgul.attendancetracker.MasterAdapters.FileInfoAdapter;
import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FileDisplay extends Fragment {

    //Credentials for server access
    //edgeDancer
    private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    //private static final String db_url = "http://192.168.1.11/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.39.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<FileCard> fileCards = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_file_display, container, false);
        recyclerView = v.findViewById(R.id.file_recycler);
        layoutManager = new LinearLayoutManager(getContext());

        //new GetFiles().execute();


        new GetFiles().execute(db_url);

        // Inflate the layout for this fragment
        return v;
    }

    private class GetFiles extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                //Try connection and store result
                URL url = new URL(params[0] + "/firebase/storage_test.php");
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
            fileCards.clear();

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = null;
                for(int i = 0; i < jsonArray.length();i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String fname = name.substring(12).trim();
                    String token = jsonObject.getString("token").trim();

                    fileCards.add(new FileCard(R.drawable.ic_download_foreground, fname, token));
                }
            } catch(Exception e) {
                Log.d("err", e.getMessage());
            }

            adapter = new FileInfoAdapter(fileCards);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
