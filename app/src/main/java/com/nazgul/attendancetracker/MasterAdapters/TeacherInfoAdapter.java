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

import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.MasterFragments.TeacherInfo;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.AdminInfoCards.TeacherInfoCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sharat on 18-08-2022, Aug, 2022.
 * Time : 18:05
 * Project : AttendanceTracker
 */
public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.TeacherInfoViewHolder> {

    String db_url = new DbUrl().getUrl();

    private ArrayList<TeacherInfoCard> teacherInfoCardArrayList;
    String uid;
    Context context;

    public static class TeacherInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageButton imgDelete;
        public TextView teacherInfo;

        public TeacherInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.teacher_info_delete);
            teacherInfo = itemView.findViewById(R.id.teacher_info_txt);
        }
    }

    public TeacherInfoAdapter(ArrayList<TeacherInfoCard> teacherList) {
        teacherInfoCardArrayList = teacherList;
    }

    @NonNull
    @Override
    public TeacherInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_info, parent, false);
        TeacherInfoViewHolder tivh = new TeacherInfoViewHolder(v);
        return tivh;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherInfoViewHolder holder, int position) {
        TeacherInfoCard currItem = teacherInfoCardArrayList.get(position);

        //uid = currItem.getTeacher_id();
        //Log.d("uid", uid);

        holder.teacherInfo.setText(currItem.getTeacher_info());
        holder.teacherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = currItem.getTeacher_id();
                AppCompatActivity aca = (AppCompatActivity) context;
                TeacherInfo ti = new TeacherInfo();
                Bundle bundle = new Bundle();
                bundle.putString("teacher_id", uid);
                Log.d("uid", uid);
                ti.setArguments(bundle);
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ti)
                        .addToBackStack("tag")
                        .commit();
            }
        });
        holder.imgDelete.setImageResource(currItem.getImgRes());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = currItem.getTeacher_id();

                new AlertDialog.Builder(context)
                        .setTitle("Delete Teacher")
                        .setMessage("Are you sure you want to delete the teacher?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new DeleteTeacherDatabase().execute(db_url, uid);
                                new DeleteTeacherFirebase().execute(db_url, uid);
                                teacherInfoCardArrayList.remove(holder.getAdapterPosition());
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
        return teacherInfoCardArrayList.size();
    }


    public class DeleteTeacherDatabase extends AsyncTask<String, Void, String> {
        ProgressDialog pd = new ProgressDialog(context);


        @Override
        protected String doInBackground(String... params) {
            String uid = params[1];

            try {
                String query = "?id=" + uid;
                URL url = new URL(params[0] + "/admin/delete_teacher.php" + query);
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
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("res", s);
        }
    }

    public class DeleteTeacherFirebase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String uid = params[1];

            String query = "?uid=" + uid;

            try {
                URL url = new URL(db_url + "/firebase/delete_user.php" + query);
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

        @Override
        protected void onPostExecute(String s) {
            Log.d("res", s);
        }
    }
}
