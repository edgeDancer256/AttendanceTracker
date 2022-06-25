package com.nazgul.attendancetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class StudentLogin extends AppCompatActivity {

    //TextViews of Username and Password
    private EditText email;
    private EditText password;
    //Login button
    private Button login;
    //FirebaseAuth instance
    private FirebaseAuth mAuth;
    //Firestore instance
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        //Init FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Init Views
        email = (EditText) findViewById(R.id.studentUsrName);
        password = (EditText) findViewById(R.id.studentPassword);
        login = (Button) findViewById(R.id.studentLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Check if user is already logged in. If yes, don't show login screen again
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {

            fStore.collection("Users")
                    .whereEqualTo("isStudent", "0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for(QueryDocumentSnapshot doc : task.getResult()) {
                                    if(doc.getId().equals(mAuth.getCurrentUser().getUid())) {
                                        startActivity(new Intent(StudentLogin.this, StudentMenu.class));
                                        finish();
                                    }
                                    Log.d("TAG", doc.getId() + doc.getData().toString());
                                }
                            }
                        }
                    });
        }
    }

    private void signIn() {
        //Retrieve Values of email and password
        String emailID = email.getText().toString();
        String pass = password.getText().toString();

        //Call Sign In method
        mAuth.signInWithEmailAndPassword(emailID, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Check Email Verification
                    if(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).isEmailVerified()) {
                        //If email verified, check access level
                        fStore.collection("Users")
                                .whereEqualTo("isStudent", "0")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()) {
                                            //If access OK, Send to Menu
                                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                                if(Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(doc.getId())) {
                                                    startActivity(new Intent(StudentLogin.this, StudentMenu.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(StudentLogin.this, "No Access", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                });
                    } else {
                        //Send Verification Email
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                        Toast.makeText(StudentLogin.this, "Please verify Email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(StudentLogin.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}