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
public class NewStmt implements IStmt{

    String varname;
    Exp expression;

    public NewStmt(String myvarname, Exp myexpression){
        varname=myvarname;
        expression=myexpression;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIDictionary<String, Value> symTable=state.getSymTable();
        MyIHeap heap=state.getHeap();
        if(symTable.isDefined(varname)){
            Value varvalue=symTable.lookup(varname);
            if(varvalue.getType() instanceof RefType){
                Value evaluated=expression.eval(symTable,heap);
                Type locationType=((RefValue)varvalue).getLocationType();
                if(locationType.equals(evaluated.getType())) {
                    int newPos=heap.addHeap(evaluated);
                    symTable.addDictionary(varname,new RefValue(newPos,locationType));
                    state.setSymTable(symTable);
                    state.setHeap(heap);
                }else{
                    throw new StmtException(varname+" not of +");
                }
            }else{
                throw new StmtException(varname+" not RefType");
            }
        }else{
            throw new StmtException(varname+" not in symtable");}
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varname,expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(varname);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varname, expression);
    }

}
