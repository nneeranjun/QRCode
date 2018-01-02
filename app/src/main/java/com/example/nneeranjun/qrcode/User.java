package com.example.nneeranjun.qrcode;


import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.Random;
import java.util.UUID;

/**
 * Created by nneeranjun on 10/6/17.
 */



public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String snapchat;
    private String instagram;
    private String twitter;
    private String facebook;
    private String location;
    private Bitmap qrcode;


    public User(String firstName, String lastName, String phoneNumber, String snapchat, String instagram, String twitter, String facebook, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.snapchat = snapchat;
        this.instagram = instagram;
        this.facebook = facebook;
        this.location = location;
        this.twitter = twitter;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSnapchat() {
        return snapchat;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getFacebook() {
        return facebook;
    }
    public Bitmap getQrcode() {
        return generateQrCode();
    }

    public String getTwitter() {
        return twitter;
    }
    public String getLocation() {
        return location;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setSnapchat(String snapchat) {
        this.snapchat = snapchat;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    private Bitmap generateQrCode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Gson gson = new Gson();
        String objectString = gson.toJson(this);
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(objectString, BarcodeFormat.QR_CODE, 700, 700);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode = bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return qrcode;
    }




}


