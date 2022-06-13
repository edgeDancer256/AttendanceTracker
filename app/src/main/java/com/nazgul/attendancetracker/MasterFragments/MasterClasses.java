package com.nazgul.attendancetracker.MasterFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MasterClasses extends Fragment {

    private static final String url = "jdbc:mysql://192.168.0.105:3306/mainData";
    //private static final String url = "jdbc:mysql://192.168.100.140:3306/mainData";
    private static final String user = "lucifer";
    private static final String pass = "lucifer";
    TextView txtData;
    LinearLayout ll;
    Context context;
    List<ResSet> resSetList = new ArrayList<ResSet>();

    public class ResSet {
        private String cID;
        private String className;
        private String cName;

        private void setcID(String cID) {
            this.cID = cID;
        }

        private void setClassName(String className) {
            this.className = className;
        }

        private void setcName(String cName) {
            this.cName = cName;
        }

        private String getcID() {
            return this.cID;
        }

        private String getClassName() {
            return this.className;
        }

        private String getcName() {
            return this.cName;
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

        new MySQLConn().execute("");





        return v;
    }


    public class MySQLConn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = "";
            try {
                String result = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from classes order by cName");


                while(rs.next()) {
                    result += rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + "\n";

                    ResSet resSet = new ResSet();
                    resSet.setcID(rs.getString(1));
                    resSet.setClassName(rs.getString(2));
                    resSet.setcName(rs.getString(3));

                    resSetList.add(resSet);


                }
                Log.d("tag", result);
                res = result;
            } catch(Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            Log.d("tag", resSetList.toString());

            LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layparams.setMargins(20, 20, 20, 20);

            for(ResSet resSet : resSetList) {

                String result = resSet.getcID() + " " + resSet.getClassName() + " " + resSet.getcName();
                Log.d("tag", result);
                CardView cv = new CardView(context);
                cv.setCardElevation(30);
                cv.setClickable(true);
                cv.setLayoutParams(layparams);
                cv.setRadius(30);
                cv.setUseCompatPadding(true);

                txtData = new TextView(cv.getContext());
                txtData.setLayoutParams(layparams);
                txtData.setText(result);
                txtData.setGravity(Gravity.CENTER_VERTICAL);
                txtData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txtData.setTextColor(Color.BLACK);

                cv.addView(txtData);

                ll.addView(cv);
            }
        }
    }
}