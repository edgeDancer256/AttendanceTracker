package com.nazgul.attendancetracker.MasterFragments;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nazgul.attendancetracker.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassList extends Fragment {


    private static final String url = "jdbc:mysql://database-1.cluster-cqqwgqkmnmfd.ap-south-1.rds.amazonaws.com/mainData";
    private static final String user = "admin";
    private static final String pass = "admin1234";

    TextView txtData;
    LinearLayout ll;
    Context context;
    List<ClassList.ResSet> resSetList = new ArrayList<ClassList.ResSet>();

    public class ResSet {
        private String cID;
        private String className;
        private String tID;

        private void set_cID(String cID) {
            this.cID = cID;
        }

        private void set_ClassName(String className) {
            this.className = className;
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

        private String get_tID() {
            return this.tID;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        assert this.getArguments() != null;
        String cName = this.getArguments().getString("cName");
        View v = inflater.inflate(R.layout.fragment_class_list, container, false);
        Context context = container.getContext();

        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        layparams.setMargins(10, 10, 10, 100);

        ll = v.findViewById(R.id.linlay1);

        new ListDisp().execute(url, pass, user, cName);
        return v;
    }

    public class ListDisp extends AsyncTask<String, Void, String> {

        //private static final String url = "jdbc:mysql://database-1.cluster-cqqwgqkmnmfd.ap-south-1.rds.amazonaws.com/mainData";
        //private static final String user = "admin";
        //private static final String pass = "admin1234";


        @Override
        protected String doInBackground(String... params) {
            String res = "";
            String cName = params[3];
            try {
                String result = "";
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select cID, className, tID from classes where cName = '" + cName + "'");


                while (rs.next()) {
                    result += rs.getString(1);

                    ClassList.ResSet resSet = new ClassList.ResSet();
                    resSet.set_cID(rs.getString(1));
                    resSet.set_ClassName(rs.getString(2));
                    resSet.set_tID(rs.getString(3));


                    resSetList.add(resSet);


                }
                Log.d("tag", result);
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String res) {
            LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layparams.setMargins(20, 20, 20, 20);


            for (ClassList.ResSet resSet : resSetList) {

                String result = "Class Name : " + resSet.get_cID() + "\n" +
                        "Class Name : " + resSet.get_ClassName() + "\n" +
                        "Class Name : " + resSet.get_tID() + "\n";

                Log.d("tag", result);
                CardView cv = new CardView(requireContext());
                cv.setCardElevation(10);
                cv.setLayoutParams(layparams);
                cv.setRadius(30);
                cv.setUseCompatPadding(true);


                txtData = new TextView(cv.getContext());
                txtData.setLayoutParams(layparams);

                txtData.setClickable(true);
                txtData.setText(result);
                txtData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txtData.setTextColor(Color.BLACK);


                cv.addView(txtData);
                //cv.addView(item_delete);

                ll.addView(cv);

            }
        }
    }
}