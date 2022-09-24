package com.nazgul.attendancetracker.TeacherAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherInfoCards.StudentAttInfo;

import java.util.ArrayList;

/**
 * Created by Sharat on 23-09-2022, Sep, 2022.
 * Time : 19:38
 * Project : AttendanceTracker
 */
public class AttendanceInfoAdapter extends RecyclerView.Adapter<AttendanceInfoAdapter.AttendanceViewHolder> {
    Context context;
    ArrayList<StudentAttInfo> studentAttInfoArrayList;

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        public TextView std_name;
        public RadioGroup radioGroup;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            std_name = itemView.findViewById(R.id.att_student_name);
            radioGroup = itemView.findViewById(R.id.att_rad_group);
        }
    }

    public AttendanceInfoAdapter(ArrayList<StudentAttInfo> studentAttInfoArrayList) {
        this.studentAttInfoArrayList = studentAttInfoArrayList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.student_att_card, parent, false);
        AttendanceViewHolder avh = new AttendanceViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        StudentAttInfo curr_item = studentAttInfoArrayList.get(position);

        holder.std_name.setText(curr_item.getStd_name());
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.att_rad_p) {
                    curr_item.setStd_status("Present");
                } else if(i == R.id.att_rad_a) {
                    curr_item.setStd_status("Absent");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentAttInfoArrayList.size();
    }
}
