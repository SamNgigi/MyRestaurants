package com.hai.jedi.myrestaurants.Models;

import java.util.ArrayList;
import java.util.List;

// Library for serialization and deserialization
import org.parceler.Parcel;

@Parcel
public class Restaurant {

    // We use this class to define the Restaurant Object.
    // We remove the private here.
    String name;
    String phone;
    String website;
    double rating;
    String imageUrl;
    double latitude;
    double longitude;
    List<String> address = new ArrayList<>();
    List<String> categories = new ArrayList<>();

    public Restaurant(){}

    public Restaurant(String name, String phone, String website, double rating,
                      String imageUrl, double latitude, double longitude, ArrayList<String> address,
                      ArrayList<String> categories){
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.categories = categories;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getWebsite(){
        return website;
    }

    public double getRating(){
        return rating;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public List<String> getAddresses(){
        return address;
    }

    public List<String> getCategories(){
        return categories;
    }

    public String getLargeImageUrl(String imageUrl){
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }
}
