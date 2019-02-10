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
    List<String> addresses = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    // Firebase pushID
    private String pushId;
    /*
    * Instead of having an int index, we go with a String index so that we can set
    * the initial value to a string key in our object constructor.
    *
    * Because alphabetically numbers comes before letters, anytime we add a brand new
    * restaurant it will recieve out default string value.
    *
    * Then when we reorder our restaurants with our new drag & drop, we will overwrite
    * this string index with a numerical index so that any ordered restaurants will come
    * in numerical order and new restaurants will automatically be added to the end of the list
    *
    * */
    String index;


    public Restaurant(){}

    public Restaurant(String name, String phone, String website, double rating,
                      String imageUrl, double latitude, double longitude, ArrayList<String> addresses,
                      ArrayList<String> categories){
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.addresses = addresses;
        this.categories = categories;
        //
        this.index = "not_specified";
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
        return addresses;
    }

    public List<String> getCategories(){
        return categories;
    }

    public String getLargeImageUrl(String imageUrl){
        String largeImageUrl = imageUrl.substring(0, imageUrl.length() - 6).concat("o.jpg");
        return largeImageUrl;
    }

    // For firebase pushId
    public String getPushId(){
        return pushId;
    }

    public void setPushId(String pushId){
        this.pushId = pushId;
    }

    // Getting and setting a Restaurant's index
    public String getIndex(){
        return index;
    }

    public void setIndex(String index){
        this.index = index;
    }

}
