package com.nazgul.attendancetracker.TeacherAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.TeacherInfoCards.DailyReportCard;

import java.util.ArrayList;

/**
 * Created by Sharat on 29-09-2022, Sep, 2022.
 * Time : 13:24
 * Project : AttendanceTracker
 */
public class DailyReportAdapter extends RecyclerView.Adapter<DailyReportAdapter.DailyReportViewHolder> {

    Context context;
    ArrayList<DailyReportCard> dailyReportCards;

    public static class DailyReportViewHolder extends RecyclerView.ViewHolder{
        TextView info;
        public DailyReportViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tch_daily_report);
        }
    }

    public DailyReportAdapter(ArrayList<DailyReportCard> dailyReportCards) {
        this.dailyReportCards = dailyReportCards;
    }

    @NonNull
    @Override
    public DailyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.daily_report_card, parent, false);
        DailyReportViewHolder drvh = new DailyReportViewHolder(v);
        return drvh;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportViewHolder holder, int position) {
        DailyReportCard curr_item = dailyReportCards.get(position);
        holder.info.setText(curr_item.getInfo());
    }

    @Override
    public int getItemCount() {
        return dailyReportCards.size();
    }
}
