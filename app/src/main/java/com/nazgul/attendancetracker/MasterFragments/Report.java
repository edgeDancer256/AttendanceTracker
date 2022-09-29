package com.nazgul.attendancetracker.MasterFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.nazgul.attendancetracker.R;


public class Report extends Fragment {

    MaterialCardView msc;
    MaterialCardView bsc;
    MaterialCardView bcom;
    MaterialCardView bca;
    MaterialCardView ba;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);

        msc = v.findViewById(R.id.report_card_msc);
        bsc = v.findViewById(R.id.report_card_bsc);
        bcom = v.findViewById(R.id.report_card_bcom);
        bca = v.findViewById(R.id.report_card_bca);
        ba = v.findViewById(R.id.report_card_ba);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        msc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportClassList reportClassList = new ReportClassList();
                Bundle bundle = new Bundle();
                bundle.putString("course", "MSC");
                reportClassList.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, reportClassList)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        bsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportClassList reportClassList = new ReportClassList();
                Bundle bundle = new Bundle();
                bundle.putString("course", "BSC");
                reportClassList.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, reportClassList)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        bcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportClassList reportClassList = new ReportClassList();
                Bundle bundle = new Bundle();
                bundle.putString("course", "BCOM");
                reportClassList.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, reportClassList)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportClassList reportClassList = new ReportClassList();
                Bundle bundle = new Bundle();
                bundle.putString("course", "BCA");
                reportClassList.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, reportClassList)
                        .addToBackStack("tag")
                        .commit();
            }
        });

        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportClassList reportClassList = new ReportClassList();
                Bundle bundle = new Bundle();
                bundle.putString("course", "BA");
                reportClassList.setArguments(bundle);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, reportClassList)
                        .addToBackStack("tag")
                        .commit();
            }
        });
    }
}
