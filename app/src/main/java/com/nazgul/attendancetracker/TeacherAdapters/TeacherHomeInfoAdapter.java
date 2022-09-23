package com.nazgul.attendancetracker.TeacherAdapters;

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
import com.nazgul.attendancetracker.TeacherFragments.TchClassInfo;
import com.nazgul.attendancetracker.TeacherInfoCards.HomeCard;

import java.util.ArrayList;

/**
 * Created by Sharat on 13-09-2022, Sep, 2022.
 * Time : 13:40
 * Project : AttendanceTracker
 */
public class TeacherHomeInfoAdapter extends RecyclerView.Adapter<TeacherHomeInfoAdapter.TeacherHomeInfoViewHolder>{

    Context context;
    ArrayList<HomeCard> homeCards;

    public static class TeacherHomeInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView tch_info;

        public TeacherHomeInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            tch_info = itemView.findViewById(R.id.teacher_home_info_txt);
        }
    }

    public TeacherHomeInfoAdapter(ArrayList<HomeCard> homeCardArrayList) {
        homeCards = homeCardArrayList;
    }

    @NonNull
    @Override
    public TeacherHomeInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.teacher_home_info, parent, false);
        TeacherHomeInfoViewHolder thivh = new TeacherHomeInfoViewHolder(v);
        return thivh;
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherHomeInfoViewHolder holder, int position) {

        HomeCard curr_item = homeCards.get(position);
        holder.tch_info.setText(curr_item.getInfo());
        holder.tch_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity aca = (AppCompatActivity) context;
                TchClassInfo tchClassInfo = new TchClassInfo();
                Bundle bundle = new Bundle();
                bundle.putString("id", curr_item.getCourse_id());
                tchClassInfo.setArguments(bundle);
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, tchClassInfo)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCards.size();
    }
}
