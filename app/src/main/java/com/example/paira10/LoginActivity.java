package com.example.paira10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.paira10.Models.Users;
import com.example.paira10.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog pgd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance () ;

        auth = FirebaseAuth.getInstance();
        pgd = new ProgressDialog(LoginActivity.this);
        pgd.setTitle("Logging account");
        pgd.setMessage("We are checking your information");

        //Google Authintacation
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder (GoogleSignInOptions. DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient (  this , gso);


        binding.btnGoogle.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        //SignUp
        binding.btnSingup.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();

            }
        });



        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etEmail.getText () .toString() .isEmpty ()) {
                    binding.etEmail.setError("Enter your email");
                    return;
                }
                if (binding.etPassword.getText () .toString() .isEmpty ()) {
                    binding.etPassword.setError("Enter your password");
                    return;
                }


                pgd.show();
                auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                pgd.dismiss();

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });





    }
    int RC_SIGN_IN =65;
    private void signIn() {
        Intent signinIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent,RC_SIGN_IN);

    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
// Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener (  this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(Task<AuthResult>task) {
                        if (task.isSuccessful()) {
                            Log. d("TAG", "signIfithcredential: success");
                            FirebaseUser user = auth. getCurrentUser () ;

                            Users users = new Users ( );
                            users.setUserId(user. getUid()) ;
                            users. setUserName (user. getDisplayName ( )) ;
                            users.setProfilePic(user. getPhotoUrl() . toString());
                            database. getReference() .child("Users") .child(user. getUid()) . setValue (users) ;


                            Intent intent = new Intent( LoginActivity.this , MainActivity.class);
                            startActivity (intent);
                            finish();

                            Toast.makeText(LoginActivity.this, "Sign in with Google", Toast.LENGTH_SHORT).show();
                            // updateUI (user);
                        }
                        else {
                            Log.w("TAG",  "signInWithCredential:failure", task.getException ());
                            // Snackbar.make (mBinding.mainLayout, "Authentication Failed.", Snackbar. LENGTH_SHORT) . shot
                            // updateUI(null);
                        }
                    }


                });}
}