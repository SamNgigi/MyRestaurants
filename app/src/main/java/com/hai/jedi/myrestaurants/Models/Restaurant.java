package com.hai.jedi.myrestaurants.Models;

import java.util.ArrayList;

// Library for serialization and deserialization
import org.parceler.Parcel;

@Parcel
public class Restaurant {

    // We use this class to define the Restaurant Object.

    private String mName;
    private String mPhone;
    private String mWebsite;
    private double mRating;
    private String mImageUrl;
    private double mLatitude;
    private double mLongitude;
    private ArrayList<String> mAddress = new ArrayList<>();
    private ArrayList<String> mCategories = new ArrayList<>();

    public Restaurant(){};

    public Restaurant(String name, String phone, String website, double rating,
                      String imageUrl, double latitude, double longitude, ArrayList<String> address,
                      ArrayList<String> categories){
        this.mName = name;
        this.mPhone = phone;
        this.mWebsite = website;
        this.mRating = rating;
        this.mImageUrl = imageUrl;
        this.mAddress = address;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mCategories = categories;
    }

    public String getmName(){
        return mName;
    }

    public String getmPhone(){
        return mPhone;
    }

    public String getmWebsite(){
        return mWebsite;
    }

    public double getmRating(){
        return mRating;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public double getmLatitude(){
        return mLatitude;
    }

    public double getmLongitude(){
        return mLongitude;
    }

    public ArrayList<String> getmAddresses(){
        return mAddress;
    }

    public ArrayList<String> getmCategories(){
        return mCategories;
    }
}
