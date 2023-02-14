package com.example.gui_first.Domain;

import com.example.gui_first.Domain.ADTs.*;
import com.example.gui_first.Domain.Stmt.IStmt;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

import java.io.BufferedReader;

public class PrgState {
        MyIStack<IStmt> exeStack;
        MyIDictionary<String, Value> symTable;
        MyIList<Value> out;

        MyIHeap heap;
        MyIDictionary<String, BufferedReader> fileTable;

        ILatchTable latchTable;
        IStmt originalProgram; //optional field, but good to have

        int myid;
        static int lastId=0;
        public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value>
                ot,MyIDictionary<String, BufferedReader> myfileTable, MyIHeap myheap,ILatchTable ltable,IStmt prg){
            exeStack=stk;
            symTable=symtbl;
            out = ot;
            fileTable=myfileTable;
            heap=myheap;
            latchTable=ltable;
            this.originalProgram=prg.deepCopy();
            this.exeStack.push(this.originalProgram);
            myid=setId();
            //originalProgram=deepCopy(prg);//recreate the entire original prg
            //stk.push(prg);
        }
        public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value>
            ot,MyIDictionary<String, BufferedReader> myfileTable, MyIHeap myheap,ILatchTable ltable){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable=myfileTable;
        heap=myheap;
        latchTable=ltable;
        myid=setId();
        //originalProgram=deepCopy(prg);//recreate the entire original prg
        //stk.push(prg);
    }

    public IStmt deepCopy(IStmt prg) {
        return null;
    }

    public MyIStack<IStmt> getStk() {
            return exeStack;
    }

    public void setStk(MyIStack<IStmt> newstack) {
        exeStack=newstack;
    }

    public MyIDictionary<String,Value> getSymTable() {
            return symTable;
    }

    public void setSymTable(MyIDictionary<String,Value> newsymTable) {
        symTable=newsymTable;
    }

    public MyIList<Value> getList(){
            return out;
    }

    public void setList(MyIList<Value> newList){
        out=newList;
    }

    public MyIDictionary<String, BufferedReader> getFileTable(){
            return fileTable;
    }

    public void setFileTable(MyIDictionary<String,BufferedReader> newftable){
            fileTable=newftable;
    }

    public MyIHeap getHeap(){
            return heap;
    }

    public void setHeap(MyIHeap newheap){
            heap=newheap;
    }

    public ILatchTable getLatchTable(){
            return latchTable;
    }

    public void setLatchTable(ILatchTable lt){
            latchTable=lt;
    }


    //
    public boolean isNotCompleted() {
        return exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, ExprException, StmtException {
        if(exeStack.isEmpty())
        throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public int getMyid(){
            return myid;
    }

    @Override
    public String toString(){
        return "ID:"+Integer.toString(myid)+"\nExecution stack: \n"+ exeStack.toString()+"\n Symbol Table: \n"+symTable.toString()+"\n Output List: \n"+out.toString()+"\n File Table: \n"+fileTable.toString()+"\n Heap: \n"+heap.toString()+"\n LatchTable: \n"+latchTable.toString();
    }

//    public PrgState oneStep(PrgState state) throws CtrlException, ExprException, StmtException, MyException {
//        MyIStack<IStmt> stk=state.getStk();
//        if(stk.isEmpty())
//            throw new CtrlException("prgstate stack is empty");
//        IStmt crtStmt = stk.pop();
//
//        return crtStmt.execute(state);
//    }

}
