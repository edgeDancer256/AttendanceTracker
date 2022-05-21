package com.nazgul.attendancetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminMenu extends AppCompatActivity {

    private TextView t1;

    private static final String DATABASE_NAME = "trial";
    /*private static final String url = "jdbc:mysql://attendance-tracker-db.cqqwgqkmnmfd.ap-south-1.rds.amazonaws.com/" +
            DATABASE_NAME;*/
    private static final String url = "jdbc:mysql://34.93.219.7/" +
            DATABASE_NAME;
    private static final String username = "root", password = "root1234";
    private static final String TABLE_NAME = "t1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        t1 = findViewById(R.id.textView);

        try {
            showRecords();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
    }

    private void showRecords() throws SQLException {
        new Thread(() -> {

            StringBuilder rec = new StringBuilder();

            try {
                //Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url,username, password);
                Statement st = conn.createStatement();

                ResultSet rs = st.executeQuery("SELECT * FROM " + TABLE_NAME);
                while (rs.next()) {
                    rec.append("col1: ").append(rs.getString(1)).append(" col2: ").append(rs.getString(2)).append(" col3: ").append(rs.getString(3)).append("\n");
                }
                Log.e("rep", "conn success");
                conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("stuff", rec.toString());
                    t1.setText(rec.toString());
                }
            });
        }).start();
    }
}