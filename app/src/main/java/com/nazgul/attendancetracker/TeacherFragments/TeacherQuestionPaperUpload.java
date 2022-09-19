package com.nazgul.attendancetracker.TeacherFragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nazgul.attendancetracker.R;


public class TeacherQuestionPaperUpload extends Fragment {

    Button select;
    Button upload;
    TextView filename;
    Uri file;
    ProgressDialog pd;
    String class_id;
    String name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        class_id = this.getArguments().getString("id");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_teacher_question_paper_upload, container, false);
        select = v.findViewById(R.id.btn_select);
        upload = v.findViewById(R.id.btn_upload);
        filename = v.findViewById(R.id.FileName);
        pd = new ProgressDialog(getContext());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setType("application/pdf");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in,"Select file to upload"), 22);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file==null) {
                    Toast.makeText(getContext(),"No file selected...",Toast.LENGTH_SHORT).show();
                } else {
                    pd.setTitle("File uploading...");
                    pd.show();
                    UploadFile(file);
                }
            }
        });
    }

    private void UploadFile(Uri file) {
        Cursor c = requireContext().getContentResolver().query(file, null, null, null, null);
        c.moveToFirst();
        @SuppressLint("Range") String name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        StorageReference sref = FirebaseStorage.getInstance().getReference().child("Question Papers/"+class_id+"/"+name);
        sref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
                Log.d("upload_s", "Success");
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail!!!!", Toast.LENGTH_SHORT).show();
                Log.d("upload_f", "Fail");
                pd.dismiss();
            }
        });
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 22 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Cursor c = requireContext().getContentResolver().query(data.getData(), null, null, null, null);
            c.moveToFirst();
            name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            filename.setText(name);
            Log.d("filename", filename.getText().toString());
            file = data.getData();
        }
    }
}
