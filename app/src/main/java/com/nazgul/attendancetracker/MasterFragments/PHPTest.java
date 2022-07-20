package com.nazgul.attendancetracker.MasterFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nazgul.attendancetracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PHPTest extends Fragment {

    TextView tv;
    LinearLayout ll;
    Context context;

    //private static final String url = "http://192.168.0.105/att_tracker/view_classes.php";
    private static final String url = "http://192.168.0.140/att_tracker/view_classes.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.php_test, container, false);
        context = container.getContext();
        v.refreshDrawableState();

        ll = v.findViewById(R.id.php_ll);
        new PHPAsync().execute(url);


        return v;
    }

    public class PHPAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String row;

                while((row = br.readLine()) != null) {
                    sb.append(row).append("\n");
                    Log.d("tag", sb.toString());
                }
                br.close();
                httpURLConnection.disconnect();
                return sb.toString();


            } catch(Exception e) {
                Log.d("err", e.toString());
                return e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String res) {

            ll.removeAllViews();

            LinearLayout.LayoutParams layparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layparams.setMargins(20, 20, 20, 20);

            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObject = null;

                for(int i = 0; i < jArray.length(); i++) {
                    jObject = jArray.getJSONObject(i);
                    String course_id = jObject.getString("course_id");
                    String course_name = jObject.getString("course_name");
                    String semester = jObject.getString("semester");
                    String subject = jObject.getString("subject");
                    String teacher_id = jObject.getString("teacher_id");

                    String result = course_id + "\n"
                            + course_name + "\n"
                            + semester + "\n"
                            + subject + "\n"
                            + teacher_id +"\n";

                    tv = new TextView(getContext());
                    tv.setText(result);

                    ll.addView(tv);
                }

            } catch(Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), res, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
