package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.BuildConfig;
import com.nazgul.attendancetracker.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MasterClasses extends Fragment {

    //Views and Layouts
    TextView txtData;
    LinearLayout ll;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_classes, container, false);
        context = container.getContext();
        v.refreshDrawableState();


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //MSc
        CardView cd = view.findViewById(R.id.course_card1);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send to MSC List

                Bundle bundle = new Bundle();
                bundle.putString("course", "MSC");

                ClassList cl = new ClassList();
                cl.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BSC
        cd = view.findViewById(R.id.course_card2);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send to MSC List

                Bundle bundle = new Bundle();
                bundle.putString("course", "BSC");

                ClassList cl = new ClassList();
                cl.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BCOM
        cd = view.findViewById(R.id.course_card3);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send to MSC List

                Bundle bundle = new Bundle();
                bundle.putString("course", "BCOM");

                ClassList cl = new ClassList();
                cl.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BCA
        cd = view.findViewById(R.id.course_card4);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send to MSC List

                Bundle bundle = new Bundle();
                bundle.putString("course", "BCA");

                ClassList cl = new ClassList();
                cl.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cl)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        //BA
        cd = view.findViewById(R.id.course_card5);
        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send to MSC List

                Bundle bundle = new Bundle();
                bundle.putString("course", "BA");

                ClassList cl = new ClassList();
                cl.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cl)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}
