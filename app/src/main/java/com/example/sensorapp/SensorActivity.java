package com.example.sensorapp;


import static androidx.core.content.ContextCompat.startActivity;

import static com.example.sensorapp.SensorDetailsActivity.EXTRA_SENSOR_TYPE_PARAMETER;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;

    private static final String SENSOR_APP_TAG = "SENSOR_APP_TAG";
    private final List<Integer> favSensors = Arrays.asList(Sensor.TYPE_LIGHT, Sensor.TYPE_ACCELEROMETER);
    public static final int SENSOR_DETAILS_ACTIVITY_REQUEST_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView=findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensorList.forEach(sensor -> {
            Log.d(SENSOR_APP_TAG, "Sensor name:" + sensor.getName());
            Log.d(SENSOR_APP_TAG, "Sensor vendor:" + sensor.getVendor());
            Log.d(SENSOR_APP_TAG, "Sensor max range:" + sensor.getMaximumRange());
        });

        if(adapter==null){
            adapter=new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensors_count_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_sensors_count:
                String string = getString(R.string.sensors_count, sensorList.size());
                getSupportActionBar().setSubtitle(string);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class SensorHolder extends RecyclerView.ViewHolder{

        private TextView SensornameTextView;
        private ImageView SensoriconImageView;
        private Sensor sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            //itemView.setOnClickListener(this);

            SensornameTextView = itemView.findViewById(R.id.sensor_item_name);
            SensoriconImageView = itemView.findViewById(R.id.sensor_imageView);

        }

        public void bind(Sensor sensor) {
            this.sensor=sensor;
            SensornameTextView.setText(sensor.getName()+" "+sensor.getType());
            SensoriconImageView.setImageResource(R.drawable.ic_sensor_off);
            View itemContainer = itemView.findViewById(R.id.sensor_list_item);
            View magneticContainer = itemView.findViewById(R.id.sensor_list_item);
            if (favSensors.contains(sensor.getType())) {
                itemContainer.setBackgroundColor(getResources().getColor(R.color.fav_item));
                itemContainer.setOnClickListener(v -> {
                    Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
                    intent.putExtra(EXTRA_SENSOR_TYPE_PARAMETER, sensor.getType());
                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
                });
            }
            if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                magneticContainer.setBackgroundColor(getResources().getColor(R.color.purple_200));
                magneticContainer.setOnClickListener(v->{
                    Intent intent = new Intent(SensorActivity.this, LocationActivity.class);
                    intent.putExtra(EXTRA_SENSOR_TYPE_PARAMETER, sensor.getType());
                    startActivityForResult(intent, SENSOR_DETAILS_ACTIVITY_REQUEST_CODE);
                });
            }
        }


//        @Override
//        public void onClick(View view) {
//
//        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder>{
        private List<Sensor> sensorList;
        public SensorAdapter(List<Sensor> sensorList){
            this.sensorList=sensorList;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position){
            Sensor sensor = sensorList.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return sensorList.size();
        }


    }

}
