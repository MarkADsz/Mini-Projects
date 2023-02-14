package com.example.gui_first.Domain.Values;

import com.example.gui_first.Domain.Types.StringType;
import com.example.gui_first.Domain.Types.Type;

public class StringValue implements Value{

    String val;

    public StringValue(String v){val=v;}

    public String getVal() {return val;}

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof StringValue))
            return false;
        StringValue castValue = (StringValue) anotherValue;
        return val.equals(castValue.val);
    }
}
