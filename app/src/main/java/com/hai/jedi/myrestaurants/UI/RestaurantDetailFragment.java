package com.hai.jedi.myrestaurants.UI;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuInflater;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseUser;

import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;

import org.parceler.Parcels;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;



/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment implements View.OnClickListener{
    // Predefining the size of our images.
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    //
    private static final int REQUEST_IMAGE_CAPTURE = 111;

    //Attaching the layout views to our logic
    @BindView(R.id.restaurantImageView) ImageView mImageLabel;
    @BindView(R.id.restaurantNameTextView) TextView nameLabel;
    @BindView(R.id.tagTextView) TextView mTagLabel;
    @BindView(R.id.ratingTextView) TextView mRatingLabel;
    @BindView(R.id.urlTextView) TextView mUrlLabel;
    @BindView(R.id.phoneTextView) TextView phoneLabel;
    @BindView(R.id.addressTextView) TextView addressLabel;
    @BindView(R.id.saveRestaurantButton) TextView mSaveRestaurantButton;

    private Restaurant mRestaurant;
    private ArrayList<Restaurant> mRestaurants;
    private int mPosition;
    private String mSource;


    private ProgressDialog mSavingProgressDialog;

    /**
     * This method below is used instead of a constructor and returns a new instance of our
     * RestaurantDetailFragment.
     *
     * We use the Parceler library add our restaurant object to our bundle and set the bundle as the
     * argument for our new RestaurantDetailFragment*/
    public static RestaurantDetailFragment newInstance(ArrayList<Restaurant> restaurants,
                                                       int position,
                                                       String source) {
        // Required empty public constructor. Instance of our Restaurant Fragment
        RestaurantDetailFragment restaurant_detail_fragment = new RestaurantDetailFragment();
        // I think what we will use to store and transfer  our serialized data to Fragment
        Bundle arguments = new Bundle();
        // Serializing our restaurants object using Parcels.wrap then "attaching it" to their arguments,
        // in a hash-map  way/ dictionary using key value pairs.
        arguments.putParcelable(Constants.EXTRA_KEY_RESTAURANTS, Parcels.wrap(restaurants));
        // We also add the int position of the restaurants as arguments.
        arguments.putInt(Constants.EXTRA_KEY_POSITION, position);
        arguments.putString(Constants.KEY_SOURCE, source);
        // Passing the serialized data to our fragment.
        restaurant_detail_fragment.setArguments(arguments);
        // returning the restaurant_fragment
        return restaurant_detail_fragment;
    }

    // Below method will be called when the fragment is created and then we unwrap our restaurant
    // object
    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        // When our fragment instances is created/initialized we want to deserialize the serialized
        // restaurant and store it to mRestaurant.
        assert getArguments() != null;
        mRestaurants = Parcels.unwrap(getArguments().getParcelable(
                      Constants.EXTRA_KEY_RESTAURANTS));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mRestaurant = mRestaurants.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
        createSavingProgressDialog();
    }

    public void createSavingProgressDialog(){
        mSavingProgressDialog = new ProgressDialog(getActivity());
        mSavingProgressDialog.setTitle("Loading...");
        mSavingProgressDialog.setMessage("Saving Restaurant to Firebase...");
        mSavingProgressDialog.setCancelable(false);
    }

    // Here we set the various restaurant attributes to the ImageViews and TextViews
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        // Attaching the various Restaurant object properties to the layout/view
        // Loading images with picasso with predefined size and some style edit.

        if(mSource.equals(Constants.SOURCE_SAVED)){
            mSaveRestaurantButton.setVisibility(View.GONE);
        }else {
            mSaveRestaurantButton.setOnClickListener(this);
        }

        Picasso.get().load(mRestaurant.getImageUrl())
                     .resize(MAX_WIDTH, MAX_HEIGHT)
                     .centerCrop()
                     .into(mImageLabel);

        nameLabel.setText(mRestaurant.getName());
        mTagLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getCategories()));
        mRatingLabel.setText(String.format("%s/5",Double.toString(mRestaurant.getRating())));
        phoneLabel.setText(mRestaurant.getPhone());
        addressLabel.setText(android.text.TextUtils.join(", ", mRestaurant.getAddresses()));
        // mUrlLabel.setText(mRestaurant.getName());

        // We set OnClickListeners to website url phone and addresses
        mUrlLabel.setOnClickListener(this);
        phoneLabel.setOnClickListener(this);
        addressLabel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        if(view == mUrlLabel){
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(mRestaurant.getWebsite()));
            startActivity(webIntent);
        }
        if(view == phoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel: " + mRestaurant.getPhone()));
            startActivity(phoneIntent);
        }
        if(view == addressLabel){
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("geo: " + mRestaurant.getLatitude()
                                            + "," + mRestaurant.getLongitude()
                                            +"?q=(" + mRestaurant.getName() + ")"));
            startActivity(mapIntent);
        }
        if(view == mSaveRestaurantButton) {
            mSavingProgressDialog.show();
            // Getting the user's uid.
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = Objects.requireNonNull(user).getUid();

            DatabaseReference restaurantRef = FirebaseDatabase
                                             .getInstance()
                                             .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                                             .child(uid); // We use this method to create node within
                                                          // the restaurant node to store the given user's
                                                          // list of restaurants.

            restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                    final ArrayList<String> restaurants = new ArrayList<>();
                    // This loop might be working to slow.
                    // When you save before the api call can check, the same restaurant is persisted
                    // twice.
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        restaurants.add(Objects.requireNonNull(snapshot.getValue(Restaurant.class))
                                   .getName());

                    }

                    Log.d("CAN YOU SEE ME", restaurants.toString());
                    Log.d("CAN YOU SEE ME",
                            Boolean.toString(restaurants.contains(mRestaurant.getName())));
                    /**
                     * We have to use the string property of the restaurant object because
                     * the object itself is not unique enough to check if object exists
                     * */
                    if(restaurants.contains(mRestaurant.getName())){
                        Toast.makeText(getContext(),
                                "Restaurant is already saved",
                                Toast.LENGTH_LONG).show();
                    } else {
                        DatabaseReference pushRef = restaurantRef.push();
                        String pushId = pushRef.getKey();
                        mRestaurant.setPushId(pushId); // setting pushId to restaurant object
                        pushRef.setValue(mRestaurant);
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                    }
                    mSavingProgressDialog.dismiss();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError dbError){
                    // do something
                }
            });

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(mSource.equals(Constants.SOURCE_SAVED)){
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_photo:
                onLaunchCamera();
            default:
                break;
        }
        return false;
    }

    public void onLaunchCamera(){
        /*
        * We set up out Intent, providing MediaStore.ACTION_IMAGE_CAPTURE  as a param.
        * This is an implicit intent, that will instruct Android to automatically access the
        * device's camera.
        *
        * MediaStore is a built-in Android class that handles all things media. ACTION_IMAGE_CAPTURE
        * is the standard intent that accesses the device's camera application
        * */
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*
        * This conditional statements basically ensures that there is a camera app available
        * to take pictures.
        *
        * This makes sure the app will not crash if there is no camera
         * */
        if(takePicIntent.resolveActivity(Objects.requireNonNull(getActivity())
                .getPackageManager()) != null ){
            /*
            * Passing our intent and  indicate we expect a result by using the
            * startActivityForResult.
            *
            * We also pass in our REQUEST_IMAGE_CAPTURE.
            * This should be an int value. If it is greater than 1, the result of the
            * action we are launching will be returned automatically in the call back
            * method onActivityResult.
            *
            * This value may also be used to identify specific results when multiple implicit
            * intents are being triggered and return multiple pieces of info back
            * */
            startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        /**
         * @param requestCode represents the REQUEST_IMAGE_CAPTURE value we provided in the
         *                    startActivityForResult()
         * @param resultCode - status of the activity, whether is was successful or not.
         *
         * @param data - the Intent object that includes intent extras containing the info
         *             being returned. In this case- our image.
         * */
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK){
            // Retrieving the intent content - i.e our image
            Bundle extras = data.getExtras();
            // Creating a new Bitmap object
            Bitmap imgBitMap = (Bitmap) Objects.requireNonNull(extras).get("data");
            // Setting the our ImageView to contain our imgBitMap image
            mImageLabel.setImageBitmap(imgBitMap);
            // Custom method to encode our image in Base64 and save to firebase to persist
            // the image.
            encodeBitmapAndSaveToFirebase(imgBitMap);
        }

    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        // Here use Android's Base64 and not Java's
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                                        .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(mRestaurant.getPushId())
                                        .child("imageUrl");
        reference.setValue(imageEncoded);
    }

}
