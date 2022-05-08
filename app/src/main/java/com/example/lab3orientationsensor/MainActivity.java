package com.example.lab3orientationsensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{
    TextView x;
    TextView y;
    TextView z;
    TextView linenow;
    TextView linealways;
    int prez = 0;
    int delta = 2;//abs(prez-cur)>2 means not in a straight line.
    int cnt = 0;//times that usr are not in straight line.
    SensorManager mySensorManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = findViewById(R.id.text_x);
        y = findViewById(R.id.text_y);
        z = findViewById(R.id.text_z);
        linenow = findViewById(R.id.text_linenow);
        linealways = findViewById(R.id.text_linealways);

        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(this
                , mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        mySensorManager.unregisterListener(this);
        super.onPause();

    }

    @Override
    protected void onStop(){
        mySensorManager.unregisterListener(this);
        super.onStop();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        //int sensorType = event.sensor.getType();

        x.setText("x-axis: "+(int)event.values[1]);
        y.setText("y-axis: "+(int)event.values[2]);
        z.setText("z-axis: "+(int)event.values[0]);

        int cur = (int)event.values[0];
        if(Math.abs(prez - cur) > delta) {
            linenow.setText("You are not in a straight line now.");
            cnt++;
            if(cnt == 3)
                linealways.setText("You haven't been always in a straight line.");
        }
        else
            linenow.setText("You are in a straight line now.");
        prez = cur;
    }
}