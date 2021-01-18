package com.sdu.turn22.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.sdu.turn22.model.Game;
import com.sdu.turn22.model.Player;
import com.sdu.turn22.model.PlayerWithGame;

import java.util.List;

@Dao
public interface GameDao {

    @Transaction
    @Query("SELECT * FROM Game ORDER BY points DESC")
    Game[] getAll();

    @Query("SELECT * FROM Game WHERE game_id = :id")
    Game getById(long id);

    @Insert
    long[] insertAll(Game... games);

    @Insert
    long insertGame(Game game);

    // ... represents infinite games

}
