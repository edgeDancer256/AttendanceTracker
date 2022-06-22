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

public class TeacherLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.teacherUsrName);
        password = (EditText) findViewById(R.id.teacherPassword);
        login = (Button) findViewById(R.id.teacherLogin);

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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {

            fStore.collection("Users")
                    .whereEqualTo("isTeacher", "0")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for(QueryDocumentSnapshot doc : task.getResult()) {
                                    if(doc.getId().equals(mAuth.getCurrentUser().getUid())) {
                                        startActivity(new Intent(TeacherLogin.this, TeacherMenu.class));
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
        String emailID = email.getText().toString();

        String pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(emailID, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).isEmailVerified()) {
                        fStore.collection("Users")
                                .whereEqualTo("isTeacher", "0")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()) {
                                            for(QueryDocumentSnapshot doc : task.getResult()) {
                                                if(Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(doc.getId())) {
                                                    startActivity(new Intent(TeacherLogin.this, TeacherMenu.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(TeacherLogin.this, "No Access", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                });
                    } else {
                        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
                        Toast.makeText(TeacherLogin.this, "Please verify Email", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(TeacherLogin.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}