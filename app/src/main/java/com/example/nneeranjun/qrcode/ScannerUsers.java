package com.example.nneeranjun.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScannerUsers extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ListView users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_users);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        users = (ListView) findViewById(R.id.scannedUsers);
        //Test
        Bitmap test_image = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.profile_test);
        User user = new User("Nilay","Neeranjun","9548920275","nneeranjun","nneeranjun","nneeranjun","Davie",test_image);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Gson gson = new Gson();

        editor.putString(user.getIdentifier(),gson.toJson(user));
        editor.commit();

        //Test


        List<User> userList = getAllValues();
        if (userList.isEmpty()){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("No scans detected");
            users.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,arrayList));

        }
        else {

            users.setAdapter(new CustomUserAdapter(getApplicationContext(), getAllValues()));
        }





    }


    public List<User> getAllValues() {
        Map<String,?> keys = sharedPreferences.getAll();
        List<User> userList = new ArrayList<User>();

        for (Map.Entry<String,?> entry : keys.entrySet()) {

            if (entry.getKey()!="user"|| entry.getKey()!="loggedIn"){

                Gson gson = new Gson();
                userList.add(gson.fromJson(entry.getValue().toString(),User.class));

            }
        }
        return userList;
    }


}
