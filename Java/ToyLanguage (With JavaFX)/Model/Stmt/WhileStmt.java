package com.example.gui_first.Domain.Stmt;

import com.example.gui_first.Domain.ADTs.MyIDictionary;
import com.example.gui_first.Domain.ADTs.MyIStack;
import com.example.gui_first.Domain.Expressions.Exp;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Types.BoolType;
import com.example.gui_first.Domain.Types.Type;
import com.example.gui_first.Domain.Values.BoolValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;

public class WhileStmt implements IStmt{

    Exp expression;
    IStmt statement;

    public WhileStmt(Exp myexp, IStmt mystmt){
        expression=myexp;
        statement=mystmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException, MyException {
        Value value = expression.eval(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getStk();
        if (!value.getType().equals(new BoolType()))
            throw new StmtException(String.format("%s is not of BoolType", value));
        if (!(value instanceof BoolValue))
            throw new StmtException(String.format("%s is not a BoolValue", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            stack.push(this.deepCopy());
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=expression.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool"+ typexp.toString());
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }
}