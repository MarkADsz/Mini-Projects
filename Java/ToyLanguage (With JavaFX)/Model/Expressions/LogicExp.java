package com.example.gui_first.Domain.Expressions;
import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.BoolValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    int op; //1-and 2-or
    LogicExp(Exp a, Exp b, int ope){
        op=ope;
        e1=a;
        e2=b;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExprException, MyException {
        Value v1,v2;
        v1= e1.eval(tbl,heap );
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,heap );
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op==1)
                {
                    if(n1 && n2 )
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);
                }
                else if (op==2){
                    if(n1 || n2 )
                        return new BoolValue(true);
                    else
                        return new BoolValue(false);
                }
            }else
                throw new ExprException("second operand is not bool");
        }else
            throw new ExprException("first operand is not bool");
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(),e2.deepCopy(),op);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType())){
            if(typ2.equals(new BoolType())){
                return new BoolType();
            }else
                throw new MyException("Type chekcer:Second operand not bool");
        }else
            throw new MyException("Type chekcer:First operand not bool");

    }

    @Override
    public String toString(){
        String operation;
        if(op==1)
            operation="and";
        else
            operation="or";
        return e1.toString()+" "+operation+" "+e2.toString();
    }
}
