package com.tourkiev.chernobyltours.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tourkiev.chernobyltours.R;

import static com.tourkiev.chernobyltours.Constants.CHECK_IF_IS_AUTH_PASSED;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_PROFILE_FIRST_NAME;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_PROFILE_IMAGE_URL;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_PROFILE_USER_ID;
import static com.tourkiev.chernobyltours.Constants.FB_TAG;
import static com.tourkiev.chernobyltours.Constants.GOOGLE_TAG;
import static com.tourkiev.chernobyltours.Constants.PREFS_PROFILE_FIRST_NAME;
import static com.tourkiev.chernobyltours.Constants.PREFS_PROFILE_IMAGE_URL;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private CallbackManager mCallbackManager;
    private Intent loginIntent;
    private static final int RC_SIGN_IN = 999;
    private GoogleApiClient mGoogleClient;
    private static final int PERMISSION_REQUEST_CODE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // request permissions for users


        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        showPermissionAlertDialog(LoginActivity.this);
                    }
                }

            }
        }

        mAuth = FirebaseAuth.getInstance();

        loginIntent = new Intent(LoginActivity.this, MainActivity.class);

        // make link to soloeast site
        TextView creditsTextView = (findViewById(R.id.txt_credits));
        creditsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        creditsTextView.setTextColor(getResources().getColor(R.color.colorGray));

        // Initialize Facebook Login button

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton fbLoginButton = findViewById(R.id.facebook_login_button);
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(FB_TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(FB_TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(FB_TAG, "facebook:onError", error);
                // ...
            }
        });


        // Initialize google-login components

        SignInButton googleLoginButton = findViewById(R.id.google_login_button);

        // set proper text for google si button
        setGooglePlusButtonText(googleLoginButton, "Продолжить с Google");

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                //Log.w(GOOGLE_TAG, "signInWithCredential:failure", task.getException());
                Toast.makeText(LoginActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }

    private void handleFacebookAccessToken(AccessToken token) {
        // facebook login handler
        Log.d(FB_TAG, "handleFacebookAccessToken:" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FB_TAG, "signInWithCredential:success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            performLogin(loginIntent, currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(FB_TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // google login handler
        Log.d(GOOGLE_TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(GOOGLE_TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            performLogin(loginIntent, user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(GOOGLE_TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void performLogin(Intent intent, FirebaseUser user) {

        if (user != null) {
            String firstName = user.getDisplayName();
            String userId = user.getUid();
            String profileImageUrl = user.getPhotoUrl().toString();

            intent.putExtra(EXTRAS_PROFILE_USER_ID, userId);
            intent.putExtra(EXTRAS_PROFILE_FIRST_NAME, firstName);
            intent.putExtra(EXTRAS_PROFILE_IMAGE_URL, profileImageUrl);

            Log.d(FB_TAG, firstName);
            Log.d(FB_TAG, userId);
            Log.d(FB_TAG, profileImageUrl);

            // change value of our variable
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            // values to store in prefs
            editor.putBoolean(CHECK_IF_IS_AUTH_PASSED, true);
            // user profile data
            editor.putString(PREFS_PROFILE_FIRST_NAME, firstName);
            editor.putString(PREFS_PROFILE_IMAGE_URL, profileImageUrl);

            editor.apply();

            startActivity(intent);
            finish();
        }
    }

    /**
     * Call this method to change text of SignInButton.
     *
     * @param signInButton instance of Google plus SignInButton
     * @param buttonText   new text of the button
     */
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Search all the views inside SignInButton for TextView
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            // if the view is instance of TextView then change the text SignInButton
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(17);
                tv.setText(buttonText);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(GOOGLE_TAG, "Google sign in failed", e);
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        performLogin(loginIntent, currentUser);
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // permissions are granted, will go to oncreate....

            } else {
                showPermissionAlertDialog(LoginActivity.this);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showPermissionAlertDialog(Context context) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Permissions are not granted!")
                .setMessage("Chernobyl tours needs some additional permissions to be granted, so that it will work in proper way. " +
                        "Please grand access to location and storage of mobile phone permissions. Grand permissions?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       requestMultiplePermissions();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }
}