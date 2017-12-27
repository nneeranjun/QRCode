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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.qrcode.encoder.QRCode;

public class UserCreation extends AppCompatActivity {
    EditText nameText;
    EditText phoneText;
    EditText snapchatText;
    EditText instagramText;
    EditText twitterText;
    ImageView profileImage;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        nameText = (EditText) findViewById(R.id.name);
        phoneText = (EditText) findViewById(R.id.phoneNumber);
        snapchatText = (EditText)findViewById(R.id.snapchat);
        instagramText = (EditText) findViewById(R.id.instagram);
        twitterText = (EditText) findViewById(R.id.twitter);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        button= (Button) findViewById(R.id.saveButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errorMessage="";
                if (nameText.getText().toString()==""){
                    errorMessage += "Enter Valid Name. ";
                }
                if (phoneText.getText().toString()==""){
                    errorMessage += "Enter Valid Number. ";
                }
                if (snapchatText.getText().toString()==""){
                    errorMessage += "Enter Valid Snapchat. ";
                }

                if (instagramText.getText().toString()==""){
                    errorMessage += "Enter Valid Instagram. ";
                }

                if (twitterText.getText().toString()==""){
                    errorMessage += "Enter Valid Twitter. ";
                }

                if(errorMessage!=""){
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }

                else{
                    String[] name = nameText.getText().toString().split(" ");
                    Bitmap bm=((BitmapDrawable)profileImage.getDrawable()).getBitmap();
                    User user = new User(name[0],name[1],phoneText.getText().toString(),snapchatText.getText().toString(),instagramText.getText().toString(),twitterText.getText().toString(),getCurrentLocation(),bm);
                    Gson gson = new Gson();
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user",gson.toJson(user));
                    editor.commit();
                   startActivity(new Intent(getApplicationContext(),Code.class));

                }
            }
        });





    }

    public String getCurrentLocation(){
        return "Berkeley";
    }
}


