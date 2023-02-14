package com.example.gui_first.Domain.Values;

import com.example.gui_first.Domain.Types.RefType;
import com.example.gui_first.Domain.Types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;
    public RefValue(int myaddress,Type mylocation){
        address=myaddress;
        locationType=mylocation;
    }
    public int getAddr() {return address;}
    public Type getType()
    { return new RefType(locationType);}
    public Type getLocationType(){
        return locationType;
    }
    @Override
    public Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }
    @Override
    public String toString(){
        return "refval("+Integer.toString(address)+":"+locationType.toString()+")";
    }
}
