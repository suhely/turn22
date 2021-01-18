package com.sdu.turn22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

//        context = getApplicationContext();
//        gameDao = AppDatabase.getAppDb(context).getGameDao();
//        turnDao = AppDatabase.getAppDb(context).getTurnDao();
//        playerDao = AppDatabase.getAppDb(context).getPlayerDao();
//
//        Player player = new Player();
//        player.setPlayer_name("BigBootyBoi");
//        long player_id = playerDao.insertPlayer(player);
//
//        Turns turn = new Turns();
//        turn.setAmount_of_turns(Turns.Amount.ZEHN);
//        long turn_id = turnDao.insertTurn(turn);
//
//        Game game = new Game();
//        game.points = 10;
//        game.idOfPlayer = player_id;
//        game.idOfTurn = turn_id;
//
//        long game_id = gameDao.insertGame(game);
//        Game game_from_db = gameDao.getById(game_id);
//
//        TextView textView = findViewById(R.id.text_view1);
//        String game_text = "Game_Points: " + game_from_db.points + " Amount_of_Turns: " + turnDao.getById(game_from_db.idOfTurn).getAmount_of_turns().toString() + " Player_Name: " + playerDao.getById(game_from_db.idOfPlayer).getPlayer_name();
//        textView.setText(game_text);
    }

    public void startNewGame(View view){
        Intent intent = new Intent(this, PleaseEnterActivity.class);
        startActivity(intent);
    }


}