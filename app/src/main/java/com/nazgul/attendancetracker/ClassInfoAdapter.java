package com.nazgul.attendancetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.MasterFragments.ClassList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {

    //Credentials for server access
    //private static final String url = "http://192.168.0.105/att_tracker/delete_class.php";
    private static final String url = "http://192.168.0.140/att_tracker/delete_class.php";

    private ArrayList<ClassInfoCard> classInfoCardArrayList;

    public static class ClassInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageButton imgButton;
        public TextView txt1;

        public ClassInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.img1);
            txt1 = itemView.findViewById(R.id.txt1);
        }
    }

    public ClassInfoAdapter(ArrayList<ClassInfoCard> classList) {
        classInfoCardArrayList = classList;
    }

    @NonNull
    @Override
    public ClassInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_info, parent, false);
        ClassInfoViewHolder civh = new ClassInfoViewHolder(v);
        return civh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInfoViewHolder holder, int position) {
        ClassInfoCard currItem = classInfoCardArrayList.get(position);

        holder.txt1.setText(currItem.getText());
        holder.imgButton.setImageResource(currItem.getImgRes());
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Class")
                        .setMessage("Are you sure you want to delete the class?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new DeleteEntry().execute(url, currItem.getClass_id());
                                Toast.makeText(view.getContext(), "Clicked " + currItem.getClass_id(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_delete)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classInfoCardArrayList.size();
    }

    private class DeleteEntry extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String cID = params[1];

            try {
                //Try connection and store result
                String query = "?class_id="+cID;
                URL url = new URL(params[0] + query);
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
                Log.d("err", e.toString());
                return e.getMessage();
            }
        }
    }
}
