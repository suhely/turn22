package com.sdu.turn22.persistence;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sdu.turn22.model.Game;
import com.sdu.turn22.model.Player;
import com.sdu.turn22.model.Turns;


@Database(entities = {Game.class, Turns.class, Player.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "turn_db";
    private static AppDatabase appDb;

    public static AppDatabase getAppDb(Context context) {
        if (appDb == null) {
            appDb = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return appDb;
    }

    public abstract GameDao getGameDao();
    public abstract TurnDao getTurnDao();
    public abstract PlayerDao getPlayerDao();

}


