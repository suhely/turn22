package com.sdu.turn22.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.sdu.turn22.model.Game;
import com.sdu.turn22.model.Player;

@Dao
public interface PlayerDao {
    @Transaction
    @Query("SELECT * FROM Player")
    Player[] getAll();

    @Query("SELECT * FROM Player WHERE player_id = :id")
    Player getById(long id);

    @Insert
    long[] insertAll(Player... players);

    @Insert
    long insertPlayer(Player player);
}


