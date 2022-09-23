package com.nazgul.attendancetracker.MasterAdapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.AdminInfoCards.ClassInfoCard;
import com.nazgul.attendancetracker.MasterFragments.ClassInfoQp;
import com.nazgul.attendancetracker.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {

    //Credentials for server access
    //edgeDancer
    //private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    private static final String db_url = "http://192.168.1.11/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.39.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    Context context;
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
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_info, parent, false);
        ClassInfoViewHolder civh = new ClassInfoViewHolder(v);
        return civh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInfoViewHolder holder, int position) {
        ClassInfoCard currItem = classInfoCardArrayList.get(position);

        String disp_txt = "Subject : " + currItem.getSubject() + "\n"
                + "Semester : " + currItem.getSemester() + "\n";

        holder.txt1.setText(disp_txt);
        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("tch_id", currItem.getTeacher_id());
                bundle.putString("class_id", currItem.getClass_id());
                AppCompatActivity aca = (AppCompatActivity) context;
                ClassInfoQp classInfoQp = new ClassInfoQp();
                classInfoQp.setArguments(bundle);
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, classInfoQp)
                        .addToBackStack("tag")
                        .commit();
            }
        });

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
                                new DeleteEntry().execute(db_url, currItem.getClass_id());
                                classInfoCardArrayList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
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


    //Method to delete class
    private class DeleteEntry extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            pd.setTitle("Deleting Class...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String cID = params[1];

            try {
                //Try connection and store result
                String query = "?class_id=" + cID;
                URL url = new URL(params[0] + "/admin/delete_class.php" + query);
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

        @Override
        protected void onPostExecute(String res) {
            pd.dismiss();
            Log.d("res", res);
        }
    }
}
