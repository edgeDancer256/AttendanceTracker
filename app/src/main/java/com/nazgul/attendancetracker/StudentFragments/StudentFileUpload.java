package com.nazgul.attendancetracker.StudentFragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nazgul.attendancetracker.R;


public class StudentFileUpload extends Fragment {

    EditText fileName;
    Button fileUpload;
    Button selectFile;

    StorageReference storageReference;
    DatabaseReference databaseReference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_student_file_upload, container, false);
        Context context = container.getContext();

        fileName = v.findViewById(R.id.fileName);
        fileUpload = v.findViewById(R.id.fileUpload);
        selectFile = v.findViewById(R.id.selectFile);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("attendancetracker");

        fileUpload.setEnabled(false);

        selectFile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });
        return inflater.inflate(R.layout.fragment_student_file_upload, container, false);
    }

    public void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            fileUpload.setEnabled(true);
            fileName.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            fileUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFfb(data.getData());
                }
            });
        }
    }

    private void uploadPDFfb(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("The file is uploading...");
        progressDialog.show();
        StorageReference reference = storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                putFile putFile = new putFile(fileName.getText().toString(), "https://attendance-tracker-402d7-default-rtdb.asia-southeast1.firebasedatabase.app");
                databaseReference.child(databaseReference.push().getKey()).setValue(putFile);

                Toast.makeText(getContext(), "File Upload", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded.." +(int)progress + "%");

            }
        });
    }
}
