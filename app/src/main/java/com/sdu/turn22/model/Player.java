package com.sdu.turn22.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    //Autoincrement
    @PrimaryKey(autoGenerate = true)
    public long player_id;

    @ColumnInfo
    public String player_name;

    @ColumnInfo
    public String player_age;

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_age() {
        return player_age;
    }

    public void setPlayer_age(String player_age) {
        this.player_age = player_age;
    }
}
