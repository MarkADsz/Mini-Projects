package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.ILatchTable;
import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.IntValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class newLatch implements IStmt{

    String var;
    Exp ex;

    Lock myLock=new ReentrantLock();

    public newLatch(String v,Exp e){
        var=v;
        ex=e;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        myLock.lock();
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap myheap=state.getHeap();

        Value num1=ex.eval(symTbl,myheap);
        if(num1.getType().equals(new IntType())){
            ILatchTable latchTable=state.getLatchTable();
            IntValue num1iv=(IntValue) num1;
            int num1v=num1iv.getVal();
            int newfreeloc=latchTable.addTable(num1v);
            state.setLatchTable(latchTable);

            if(symTbl.isDefined(var)){
                symTbl.update(var,new IntValue(newfreeloc));
                state.setSymTable(symTbl);
            }else throw new StmtException("Var not in symtable");

        }else throw new StmtException("num1 not int");
        myLock.unlock();

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new newLatch(var,ex.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType()) && ex.typecheck(typeEnv).equals(new IntType())){
            return typeEnv;
        }else throw new MyException("newLatch:Variable and expression not Int");


    }
    public String toString(){
        return "newLatch("+var+","+ex.toString()+").";
    }
}
