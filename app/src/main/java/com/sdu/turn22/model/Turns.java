package com.sdu.turn22.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Turns {
    //Autoincrement
    @PrimaryKey(autoGenerate = true)
    public long turn_id;

    @TypeConverters(TurnsConverter.class)
    private Amount amount_of_turns;

    public Turns(){
    }

    public enum Amount {
        FIVE(0, "Five"),
        TEN(1, "Ten"),
        TWENTY(2, "Twenty");

        private int code;
        private String friendlyName;


        Amount(int code, String friendlyName) {
            this.code = code;
            this.friendlyName = friendlyName;
        }

        public int getCode(){
            return code;
        }


        @Override public String toString(){
            return friendlyName;
        }
    }

    public Amount getAmount_of_turns() {
        return amount_of_turns;
    }

    public void setAmount_of_turns(Amount amount_of_turns) {
        this.amount_of_turns = amount_of_turns;
    }
}
