package com.hai.jedi.myrestaurants.UI;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import android.util.Log;

import android.widget.TextView;

import android.graphics.Typeface;

import com.google.firebase.auth.FirebaseAuth;
import com.hai.jedi.myrestaurants.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

// We implement the built in OnClickListener here
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Had not seen this. Had to initialize tag here.
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView( R.id.sample_text) TextView welcome_text;

    @BindView(R.id.findRestaurantsButton) Button mFindRestaurantsButton;

    @BindView(R.id.savedRestaurantButton) Button mSavedRestaurantsButton;

    // Used to load the 'native-lib' library on application startup.
    protected void loadNativeLibraries() {
        try {
            System.loadLibrary("native-lib");
        } catch (UnsatisfiedLinkError error){
            String error_string = "STILL HAVE THIS ERROR: " + error;
            Log.d(TAG, error_string);
        }
    }

    @SuppressLint( "CommitPrefEdits" )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Run default behaviours for an activity.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Getting our font
        Typeface ostrich_font = Typeface.createFromAsset(
                getAssets(), "fonts/ostrich-regular.ttf");
        // Setting our font at runtime.
        welcome_text.setTypeface(ostrich_font);

        // We listen for a click then implement View.OnClickListener's onClick() method.
        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflating our menu overflow with our menu_main.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // Implementing the View.OnClickListener onClick() method for our navigating to other activity
    @Override
    public void onClick(View v){
        // We check which view has been clicked and take action accordingly.
        if(v == mFindRestaurantsButton) {
            Intent intent = new Intent(MainActivity.this, RestaurantsListActivity.class);
            startActivity(intent);
        }
        if(v == mSavedRestaurantsButton){
            Intent intent = new Intent(MainActivity.this,
                    SavedRestaurantsListActivity.class);
            startActivity(intent);
        }
    }


}