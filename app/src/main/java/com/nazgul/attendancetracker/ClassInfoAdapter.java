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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {

    //private static final String url = "jdbc:mysql://192.168.0.105:3306/mainData";
    private static final String url = "jdbc:mysql://192.168.100.140:3306/mainData";
    private static final String user = "lucifer";
    private static final String pass = "lucifer";

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
                                new DeleteEntry().execute(url, user, pass, currItem.getClass_id());
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
            String cID = params[3];

            try {
                String result = "";

                //Try connection and store result
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                st.executeUpdate("delete from classes where cID = '" + cID + "'");
            } catch(Exception e) {
                Log.d("tag", e.toString());
            }
            return null;
        }
    }
}
