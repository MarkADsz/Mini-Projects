package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIStack;
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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt {

    Exp myExp;
    public OpenRFile(Exp expression) {
        myExp=expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap myheap=state.getHeap();
        Value myVal = myExp.eval(symTbl,myheap );

        if (myVal.getType().equals(new StringType())) {
            StringValue fname = (StringValue) myVal;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(fname.getVal())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fname.getVal()));
                } catch (FileNotFoundException e) {
                    throw new StmtException(String.format("%s could not be opened", fname.getVal()));
                }
                fileTable.addDictionary(fname.getVal(), br);
                state.setFileTable(fileTable);


            } else
                throw new StmtException("Already defined(file)");

        } else
            throw new StmtException("Not a String(file)");

        return state;
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile "+ myExp.toString());
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(myExp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        myExp.typecheck(typeEnv);
        return typeEnv;
    }
}
