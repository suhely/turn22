package com.sdu.turn22.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.sdu.turn22.model.Game;

@Dao
public interface GameDao {

    @Transaction
    @Query("SELECT * FROM Game ORDER BY points DESC")
    Game[] getAll();

    @Query("SELECT * FROM Game WHERE game_id = :id")
    Game getById(long id);

    @Query("Select * from Game where idOfTurn = :turn_id ORDER By points")
    Game[] getGameByTurn(long turn_id);

    @Insert
    long[] insertAll(Game... games);

    @Insert
    long insertGame(Game game);

    // ... represents infinite games

}
