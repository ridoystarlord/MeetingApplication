package com.ridoy.asunkothaboli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ridoy.asunkothaboli.databinding.ActivityDashboardBinding;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        activityDashboardBinding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                       transaction.replace(R.id.container,new HomeFragment());
                       break;
                    case 1:
                        transaction.replace(R.id.container,new ProfileFragment());
                        break;
                    case 2:
                        FirebaseAuth.getInstance().signOut();
                        finishAffinity();
                        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                        break;
                }
                transaction.commit();
                return false;
            }
        });


    }
}