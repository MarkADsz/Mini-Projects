package com.example.gui_first.Domain.Types;

import com.example.gui_first.Domain.Values.StringValue;
import com.example.gui_first.Domain.Values.Value;

public class StringType implements Type{

    public boolean equals(Object another){
        if (another instanceof StringType)
            return true;
        else
            return false;
    }
    @Override
    public String toString() { return "String";}
    @Override
    public Value defaultValue() {
        return new StringValue(" ");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }
}
