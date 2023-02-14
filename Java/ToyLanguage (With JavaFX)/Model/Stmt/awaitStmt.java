package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.ILatchTable;
import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.ADTs.MyIStack;
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

public class awaitStmt implements IStmt{

    String var;
    Lock myLock=new ReentrantLock();

    public awaitStmt(String var){
        this.var=var;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        myLock.lock();
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap myheap=state.getHeap();
        if(symTbl.isDefined(var) && symTbl.lookup(var).getType().equals(new IntType())){
            Value fi=symTbl.lookup(var);
            IntValue foundI=(IntValue) fi;
            int foundIndex=foundI.getVal();
            ILatchTable latchTable=state.getLatchTable();
            if(latchTable.containsKey(foundIndex)){
                if(latchTable.getTablePosition(foundIndex)==0){

                }else{
                    stk.push(this);
                    state.setStk(stk);
                }
            }else throw new StmtException("latch table doesnt contain index");
        }else throw new StmtException("Var not defined/ Var is not int");
        myLock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new awaitStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var).equals(new IntType())){
            return typeEnv;
        }else throw new MyException("Await:Type not int");
    }

    @Override
    public String toString() {
        return "await("+var+")";
    }
}
