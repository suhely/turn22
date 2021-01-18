package com.sdu.turn22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdu.turn22.R;
import com.sdu.turn22.model.Game;
import com.sdu.turn22.persistence.AppDatabase;
import com.sdu.turn22.persistence.TurnDao;

public class ReadyActivity extends AppCompatActivity implements SensorEventListener {

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private SensorManager sensorManager;

    // Get the Intent that started this activity and extract the game
    Intent intent = getIntent();
    Game game = (Game) intent.getSerializableExtra("Game");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);
        Context context = getApplicationContext();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        TurnDao turnDao = AppDatabase.getAppDb(context).getTurnDao();



        Resources res = getResources();
        String amount_turns_display_text = String.format(res.getString(R.string.readyInfo), turnDao.getById(game.idOfTurn).getAmount_of_turns().toString());

        TextView textView = findViewById(R.id.textView_readyInfo);
        textView.setText(amount_turns_display_text);
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

        Button start = (Button) findViewById(R.id.button_start);

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

                float azimuth = orientation[0];

                if (azimuth >= -1f && azimuth <= 1f){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    start.setEnabled(true);

                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    start.setEnabled(false);

                }
            }
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onStart(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Game", game);
        startActivity(intent);

    }
}