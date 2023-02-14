package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt snd;
    public CompStmt(IStmt myfirst, IStmt mysnd){
        first=myfirst;
        snd =mysnd;
    }
    @Override
    public String toString() {
        if(first==null)
            return null;
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        if(first==null)
            return null;
        return new CompStmt(first.deepCopy(),snd.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        return typEnv2;
        //return snd.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException,ExprException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(snd);
        stk.push(first);
        state.setStk(stk);
        return state;
    }

}
