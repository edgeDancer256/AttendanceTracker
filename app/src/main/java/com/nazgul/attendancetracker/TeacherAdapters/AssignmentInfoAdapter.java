package com.nazgul.attendancetracker.TeacherAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherInfoCards.AssignmentCard;

import java.util.ArrayList;

/**
 * Created by Sharat on 13-09-2022, Sep, 2022.
 * Time : 17:12
 * Project : AttendanceTracker
 */
public class AssignmentInfoAdapter extends RecyclerView.Adapter<AssignmentInfoAdapter.AssignmentViewHolder>{

    private String fb_url = "https://firebasestorage.googleapis.com/v0/b/attendance-tracker-402d7.appspot.com/o/Assignments%2F";

    Context context;
    private ArrayList<AssignmentCard> assignmentCardArrayList;

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

        TextView file_info;
        ImageButton file_down;
        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            file_info = itemView.findViewById(R.id.ass_name);
            file_down = itemView.findViewById(R.id.ass_download);
        }
    }

    public AssignmentInfoAdapter(ArrayList<AssignmentCard> assignmentCardArrayList) {
        this.assignmentCardArrayList = assignmentCardArrayList;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.assignment_card, parent, false);
        AssignmentViewHolder avh = new AssignmentViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentCard curr_item = assignmentCardArrayList.get(position);

        holder.file_info.setText(curr_item.getFile_name());
        holder.file_down.setImageResource(curr_item.getImg());
        holder.file_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = fb_url
                        + curr_item.getId()
                        + "%2F"
                        + curr_item.getFile_name()
                        + "?alt=media&token="
                        + curr_item.getToken();
                Intent in = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignmentCardArrayList.size();
    }
}
