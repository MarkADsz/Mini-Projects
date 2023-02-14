package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class VarDeclStmt implements IStmt {
        String name;
        Type type;

        public VarDeclStmt(String myname,Type myType){
            name=myname;
            type=myType;
        };
       @Override
        public PrgState execute(PrgState state) throws StmtException, ExprException {
           MyIStack<IStmt> stk = state.getStk();
           MyIDictionary<String, Value> symTbl = state.getSymTable();
           if( symTbl.isDefined(name)){
               throw new StmtException("Variable is arleady definde\n");
           }
           else{
               symTbl.addDictionary(name,type.defaultValue());
               state.setSymTable(symTbl);
               return state;
           }

        }
    @Override
    public String toString(){
        return name+" "+type.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name,type);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.addDictionary(name,type);
        return typeEnv;
    }
}
