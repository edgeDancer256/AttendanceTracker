package com.nazgul.attendancetracker.MasterAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.AdminInfoCards.ReportClassListCard;
import com.nazgul.attendancetracker.MasterFragments.ClassWiseReport;
import com.nazgul.attendancetracker.R;

import java.util.ArrayList;

/**
 * Created by Sharat on 29-09-2022, Sep, 2022.
 * Time : 16:15
 * Project : AttendanceTracker
 */
public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportListViewHolder> {

    Context context;
    ArrayList<ReportClassListCard> reportClassListCards;

    public static class ReportListViewHolder extends RecyclerView.ViewHolder {
        TextView info;
        public ReportListViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.report_list_text);
        }
    }

    public ReportListAdapter(ArrayList<ReportClassListCard> reportClassListCards) {
        this.reportClassListCards = reportClassListCards;
    }

    @NonNull
    @Override
    public ReportListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_card,
                parent,
                false);
        ReportListViewHolder reportListViewHolder = new ReportListViewHolder(v);
        return reportListViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportListViewHolder holder, int position) {
        ReportClassListCard curr_item = reportClassListCards.get(position);

        String info = "\nSubject : " + curr_item.getSubject()
                + "\nSemester : " + curr_item.getSemester() + "\n";
        holder.info.setText(info);
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassWiseReport classWiseReport = new ClassWiseReport();
                Bundle bundle = new Bundle();
                bundle.putString("class_id", curr_item.getClass_id());
                bundle.putString("course_name", curr_item.getCourse_name());
                bundle.putString("subject", curr_item.getSubject());
                bundle.putString("tch_id", curr_item.getTch_id());
                bundle.putString("sem", curr_item.getSemester());
                classWiseReport.setArguments(bundle);

                AppCompatActivity aca = (AppCompatActivity) context;
                aca.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, classWiseReport)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportClassListCards.size();
    }
}
