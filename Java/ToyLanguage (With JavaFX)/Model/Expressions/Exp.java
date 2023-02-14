package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public interface Exp {
    Value eval(MyIDictionary<String,Value> tbl,MyIHeap heap) throws ExprException, MyException;
    Exp deepCopy();

    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
}
