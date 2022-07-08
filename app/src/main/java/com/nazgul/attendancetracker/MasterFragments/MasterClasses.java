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

    //IS to be checked later!!!
    ImageButton item_delete;
    //Views and Layouts
    TextView txtData;
    LinearLayout ll;
    Context context;
    //List to contain results from DB query
    List<MasterClasses.ResSet> resSetList = new ArrayList<ResSet>();

    //Object Type for the result list
    public class ResSet {
        private String cName;

        private void set_cName(String cName) {
            this.cName = cName;
        }

        private String get_cName() {
            return this.cName;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_classes, container, false);
        context = container.getContext();
        v.refreshDrawableState();

        //Check Later!!!!!
        LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
        layparams.setMargins(10, 10, 10, 100);

        //Init Layout
        ll = v.findViewById(R.id.linlay1);

        if(ll.getChildCount() > 0) {
            ll.removeAllViews();
        } else {
            //Execute the Async Method
            new MySQLConn().execute("");
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ll.removeAllViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        ll.removeAllViews();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }


    //The Async Method to access DB and query records
    public class MySQLConn extends AsyncTask<String, Void, String> {

        //URL for the DB instance
        //private static final String url = "jdbc:mysql://192.168.0.105:3306/mainData";
        private static final String url = "jdbc:mysql://192.168.100.140:3306/mainData";

        //User Credentials
        private static final String user = "lucifer";
        private static final String pass = "lucifer";

        @Override
        protected String doInBackground(String... params) {

            String res = "";
            try {
                String result = "";

                //Trying the connection and storing result
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select distinct cName from classes");


                while(rs.next()) {
                    result += rs.getString(1);

                    ResSet resSet = new ResSet();
                    resSet.set_cName(rs.getString(1));

                    resSetList.add(resSet);


                }
                rs.close();
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

            //Update UI according to result set
            ll.removeAllViews();
            Log.d("tag", resSetList.toString());

            LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layparams.setMargins(20, 20, 20, 20);

            for(ResSet resSet : resSetList) {


                String result = "Course Name : " + resSet.get_cName() + "\n";

                Log.d("tag", result);
                CardView cv = new CardView(context);
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

                        //To update fragment according to Course Name
                        Bundle bundle = new Bundle();
                        String cName = resSet.get_cName();
                        bundle.putString("cName", cName);
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
                txtData.setText(result);
                txtData.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                txtData.setTextColor(Color.BLACK);

                //Adding the card view to the UI
                cv.addView(txtData);
                ll.addView(cv);
            }
            resSetList.clear();
        }
    }
}
