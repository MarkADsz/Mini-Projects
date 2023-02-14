package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.Expressions.RelationalExpression;
import com.example.gui_first.Domain.Expressions.ValueExp;
import com.example.gui_first.Domain.Expressions.VarExp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class ForStmt implements IStmt{

    IStmt myStatement;
    Exp exp1;
    Exp exp2;
    Exp exp3;

    public ForStmt(IStmt sm,Exp e1, Exp e2,Exp e3){
        myStatement=sm;
        exp1=e1;
        exp2=e2;
        exp3=e3;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
//        Value value1 = exp1.eval(state.getSymTable(), state.getHeap());
//        Value value2 = exp2.eval(state.getSymTable(), state.getHeap());
//        Value value3 = exp3.eval(state.getSymTable(), state.getHeap());

        MyIStack<IStmt> stack = state.getStk();
        IStmt pushable=new CompStmt(new AssignStmt("v", exp1),new WhileStmt(new RelationalExpression("<",new VarExp("v"),exp2),new CompStmt( myStatement,new AssignStmt("v",exp3))));

        stack.push(pushable);
        state.setStk(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(myStatement.deepCopy(),exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp1=exp1.typecheck(typeEnv);
        Type typexp2=exp2.typecheck(typeEnv);
        Type typexp3=exp3.typecheck(typeEnv);
        if (typexp1.equals(new IntType()) && typexp2.equals(new IntType()) && typexp3.equals(new IntType())) {
            myStatement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The conditions of FOR has not the type INT");

    }

    @Override
    public String toString(){
        return "for(v="+exp1.toString()+", v<"+exp2.toString()+", v="+exp3.toString()+") "+myStatement.toString();
    }
}
