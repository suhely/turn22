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
import android.widget.EditText;
import android.widget.TextView;

import com.sdu.turn22.R;
import com.sdu.turn22.model.Game;
import com.sdu.turn22.persistence.AppDatabase;
import com.sdu.turn22.persistence.GameDao;
import com.sdu.turn22.persistence.PlayerDao;
import com.sdu.turn22.persistence.TurnDao;

public class ResultActivity extends AppCompatActivity implements SensorEventListener{

    float startAzimuth;
    float endAzimuth;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private SensorManager sensorManager;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Context context = getApplicationContext();
        TurnDao turnDao = AppDatabase.getAppDb(context).getTurnDao();
        PlayerDao playerDao = AppDatabase.getAppDb(context).getPlayerDao();
        GameDao gameDao = AppDatabase.getAppDb(context).getGameDao();


        Intent intent = getIntent();
        this.game = (Game) intent.getSerializableExtra("Game");

        // write scored points into database
        game.setPoints(calculatePoints());

        // filling text fields dynamically
        Resources res = getResources();
        String result_player = String.format(res.getString(R.string.result_text_player), playerDao.getById(game.idOfPlayer).getPlayer_name());
        String result_points = String.format(res.getString(R.string.result_text_points), game.getPoints());
        String amount_turns_display_text = String.format(res.getString(R.string.readyInfo), turnDao.getById(game.idOfTurn).getAmount_of_turns().toString());

        TextView textView1 = findViewById(R.id.textView_player);
        textView1.setText(result_player);
        TextView textView2 = findViewById(R.id.textView_points);
        textView2.setText(result_points);
        TextView textView3 = findViewById(R.id.textView_turns);
        textView3.setText(amount_turns_display_text);

        gameDao.insertGame(game);
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

                endAzimuth = orientation[0];

                if (endAzimuth >= -1f && endAzimuth <= 1f){
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

    public void onHome(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("Game", game);
        startActivity(intent);
    }

    private int calculatePoints(){
        Intent intent = getIntent();
        startAzimuth = (float) intent.getSerializableExtra("startAzimuth");
        int result = Math.round(Math.abs(startAzimuth - endAzimuth));
        int points = 180 - result;
        return points;


    }
}