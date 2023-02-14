package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyDictionary;
import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.ADTs.MyStack;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

import java.util.Map;

public class ForkStmt implements IStmt{

    IStmt statement;

    public ForkStmt(IStmt mystatement) {
        statement = mystatement;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
//        MyIStack<IStmt> newStack = new MyStack<>();
//        newStack.push(statement);
//        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
//        for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet()) {
//            newSymTable.addDictionary(entry.getKey(), entry.getValue().deepcopy());
//        }
//
//        return new PrgState(newStack, newSymTable, state.getList(), state.getFileTable(), state.getHeap());
        MyDictionary<String,Value> temporarySymTable= new MyDictionary<>();
        for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet()) {
            temporarySymTable.addDictionary(entry.getKey(), entry.getValue());
        }
        //temporarySymTable.setContent(state.getSymTable().getContent());
        MyIStack<IStmt> thisStack= new MyStack<IStmt>();
        return new PrgState(thisStack,
                temporarySymTable,
                state.getList(),
                state.getFileTable(),
                state.getHeap(),state.getLatchTable(),statement);
    }
    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
