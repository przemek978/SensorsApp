package com.example.sensorapp;


import static androidx.core.content.ContextCompat.startActivity;

import static com.example.sensorapp.SensorActivity.KEY_EXTRA_TASK_ID;
import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_TASK_ID ="KEY_EXTRA_TASK_ID" ;
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView=findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);

        if(adapter==null){
            adapter=new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    private class SensorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView SensornameTextView;
        private ImageView SensoriconImageView;
        private Sensor sensor;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            itemView.setOnClickListener(this);

            SensornameTextView = itemView.findViewById(R.id.sensor_item_name);
            SensoriconImageView = itemView.findViewById(R.id.sensor_imageView);

        }

        public void bind(Sensor sensor) {
            this.sensor = sensor;
            SensornameTextView.setText(sensor.getName());
        }


        @Override
        public void onClick(View view) {

        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder>{
        private List<Sensor> sensorList;
        public SensorAdapter(List<Sensor> sensorList){
            this.sensorList=sensorList;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
//            return new SensorHolder(layoutInflater, parent);
        return null;
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position){

        }

        @Override
        public int getItemCount() {
            return 0;
        }


    }

}
