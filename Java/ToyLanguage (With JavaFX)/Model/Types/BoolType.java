package com.example.gui_first.Domain.Types;

import com.example.gui_first.Domain.Values.BoolValue;
import com.example.gui_first.Domain.Values.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    @Override
    public String toString() { return "boolean";}

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}
