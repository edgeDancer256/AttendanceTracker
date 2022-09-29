package com.nazgul.attendancetracker.StudentAdapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.StudentFragments.StudentHomeInfo;
import com.nazgul.attendancetracker.StudentInfoCards.StudentHomeCard;
import com.nazgul.attendancetracker.TeacherFragments.TchClassInfo;

import java.util.ArrayList;

/**
 * Created by Sharat on 19-09-2022, Sep, 2022.
 * Time : 13:36
 * Project : AttendanceTracker
 */
public class StudentHomeInfoAdapter extends RecyclerView.Adapter<StudentHomeInfoAdapter.StudentHomeInfoViewHolder> {

    ArrayList<StudentHomeCard> studentHomeCards;
    Context context;


    public static class StudentHomeInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView stdInfo;
        public StudentHomeInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            stdInfo = itemView.findViewById(R.id.student_home_info);
        }
    }

    public StudentHomeInfoAdapter(ArrayList<StudentHomeCard> studentHomeCardArrayList) {
        studentHomeCards = studentHomeCardArrayList;
    }

    @NonNull
    @Override
    public StudentHomeInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.student_home_card, parent, false);
        StudentHomeInfoViewHolder svh = new StudentHomeInfoViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHomeInfoViewHolder holder, int position) {
        StudentHomeCard curr_item = studentHomeCards.get(position);

        holder.stdInfo.setText(curr_item.getInfo());
        holder.stdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity aca = (AppCompatActivity) context;
                StudentHomeInfo studentHomeInfo = new StudentHomeInfo();
                Bundle bundle = new Bundle();
                bundle.putString("id", curr_item.getCourse_id());
                bundle.putString("class", curr_item.getSubject());
                studentHomeInfo.setArguments(bundle);
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, studentHomeInfo)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentHomeCards.size();
    }
}
