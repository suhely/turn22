package com.sdu.turn22.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.sdu.turn22.R;
import com.sdu.turn22.model.Game;
import com.sdu.turn22.model.Player;
import com.sdu.turn22.model.Turns;
import com.sdu.turn22.persistence.AppDatabase;
import com.sdu.turn22.persistence.PlayerDao;
import com.sdu.turn22.persistence.TurnDao;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class PleaseEnterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_enter);
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_id);
        mySpinner.setAdapter(new ArrayAdapter<Turns.Amount>(this, android.R.layout.simple_spinner_item, Turns.Amount.values()));
    }

    public void sendDetails(View view) {
        Context context = getApplicationContext();
        Intent intent = new Intent(this, ReadyActivity.class);

        PlayerDao playerDao = AppDatabase.getAppDb(context).getPlayerDao();
        TurnDao turnDao = AppDatabase.getAppDb(context).getTurnDao();

        Game game = new Game();
        Player player = new Player();
        Turns turns = new Turns();

        EditText editText_playerName = (EditText) findViewById(R.id.editText_playerName);
        String playerName = editText_playerName.getText().toString();
        player.setPlayer_name(playerName);

        EditText editText_age = (EditText) findViewById(R.id.editText_age);
        String age = editText_age.getText().toString();
        player.setPlayer_age(age);


        Spinner spinner_amountTurns = (Spinner) findViewById(R.id.spinner_id);
        String amountTurns =  spinner_amountTurns.getSelectedItem().toString();
        Turns.Amount amount = Turns.Amount.valueOf(amountTurns.toUpperCase());
        turns.setAmount_of_turns(amount);


        long player_id = playerDao.insertPlayer(player);
        long turn_id = turnDao.insertTurn(turns);
        game.idOfPlayer = player_id;
        game.idOfTurn = turn_id;

        intent.putExtra("Game", game);
        startActivity(intent);
    }
}