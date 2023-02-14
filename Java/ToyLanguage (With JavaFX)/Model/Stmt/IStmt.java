package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;
public interface IStmt {
    PrgState execute(PrgState state) throws StmtException, ExprException, MyException;

    @Override
    public String toString();

    IStmt deepCopy();

    MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;

}
