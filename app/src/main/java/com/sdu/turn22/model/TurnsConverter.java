package com.sdu.turn22.model;

import androidx.room.TypeConverter;

import static com.sdu.turn22.model.Turns.Amount.FIVE;
import static com.sdu.turn22.model.Turns.Amount.TEN;
import static com.sdu.turn22.model.Turns.Amount.TWENTY;


public class TurnsConverter {

    @TypeConverter
    public static Turns.Amount toAmount(int amount) {
        if (amount == FIVE.getCode()) {
            return FIVE;
        } else if (amount == TEN.getCode()) {
            return TEN;
        } else if (amount == TWENTY.getCode()) {
            return TWENTY;
        } else {
            throw new IllegalArgumentException("Could not recognize urgency");
        }

    }

    @TypeConverter
    public static Integer toInteger(Turns.Amount status) {
        return status.getCode();
    }
}
