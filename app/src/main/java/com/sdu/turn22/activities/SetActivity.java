package com.sdu.turn22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdu.turn22.R;
import com.sdu.turn22.model.Game;

public class SetActivity extends AppCompatActivity implements SensorEventListener{

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private SensorManager sensorManager;
    float azimuth;
    private Game game;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Intent intent = getIntent();
        this.game = (Game) intent.getSerializableExtra("Game");

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Button measure = (Button) findViewById(R.id.button_measure);

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {

                // orientation contains azimuth, pitch and roll
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                 azimuth = orientation[0];

                if (azimuth >= -1f && azimuth <= 1f){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    measure.setEnabled(true);

                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    measure.setEnabled(false);

                }
            }
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onMeasure(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Game", game);
        intent.putExtra("startAzimuth", azimuth);
        startActivity(intent);
    }

//    calculate points in result activity
}