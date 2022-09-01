package com.nazgul.attendancetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Sharat on 27-08-2022, Aug, 2022.
 * Time : 11:49
 * Project : AttendanceTracker
 */
public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.StudentInfoViewHolder> {

    Context context;
    private ArrayList<StudentInfoCard> studentInfoCardArrayList;


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
        holder.imgDelete.setImageResource(currItem.getImgRes());

    }

    @Override
    public int getItemCount() {
        return studentInfoCardArrayList.size();
    }
}
