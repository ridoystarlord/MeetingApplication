package com.ridoy.asunkothaboli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ridoy.asunkothaboli.databinding.FragmentProfileBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    

    public ProfileFragment() {
        // Required empty public constructor
    }

    FragmentProfileBinding fragmentProfileBinding;
    ProgressDialog dialog,progressDialog;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding=FragmentProfileBinding.inflate(inflater,container,false);
        dialog=new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Image Updating...");

        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("on").equals("1")){
                    Glide.with(getContext()).load(documentSnapshot.get("profileimage")).placeholder(R.drawable.ic_upload).into(fragmentProfileBinding.profileImage);
                }else {
                    Glide.with(getContext()).load(documentSnapshot.get("profileimage")).placeholder(R.drawable.ic_man).into(fragmentProfileBinding.profileImage);
                }
                fragmentProfileBinding.usernameTV.setText(documentSnapshot.getString("name"));
                fragmentProfileBinding.useremailTV.setText(documentSnapshot.getString("email"));
                dialog.dismiss();
            }
        });

        fragmentProfileBinding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,12);
            }
        });
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null){
            if (data.getData()!=null){
                Uri selectedImagepath=data.getData();
                progressDialog.show();
                StorageReference reference= storage.getReference().child("ProfileImages").child(FirebaseAuth.getInstance().getUid());

                reference.putFile(selectedImagepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String filepath=uri.toString();

                                    HashMap<String,Object> map=new HashMap<>();
                                    map.put("profileimage",filepath);
                                    map.put("on","2");

                                    firebaseFirestore.collection("Users")
                                            .document(FirebaseAuth.getInstance().getUid()).update(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Glide.with(getContext()).load(filepath).into(fragmentProfileBinding.profileImage);
                                            progressDialog.dismiss();
                                        }
                                    });

                                }
                            });
                        }

                    }
                });

            }
        }
    }
}