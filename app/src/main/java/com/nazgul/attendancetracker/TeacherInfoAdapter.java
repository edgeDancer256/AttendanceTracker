package com.nazgul.attendancetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sharat on 18-08-2022, Aug, 2022.
 * Time : 18:05
 * Project : AttendanceTracker
 */
public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.TeacherInfoViewHolder> {

    private ArrayList<TeacherInfoCard> teacherInfoCardArrayList;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_info, parent, false);
        TeacherInfoViewHolder tivh = new TeacherInfoViewHolder(v);
        return tivh;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherInfoViewHolder holder, int position) {
        TeacherInfoCard currItem = teacherInfoCardArrayList.get(position);

        holder.teacherInfo.setText(currItem.getTeacher_info());
        holder.imgDelete.setImageResource(currItem.getImgRes());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Delete " + currItem.getTeacher_id() + " ?", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherInfoCardArrayList.size();
    }
}
