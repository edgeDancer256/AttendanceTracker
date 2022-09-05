package com.nazgul.attendancetracker.MasterAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.MasterFragments.StudentInfo;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.InfoCards.StudentInfoCard;

import java.util.ArrayList;

/**
 * Created by Sharat on 27-08-2022, Aug, 2022.
 * Time : 11:49
 * Project : AttendanceTracker
 */
public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.StudentInfoViewHolder> {

    //Credentials for server access
    //edgeDancer
    private static final String db_url = "http://192.168.0.105/att_tracker";
    //l1ght
    //private static final String db_url = "http://192.168.1.19/att_tracker";
    //l1ght hotspot
    //private static final String db_url = "http://192.168.57.104/att_tracker";
    //College
    //private static final String db_url = "http://192.168.0.140/att_tracker";

    Context context;
    private ArrayList<StudentInfoCard> studentInfoCardArrayList;
    String uid;
    String course;
    String semester;


    public static class StudentInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageButton imgDelete;
        public TextView studentInfo;

        public StudentInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.student_info_delete);
            studentInfo = itemView.findViewById(R.id.student_info_txt);
        }
    }

    public StudentInfoAdapter(ArrayList<StudentInfoCard> studentInfoCardArrayList) {
        this.studentInfoCardArrayList = studentInfoCardArrayList;
    }

    @NonNull
    @Override
    public StudentInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.student_info, parent, false);
        StudentInfoViewHolder stvh = new StudentInfoViewHolder(v);
        return stvh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentInfoViewHolder holder, int position) {
        StudentInfoCard currItem = studentInfoCardArrayList.get(position);

        holder.studentInfo.setText(currItem.getStudent_info());
        holder.studentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = currItem.getStudent_id();
                course = currItem.getStd_course();
                semester = currItem.getStd_semester();
                AppCompatActivity aca = (AppCompatActivity) context;
                StudentInfo si = new StudentInfo();
                Bundle bundle = new Bundle();
                bundle.putString("id", uid);
                bundle.putString("course", course);
                bundle.putString("sem", semester);
                si.setArguments(bundle);
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, si)
                        .addToBackStack("tag")
                        .commit();
            }
        });
        holder.imgDelete.setImageResource(currItem.getImgRes());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uid = currItem.getStudent_id();

                new AlertDialog.Builder(context)
                        .setTitle("Delete Student")
                        .setMessage("Are you sure you want to delete the student?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new StudentInfoAdapter.DeleteStudentDatabase().execute( uid);
                                new StudentInfoAdapter.DeleteStudentFirebase().execute(uid);
                                studentInfoCardArrayList.remove(holder.getAdapterPosition());
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
        return studentInfoCardArrayList.size();
    }

    public class DeleteStudentDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String uid = params[0];


            return null;
        }
    }


    public class DeleteStudentFirebase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
