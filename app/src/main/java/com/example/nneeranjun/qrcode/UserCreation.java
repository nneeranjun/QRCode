package com.example.nneeranjun.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.objects.IGUser;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;
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
    EditText snapchat;
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
        snapchat = findViewById(R.id.snapchat);
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

       /* InstagramLoginCallbackListener instagramLoginCallbackListener = new InstagramLoginCallbackListener() {
            @Override
            public void onSuccess(IGSession session) {


            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(InstagramException error) {


            }
        };

        String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.RELATIONSHIP};
        InstagramLoginButton instagramLoginButton = findViewById(R.id.instagram_btn);
        instagramLoginButton.setInstagramLoginCallback(instagramLoginCallbackListener);
        //if you dont specify scopes, you will have basic access.
        instagramLoginButton.setScopes(scopes);

        InstagramAPIResponseCallback<IGUser> instagramUserResponseCallback = new InstagramAPIResponseCallback<IGUser>() {
            @Override
            public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {

                instagramUsername=responseObject.getUsername();
            }

            @Override
            public void onFailure(InstagramException exception) {

            }
        };

        InstagramEngine.getInstance(this).getUserDetails(instagramUserResponseCallback);
        */





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
        snapchatUsername = snapchat.getText().toString();
        Bitmap bm = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
        User user = new User(name[0], name[1], phoneText.getText().toString(), snapchatUsername, instagramUsername, twitterUsername, facebookID, getCurrentLocation(),emailAddress);



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


