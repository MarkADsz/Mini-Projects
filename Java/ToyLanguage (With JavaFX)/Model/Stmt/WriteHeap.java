package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.RefType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.RefValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class WriteHeap implements IStmt{
    String varname;
    Exp expression;

    public WriteHeap(String myvarname,Exp myexpression){
        varname=myvarname;
        expression=myexpression;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varname))
            throw new StmtException(String.format("%s not present in the symTable", varname));
        Value value = symTable.lookup(varname);
        if (!(value instanceof RefValue))
            throw new StmtException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        Value evaluated = expression.eval(symTable, heap);
        if (!evaluated.getType().equals(refValue.getLocationType()))
            throw new StmtException(String.format("%s not of %s", evaluated, refValue.getType()));
        heap.updateHeap(refValue.getAddr(), evaluated);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(varname, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varname);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("Write heap stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varname, expression);
    }
}
