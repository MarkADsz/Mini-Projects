package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIList;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;
public class PrintStmt implements IStmt{
    Exp exp;
    public PrintStmt(Exp myexp){
        exp=myexp;
    }
    @Override
    public String toString(){ return "print(" +exp.toString()+")";}

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIList<Value> out= state.getList();
        MyIHeap myheap=state.getHeap();
        out.addList(exp.eval(symTbl, myheap));
        state.setList(out);
        return state;

    }

}
