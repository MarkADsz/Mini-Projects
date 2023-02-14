package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public class ValueExp implements Exp{
    Value e;
    public ValueExp(Value a){
        e=a;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExprException{
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString(){
        return e.toString();
    }

}
