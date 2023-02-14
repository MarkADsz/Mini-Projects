package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public class VarExp implements Exp{
    String id;
    public VarExp(String iid){
        id=iid;
    }
    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap heap) throws ExprException {
        return tbl.lookup(id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString(){
        return id;
    }
}
