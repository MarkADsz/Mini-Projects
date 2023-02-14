package com.example.gui_first.Domain.Values;

import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.Type;

public class IntValue implements Value{

    int val;
    public IntValue(int v){val=v;}

    public int getVal() {return val;}

    @Override
    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }


    @Override
    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof IntValue))
            return false;
        IntValue castValue = (IntValue) anotherValue;
        return val == castValue.val;
    }
}
