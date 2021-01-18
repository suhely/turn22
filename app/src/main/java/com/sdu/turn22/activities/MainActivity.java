package com.sdu.turn22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sdu.turn22.R;
import com.sdu.turn22.model.Game;
import com.sdu.turn22.model.Player;
import com.sdu.turn22.model.Turns;
import com.sdu.turn22.persistence.AppDatabase;
import com.sdu.turn22.persistence.GameDao;
import com.sdu.turn22.persistence.PlayerDao;
import com.sdu.turn22.persistence.TurnDao;

public class MainActivity extends AppCompatActivity {

    private GameDao gameDao;
    private Context context;
    private PlayerDao playerDao;
    private TurnDao turnDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        gameDao = AppDatabase.getAppDb(context).getGameDao();
        playerDao = AppDatabase.getAppDb(context).getPlayerDao();
        turnDao = AppDatabase.getAppDb(context).getTurnDao();

        Game[] games = gameDao.getAll();

        for (int i = 0; i < games.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(games[i].points + " Amount_of_Turns: " + turnDao.getById(games[i].idOfTurn).getAmount_of_turns().toString() + " Player_Name: " + playerDao.getById(games[i].idOfPlayer).getPlayer_name());
        }

    }

    public void startNewGame(View view) {
        Intent intent = new Intent(this, PleaseEnterActivity.class);
        startActivity(intent);
    }


}