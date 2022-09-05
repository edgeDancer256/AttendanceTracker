package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.nazgul.attendancetracker.InfoCards.FileCard;
import com.nazgul.attendancetracker.MasterAdapters.FileInfoAdapter;
import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class FileDisplay extends Fragment {

    //Credentials for server access
    //edgeDancer
    private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    //private static final String db_url = "http://192.168.1.19/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.57.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    ArrayList<FileCard> fileCards = new ArrayList<>();
    JSONArray fileList;


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

                    fileCards.add(new FileCard(R.drawable.ic_delete, fname, token));
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
