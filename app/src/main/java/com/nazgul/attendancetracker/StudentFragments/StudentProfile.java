package com.nazgul.attendancetracker.StudentFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nazgul.attendancetracker.DbUrl;
import com.nazgul.attendancetracker.MainActivity;
import com.nazgul.attendancetracker.MasterFragments.Profile;
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.StudentInfoCards.StudentHomeCard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class StudentProfile extends Fragment {

    String db_url;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView name;
    TextView email;
    TextView phone;
    TextView class_name;
    TextView sem;
    Button change_password;
    Button logout;

    String s_name;
    String s_email;
    String s_phone;
    String s_class_name;
    String s_sem;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student_profile, container, false);

        name = v.findViewById(R.id.std_prof_name);
        email = v.findViewById(R.id.std_prof_email);
        phone = v.findViewById(R.id.std_prof_phone);
        class_name = v.findViewById(R.id.std_prof_class);
        sem = v.findViewById(R.id.std_prof_sem);
        change_password = v.findViewById(R.id.std_prof_pass_change);
        logout = v.findViewById(R.id.logout_student);

        new GetProfile().execute(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(StudentProfile.this.getActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Password reset email sent.\n Please check your spam/trash folder as well.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public class GetProfile extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String sid = params[0];

            String query = "?sid=" + sid;

            try {
                URL url = new URL(db_url + "/students/profile.php" + query);
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
                Log.d("err_conn", e.toString());
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                JSONArray jArray = new JSONArray(res);
                JSONObject jObj = null;
                for(int i = 0; i < jArray.length(); i++) {
                    jObj = jArray.getJSONObject(i);
                    String student_name = jObj.getString("student_name");
                    String student_email = jObj.getString("student_email");
                    String student_phone = jObj.getString("student_phone");
                    String course = jObj.getString("student_course");
                    String semester = jObj.getString("semester");

                    s_name = (String) name.getText();
                    name.setText(s_name + " " + student_name);

                    s_email = (String) email.getText();
                    email.setText(s_email + " " + student_email);

                    s_phone = (String) phone.getText();
                    phone.setText(s_phone + " " + student_phone);

                    s_class_name = (String) class_name.getText();
                    class_name.setText(s_class_name + " " + course);

                    s_sem = (String) sem.getText();
                    sem.setText(s_sem + " " + semester);
                }

            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(), "Something went wrong...take a guess what...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
