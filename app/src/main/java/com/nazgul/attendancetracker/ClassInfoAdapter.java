package com.nazgul.attendancetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassInfoAdapter extends RecyclerView.Adapter<ClassInfoAdapter.ClassInfoViewHolder> {
    private ArrayList<ClassInfoCard> classInfoCardArrayList;

    public static class ClassInfoViewHolder extends RecyclerView.ViewHolder {

        public ImageButton imgButton;
        public TextView txt1;

        public ClassInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.img1);
            txt1 = itemView.findViewById(R.id.txt1);
        }
    }

    public ClassInfoAdapter(ArrayList<ClassInfoCard> classList) {
        classInfoCardArrayList = classList;
    }

    @NonNull
    @Override
    public ClassInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_info, parent, false);
        ClassInfoViewHolder civh = new ClassInfoViewHolder(v);
        return civh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassInfoViewHolder holder, int position) {
        ClassInfoCard currItem = classInfoCardArrayList.get(position);

        holder.txt1.setText(currItem.getText());
        holder.imgButton.setImageResource(currItem.getImgRes());
        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked " + currItem.getClass_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return classInfoCardArrayList.size();
    }
}
