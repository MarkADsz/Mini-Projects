package com.example.gui_first.Domain.Values;

import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.Type;


public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v){val=v;}

    public boolean getVal() {return val;}
    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) anotherValue;
        return val == castValue.val;
    }
}
