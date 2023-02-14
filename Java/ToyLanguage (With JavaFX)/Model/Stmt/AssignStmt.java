package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;
public class AssignStmt implements IStmt{
    String id;
    Exp exp;
    //
    public AssignStmt(String myid, Exp myexp){
        id=myid;
        exp=myexp;
    }
    @Override
    public String toString(){ return id+"="+ exp.toString();}

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap myheap=state.getHeap();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl,myheap );
            Type typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new StmtException("declared type of variable" + id + " and type of the assigned expression do not match");
            }
        else
            {
                throw new StmtException("the used variable" + id + " was not declared before");
            }
        state.setSymTable(symTbl);
        return state;
    }
}