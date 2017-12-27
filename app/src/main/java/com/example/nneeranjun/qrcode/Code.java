package com.example.nneeranjun.qrcode;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Code extends AppCompatActivity {
    ImageView qr_view;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
      // Bitmap test_image = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.profile_test);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString("user",""),User.class);
        //User user = new User("nilay","neeranjun","9548920275","neeranjun", "neeranjun", "nneeranjun","Davie");
        Bitmap qrcode = user.getQrcode();
        qr_view = (ImageView) findViewById(R.id.qr);
       qr_view.setImageBitmap(qrcode);


    }


}
