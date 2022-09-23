package com.nazgul.attendancetracker.MasterAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nazgul.attendancetracker.AdminInfoCards.QuestionPaperCard;
import com.nazgul.attendancetracker.R;

import java.util.ArrayList;

/**
 * Created by Sharat on 20-09-2022, Sep, 2022.
 * Time : 12:56
 * Project : AttendanceTracker
 */
public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.QPViewHolder> {

    private String fb_url = "https://firebasestorage.googleapis.com/v0/b/attendance-tracker-402d7.appspot.com/o/Question%20Papers%2F";


    Context context;
    ArrayList<QuestionPaperCard> qp_Cards;

    public static class QPViewHolder extends RecyclerView.ViewHolder {
        public TextView fName;
        public ImageButton download;

        public QPViewHolder(@NonNull View itemView) {
            super(itemView);
            fName = itemView.findViewById(R.id.qp_name);
            download = itemView.findViewById(R.id.qp_download);
        }
    }

    public QuestionPaperAdapter(ArrayList<QuestionPaperCard> questionCards) {
        qp_Cards = questionCards;
    }

    @NonNull
    @Override
    public QPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card, parent, false);
        QPViewHolder qpViewHolder = new QPViewHolder(v);
        return qpViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QPViewHolder holder, int position) {
        QuestionPaperCard fc = qp_Cards.get(position);

        holder.fName.setText(fc.getFile_name());
        holder.download.setImageResource(fc.getImg());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = fb_url
                        + fc.getClass_id()
                        + "%2F"
                        + fc.getFile_name()
                        + "?alt=media&token="
                        + fc.getToken();
                Intent in = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return qp_Cards.size();
    }
}
