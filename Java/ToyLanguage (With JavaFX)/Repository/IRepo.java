package com.example.gui_first.Repository;

import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.MyException.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepo {
    public List<PrgState> getPrgList();

    public void setPrgList(List<PrgState> newlist);
    void logPrgStateExec(PrgState programstate) throws MyException, IOException;
}
