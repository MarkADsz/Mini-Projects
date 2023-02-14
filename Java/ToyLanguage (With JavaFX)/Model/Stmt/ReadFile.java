package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIHeap;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.IntType;
import com.example.gui_first.Domain.Types.StringType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.IntValue;
import com.example.gui_first.Domain.Values.StringValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{

    Exp myExp;
    String varName;

    public ReadFile(Exp express,String varnamee){
        myExp=express;
        varName=varnamee;
    }
    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap myheap=state.getHeap();
        if (symTable.isDefined(varName)) {
            Value value = symTable.lookup(varName);
            if (value.getType().equals(new IntType())) {
                value = myExp.eval(symTable,myheap );
                if (value.getType().equals(new StringType())) {
                    StringValue castValue = (StringValue) value;
                    if (fileTable.isDefined(castValue.getVal())) {
                        BufferedReader br = fileTable.lookup(castValue.getVal());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.addDictionary(varName, new IntValue(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new StmtException(String.format("Could not read from file %s", castValue));
                        }
                    } else {
                        throw new StmtException(String.format("The file table does not contain %s", castValue));
                    }
                } else {
                    throw new StmtException(String.format("%s does not evaluate to StringType", value));
                }
            } else {
                throw new StmtException(String.format("%s is not of type IntType", value));
            }
        } else {
            throw new StmtException(String.format("%s is not present in the symTable.", varName));
        }
        return state;
    }
    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", myExp.toString(), varName);
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(myExp.deepCopy(),varName);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=myExp.typecheck(typeEnv);
        Type typevar=typeEnv.lookup(varName);
        if(typexp.equals(new StringType())){
            if(typevar.equals(new IntType())){
            return typeEnv;
            }else
                throw new MyException("Readfile needs int as variable parameter");
        }else
            throw new MyException("ReadFile requires string for expression");

    }
}
