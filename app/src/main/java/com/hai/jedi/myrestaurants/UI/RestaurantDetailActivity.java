package com.hai.jedi.myrestaurants.UI;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;

// We have to import this now because everything is now  modularized and not in the
// default locations
import com.hai.jedi.myrestaurants.Constants;
import com.hai.jedi.myrestaurants.R;
import com.hai.jedi.myrestaurants.Models.Restaurant;
import com.hai.jedi.myrestaurants.Adapters.RestaurantPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RestaurantDetailActivity
        extends AppCompatActivity
        implements SensorEventListener {

    private final String TAG = RestaurantDetailActivity.class.getSimpleName();

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private RestaurantPagerAdapter restaurantAdapterViewPager;
    ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    // Shaking Sensor Manenos
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);

        mRestaurants = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_RESTAURANTS));
        int starting_position = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        /*
        * We instantiate an new pager adapter providing  our restaurant array as Arguments*/
        restaurantAdapterViewPager = new RestaurantPagerAdapter(getSupportFragmentManager(),
                mRestaurants);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mViewPager.setAdapter(restaurantAdapterViewPager);
        mViewPager.setCurrentItem(starting_position);
        mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 1000;

                if(speed > SHAKE_THRESHOLD) {
                    Log.d(TAG, "SensorEventListener: Shaking");
                    Intent intent = new Intent(RestaurantDetailActivity.this,
                                                SavedRestaurantsListActivity.class);

                    startActivity(intent);

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Do something
    }

}
