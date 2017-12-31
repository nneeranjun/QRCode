package com.example.nneeranjun.qrcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    EditText snapchatText;
    EditText instagramText;
    EditText twitterText;
    String twitterUsername;
    TwitterLoginButton twitterBtn;
    ImageView profileImage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        twitterUsername = "";



        /** TWITTER BUTTON STUFF */
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        twitterBtn = (TwitterLoginButton) findViewById(R.id.twitter_btn);
        twitterBtn.setVisibility(View.VISIBLE);
        twitterBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                twitterUsername=result.data.getUserName();
                Toast.makeText(getApplicationContext(),twitterUsername,Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(getApplicationContext(), "Error linking Twitter account", Toast.LENGTH_SHORT).show();
            }
        });
        /** END TWITTER BUTTON STUFF */


        nameText = (EditText) findViewById(R.id.name);
        phoneText = (EditText) findViewById(R.id.phoneNumber);
        snapchatText = (EditText) findViewById(R.id.snapchat);
        instagramText = (EditText) findViewById(R.id.instagram);
        twitterText = (EditText) findViewById(R.id.twitter);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        button = (Button) findViewById(R.id.saveButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errors= getErrors();
                if(!errors.equals("")){
                    Toast.makeText(getApplicationContext(),errors,Toast.LENGTH_LONG).show();
                }

                else{
                    String[] name = nameText.getText().toString().split(" ");
                    Bitmap bm = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
                    User user = new User(name[0], name[1], phoneText.getText().toString(), snapchatText.getText().toString(), instagramText.getText().toString(), twitterText.getText().toString(), getCurrentLocation());
                    Gson gson = new Gson();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user", gson.toJson(user));
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), Code.class));

                }
            }
        });


    }

    public String getCurrentLocation() {
        return "Berkeley";
    }

    public String getErrors() {
        String errorMessage = "";
        if (nameText.getText().toString() == "") {
            errorMessage += "Enter Valid Name. ";
        }
        if (phoneText.getText().toString() == "") {
            errorMessage += "Enter Valid Number. ";
        }
        if (snapchatText.getText().toString() == "") {
            errorMessage += "Enter Valid Snapchat. ";
        }

        if (instagramText.getText().toString() == "") {
            errorMessage += "Enter Valid Instagram. ";
        }

        if (twitterText.getText().toString() == "") {
            errorMessage += "Enter Valid Twitter. ";
        }

       return errorMessage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterBtn.onActivityResult(requestCode, resultCode, data);
    }
}


