package com.sdu.turn22.persistence;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.sdu.turn22.model.Player;
import com.sdu.turn22.model.Turns;
import com.sdu.turn22.model.TurnsConverter;

@Dao
public interface TurnDao {
    @Transaction
    @Query("SELECT * FROM Turns")
    Turns[] getAll();

    @Query("SELECT * FROM Turns WHERE turn_id = :id")
    Turns getById(long id);

//    @Query("SELECT * FROM Turns WHERE amount_of_turns = :amount")
//    Turns getByTurn(Turns.Amount amount);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertAll(Turns... turns);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTurn(Turns turn);
}

