package com.example.nneeranjun.qrcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.zxing.qrcode.encoder.QRCode;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class UserCreation extends AppCompatActivity {
    EditText nameText;
    EditText phoneText;
    String twitterUsername;
    String facebookID;
    String snapchatUsername;
    String instagramUsername;
    TwitterLoginButton twitterBtn;
    ImageView profilePicture;
    CallbackManager callbackManager;
    FirebaseFirestore db;
    LoginButton facebookBtn;
    String emailAddress;
    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        setContentView(R.layout.activity_user_creation);
        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phoneNumber);
        profilePicture = findViewById(R.id.profile_image);
        facebookBtn = findViewById(R.id.facebook_btn);
        twitterUsername = "";
        facebookID = "";
        snapchatUsername = "";
        instagramUsername = "";
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser!=null){
            emailAddress = currentUser.getEmail();
        }

        db = FirebaseFirestore.getInstance();




        /** TWITTER BUTTON STUFF */

        twitterBtn =  findViewById(R.id.twitter_btn);
        twitterBtn.setVisibility(View.VISIBLE);
        twitterBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                twitterUsername=result.data.getUserName();

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        /** END TWITTER BUTTON STUFF */


        /**FACEBOOK BUTTON STUFF*/
        facebookBtn.setReadPermissions("email", "public_profile", "user_friends");
        callbackManager = CallbackManager.Factory.create();
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        facebookID = loginResult.getAccessToken().getUserId();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        /** END FACEBOOK BUTTON STUFF */


        /** INSTAGRAM BUTTON STUFF */

        /** END INSTAGRAM BUTTON STUFF */

    }

    public String getCurrentLocation() {
        return "Berkeley";
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterBtn.onActivityResult(requestCode, resultCode, data); //For Twitter
        callbackManager.onActivityResult(requestCode, resultCode, data); //For Facebook
    }

    public void saveInfo(View view){
        String[] name = nameText.getText().toString().split(" ");
        Bitmap bm = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
        User user = new User(name[0], name[1], phoneText.getText().toString(), snapchatUsername, instagramUsername, twitterUsername, facebookID, getCurrentLocation(),emailAddress);
        Gson gson = new Gson();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", gson.toJson(user));
        editor.apply();

        /** FIRESTORE */

        db.collection("users").document(emailAddress)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(), Code.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                });


    }


}


