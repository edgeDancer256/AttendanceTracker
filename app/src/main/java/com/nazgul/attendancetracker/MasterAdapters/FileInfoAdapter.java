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

import com.nazgul.attendancetracker.InfoCards.FileCard;
import com.nazgul.attendancetracker.R;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Sharat on 05-09-2022, Sep, 2022.
 * Time : 11:36
 * Project : AttendanceTracker
 */
public class FileInfoAdapter extends RecyclerView.Adapter<FileInfoAdapter.FileViewHolder> {

    private String fb_url = "https://firebasestorage.googleapis.com/v0/b/attendance-tracker-402d7.appspot.com/o/Assignments%2F";

    Context context;
    private ArrayList<FileCard> fileCardArrayList;

    public static class FileViewHolder extends RecyclerView.ViewHolder {
        public ImageButton down;
        public TextView fileName;


        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            down = itemView.findViewById(R.id.file_download);
            fileName = itemView.findViewById(R.id.file_name);
        }
    }

    public FileInfoAdapter(ArrayList<FileCard> downArrayList) {
        fileCardArrayList = downArrayList;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_card, parent, false);
        FileViewHolder fileViewHolder = new FileViewHolder(v);
        return fileViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        FileCard fc = fileCardArrayList.get(position);

        holder.fileName.setText(fc.getFile_name());
        holder.down.setImageResource(fc.getImg());
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = fb_url
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
        return fileCardArrayList.size();
    }
}
