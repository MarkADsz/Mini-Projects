package com.example.gui_first.Domain.Types;

import com.example.gui_first.Domain.Values.RefValue;
import com.example.gui_first.Domain.Values.Value;

public class RefType implements Type{

    Type inner;
    public RefType(Type myinner){
        inner=myinner;
    }

    public Type getInner(){
        return inner;
    }
    public boolean equals(Object another){
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }
    @Override
    public String toString(){
        return "Ref(" +inner.toString()+")";
    }

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
