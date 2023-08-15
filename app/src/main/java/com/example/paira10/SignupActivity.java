package com.example.paira10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.paira10.Models.Users;
import com.example.paira10.databinding.ActivityLoginBinding;
import com.example.paira10.databinding.ActivitySignupBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
ActivitySignupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog pgd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        auth = FirebaseAuth. getInstance () ;
        database = FirebaseDatabase. getInstance () ;




        binding.btnLogIn.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


        pgd= new ProgressDialog(SignupActivity.this);
        pgd.setTitle("Creating account");
        pgd.setMessage("We are creating a account");

        binding.btnSignup.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                if (binding.etUserName.getText () .toString() .isEmpty ()) {
                    binding.etEmail.setError("Enter your email");
                    return;
                }
                if (binding.etEmail.getText () .toString() .isEmpty ()) {
                    binding.etEmail.setError("Enter your email");
                    return;
                }
                if (binding.etPassword.getText () .toString() .isEmpty ()) {
                    binding.etPassword.setError("Enter your password");
                    return;
                }
                if (binding.etPassword2.getText () .toString() .isEmpty ()) {
                    binding.etPassword2.setError("Enter your password again");
                    return;
                }
//               else if (binding.etPassword2.getText ().toString() !=binding.etPassword.getText ().toString()) {
//                    binding.etPassword2.setError("Enter your password again");
//                    Toast.makeText(SignUpActivity.this, "Password doesnot match", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                pgd.show();
                auth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                pgd.dismiss();
                                if (task.isSuccessful()) {
                                    Users user = new Users(binding.etUserName.getText().toString(),binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
                                    String id = task. getResult (). getUser (). getUid();
                                    database.getReference() .child("Users") .child(id).setValue(user);


                                    Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });





    }

}