package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        return null;
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
