package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.BoolValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el) {exp=e; thenS=t;elseS=el;}

    @Override
    public String toString(){ return "(IF("+ exp.toString()+") THEN(" +thenS.toString()
            +")ELSE("+elseS.toString()+"))";}

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(),thenS.deepCopy(),elseS.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }


    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap myheap=state.getHeap();
        Value Cond=exp.eval(symTbl,myheap );
        if (Cond.getType().equals(new BoolType())) //instanceof BoolValue myCond) ///////////////////////////////////////////
        {
            BoolValue myCond=(BoolValue) Cond;
            IStmt myStatement;
            if(myCond.getVal())
            {
                myStatement=thenS;
            }
            else {
                myStatement=elseS;
            }
            stk.push(myStatement);
            state.setStk(stk);
            return state;

        }else {
            throw new StmtException("Need boolean expression in an if statement");
        }

    }

}
