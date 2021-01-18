package com.sdu.turn22.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Game implements Serializable {
    //Autoincrement
    @PrimaryKey(autoGenerate = true)
    public long game_id;

    @ColumnInfo
    public int points;

    @ColumnInfo
    public long idOfPlayer;

    @ColumnInfo
    public long idOfTurn;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
