package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.IntValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public class ArithExp implements Exp {
    Exp e1;
    Exp e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp ee1, Exp ee2, int oop) {
        e1 = ee1;
        e2 = ee2;
        op = oop;
    }

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExprException, MyException {
        Value v1, v2;
        v1 = e1.eval(tbl,heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,heap );
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4)
                    if (n2 == 0) throw new ExprException("division by zero");
                    else return new IntValue(n1 / n2);
            } else
                throw new ExprException("second operand is not an integer");
        } else
            throw new ExprException("first operand is not an integer");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(e1.deepCopy(),e2.deepCopy(),op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new IntType();
            }else
                throw new MyException("Type chekcer:Second operand not int");
        }else
            throw new MyException("Type chekcer:First operand not int");

    }

    @Override
    public String toString(){
        String operation;
        if(op==1)
            operation="+";
        else if(op==2)
            operation="-";
        else if(op==3)
            operation="*";
        else
            operation="/";
        return e1.toString()+" "+operation+" "+e2.toString();
    }

}
