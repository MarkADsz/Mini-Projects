package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.StringType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.StringValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{

    Exp myExp;
    public CloseRFile(Exp expression) {
        myExp=expression;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIHeap myheap=state.getHeap();
        Value value = myExp.eval(state.getSymTable(), myheap);
        if (!value.getType().equals(new StringType()))
            throw new StmtException(String.format("%s does not evaluate to StringValue", myExp));
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getVal()))
            throw new StmtException(String.format("%s is not present in the FileTable", value));
        BufferedReader br = fileTable.lookup(fileName.getVal());
        try {
            br.close();
        } catch (IOException e) {
            throw new StmtException(String.format("Unexpected error in closing %s", value));
        }
        fileTable.deleteDictionary(fileName.getVal());
        state.setFileTable(fileTable);
        return state;
    }
    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", myExp.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(myExp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        myExp.typecheck(typeEnv);
        return typeEnv;
    }
}
