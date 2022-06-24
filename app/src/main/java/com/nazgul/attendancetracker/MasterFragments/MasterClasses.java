package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

    ImageButton item_delete;
    TextView txtData;
    LinearLayout ll;
    Context context;
    List<ResSet> resSetList = new ArrayList<ResSet>();
    List<Integer> idList = new ArrayList<Integer>();

    public class ResSet {
        private String cID;
        private String className;
        private String cName;
        private String tID;

        private void set_cID(String cID) {
            this.cID = cID;
        }

        private void set_ClassName(String className) {
            this.className = className;
        }

        private void set_cName(String cName) {
            this.cName = cName;
        }

        private void set_tID(String tID) {
            this.tID = tID;
        }

        private String get_cID() {
            return this.cID;
        }

        private String get_ClassName() {
            return this.className;
        }

        private String get_cName() {
            return this.cName;
        }

        private String get_tID() {
            return this.tID;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_classes, container, false);
        context = container.getContext();

        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        layparams.setMargins(10, 10, 10, 100);

        ll = v.findViewById(R.id.linlay1);

        if(ll.getChildCount() > 0) {
            ll.removeAllViews();
        } else {
            new MySQLConn().execute("");
        }
        //ll.removeAllViews();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }


    public class MySQLConn extends AsyncTask<String, Void, String> {

        //private static final String url = "jdbc:mysql://192.168.0.105:3306/mainData";
        //private static final String url = "jdbc:mysql://192.168.100.140:3306/mainData";
        private static final String url = "jdbc:mysql://database-1.cluster-cqqwgqkmnmfd.ap-south-1.rds.amazonaws.com/mainData";
        private static final String user = "admin";
        private static final String pass = "admin1234";


        @Override
        protected String doInBackground(String... params) {

            if(ll.getChildCount() > 0) {
                ll.removeAllViews();
            }
            //ll.removeAllViews();
            String res = "";
            try {
                String result = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select distinct cName from classes");


                while(rs.next()) {
                    result += rs.getString(1) ;

                    ResSet resSet = new ResSet();
                    resSet.set_cName(rs.getString(1));

                    resSetList.add(resSet);


                }
                Log.d("tag", result);
                res = result;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String res) {
            Log.d("tag", resSetList.toString());
            //ll.removeAllViews();



            LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layparams.setMargins(20, 20, 20, 20);


            for(ResSet resSet : resSetList) {


                String result = "Course Name : " + resSet.get_cName() + "\n";

                Log.d("tag", result);
                CardView cv = new CardView(context);
                int i = View.generateViewId();
                Log.d("tag", String.valueOf(i));
                idList.add(i);
                cv.setId(i);
                cv.setCardElevation(10);
                cv.setLayoutParams(layparams);
                cv.setRadius(30);
                cv.setUseCompatPadding(true);


                txtData = new TextView(cv.getContext());
                txtData.setLayoutParams(layparams);

                txtData.setClickable(true);
                txtData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        String cName = resSet.get_cName();
                        bundle.putString("cName", cName);
                        ClassList cl = new ClassList();
                        cl.setArguments(bundle);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cl).addToBackStack("tag").commit();


                        DispClicK(resSet.get_cName());
                    }
                });
                txtData.setText(result);
                txtData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txtData.setTextColor(Color.BLACK);


                cv.addView(txtData);
                //cv.addView(item_delete);

                ll.addView(cv);
            }
        }

        private void DispClicK(String text) {
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}