package com.nazgul.attendancetracker.MasterFragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nazgul.attendancetracker.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MasterTeachers extends Fragment {

    private static final String url = "jdbc:mysql://192.168.0.105:3306/trial";
    private static final String user = "lucifer";
    private static final String pass = "lucifer";
    TextView txtData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_master_teachers, container, false);
        txtData = v.findViewById(R.id.txtdata);
        MySQLConn conn = new MySQLConn();
        conn.execute("");
        return v;
    }


    private class MySQLConn extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                String result = "Database Connected Successfully!!\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from t1");

                while(rs.next()) {
                    result += rs.getString(1).toString() + " " + rs.getString(2).toString() + "\n";
                }
                res = result;
                Log.d("tag", res);
            } catch(Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            txtData.setText(result);
        }
    }
}