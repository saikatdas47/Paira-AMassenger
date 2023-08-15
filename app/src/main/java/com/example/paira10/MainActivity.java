package com.example.paira10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.paira10.Adapters.FragmentsAdapter;
import com.example.paira10.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources () . getColor (R.color.primary)));

        binding.viewPager. setAdapter (new FragmentsAdapter(getSupportFragmentManager ()));
        binding. tablayout.setupWithViewPager (binding.viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout){

            new AlertDialog. Builder ( this)
                    .setMessage ("Are you sure you want to exit?")
                    .setCancelable(false) .setPositiveButton( "Yes", new DialogInterface. OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance() .signOut ( );
                            Intent intent = new Intent ( MainActivity. this , LoginActivity.class);
                            startActivity (intent);
                            finish();                               }
                    })
                    .setNegativeButton("No",null)
                    .show();

        }
        else if (item.getItemId()==R.id.settings){
            Intent intent = new Intent ( MainActivity. this , SettingsActivity.class);
            startActivity (intent);
        }
        else if (item.getItemId()==R.id.ai){
            Intent intent = new Intent ( MainActivity. this , AIActivity.class);
            startActivity (intent);
        }
        else if (item.getItemId()==R.id.tic){
            Intent intent = new Intent ( MainActivity. this , TikGame.class);
            startActivity (intent);
        }

        else if (item.getItemId()==R.id.groupChat) {
            Intent intent = new Intent ( MainActivity. this , GroupChatActivity.class);
            startActivity (intent);
        }

        return true;
    }

}