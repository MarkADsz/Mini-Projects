package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.RefType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.RefValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public class ReadHeap implements Exp{
    Exp expression;

    public ReadHeap(Exp myExp){
        expression=myExp;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExprException, MyException {
        Value value = expression.eval(tbl, heap);
        if (!(value instanceof RefValue))
            throw new ExprException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        return heap.getHeapPosition(refValue.getAddr());
    }

    @Override
    public Exp deepCopy() {
        return new ReadHeap(expression.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=expression.typecheck(typeEnv);
        if(typ instanceof RefType){
            RefType refT= (RefType) typ;
            return refT.getInner();

        }else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", expression);
    }
}
