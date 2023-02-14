package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class CondAssignStmt implements IStmt{

    String var;
    Exp exp1,exp2,exp3;

    public CondAssignStmt(String v,Exp e1,Exp e2,Exp e3){
        var=v;
        exp1=e1;
        exp2=e2;
        exp3=e3;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        IStmt pushable=new IfStmt(exp1,new AssignStmt(var,exp2),new AssignStmt(var,exp3));
        MyIStack<IStmt> stk = state.getStk();
        stk.push(pushable);
        state.setStk(stk);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignStmt(var,exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(exp1.typecheck(typeEnv).equals(new BoolType()) && typeEnv.lookup(var).equals(exp2.typecheck(typeEnv)) && exp2.typecheck(typeEnv).equals(exp3.typecheck(typeEnv))){
            return typeEnv;
        }else throw new MyException("The types in CondAssignStmt are not good");
    }

    @Override
    public String toString() {
        return var+"="+exp1.toString()+"?"+exp2.toString()+":"+exp3.toString();
    }
}
