package com.example.gui_first.Domain.Expressions;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.BoolValue;
import com.example.gui_first.Domain.Values.IntValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

import java.util.Objects;

public class RelationalExpression implements Exp{

    Exp expression1;
    Exp expression2;
    String operator;
    public RelationalExpression(String op, Exp expr1, Exp expr2){
        expression1=expr1;
        expression2=expr2;
        operator=op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExprException, MyException {
        Value value1, value2;
        value1 = this.expression1.eval(tbl,heap );
        if (value1.getType().equals(new IntType())) {
            value2 = this.expression2.eval(tbl,heap );
            if (value2.getType().equals(new IntType())) {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getVal();
                v2 = val2.getVal();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            } else
                throw new ExprException("Second operand is not an integer.");
        } else
            throw new ExprException("First operand in not an integer.");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExpression(operator,expression1.deepCopy(),expression2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=expression1.typecheck(typeEnv);
        typ2=expression2.typecheck(typeEnv);
        if(typ1.equals(new IntType())){
            if(typ2.equals(new IntType())){
                return new BoolType();
            }else
                throw new MyException("Type chekcer relational:Second operand not int");
        }else
            throw new MyException("Type chekcer relational:First operand not int");

    }
    @Override
    public String toString() {
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }
}
