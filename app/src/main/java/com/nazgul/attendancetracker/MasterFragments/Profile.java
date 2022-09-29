package com.nazgul.attendancetracker.MasterFragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.nazgul.attendancetracker.R;
import com.nazgul.attendancetracker.StudentFragments.StudentProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class Profile extends Fragment {

    String db_url;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView name;
    TextView email;
    TextView phone;
    Button change_password;
    Button logout;

    String admin_name;
    String admin_email;
    String admin_phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db_url = new DbUrl().getUrl();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = v.findViewById(R.id.admin_prof_name);
        email = v.findViewById(R.id.admin_prof_email);
        phone = v.findViewById(R.id.admin_prof_phone);

        change_password = v.findViewById(R.id.admin_prof_pass_change);
        logout = v.findViewById(R.id.logout_admin);

        new GetAdminProfile().execute();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Profile.this.getActivity(), MainActivity.class));
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

    public class GetAdminProfile extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(db_url + "/firebase/profile.php");
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
                    String a_name = jObj.getString("displayName");
                    String a_email = jObj.getString("email");
                    String a_phone = jObj.getString("phoneNumber");

                    admin_name = (String) name.getText();
                    name.setText(admin_name + a_name);

                    admin_email = (String) email.getText();
                    email.setText(admin_email + a_email);

                    admin_phone = (String) phone.getText();
                    phone.setText(admin_phone + a_phone);
                }

            } catch(Exception e) {
                Log.d("err_encode", e.getMessage());
                Toast.makeText(getContext(), "Something went wrong...take a guess what...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
