package com.example.gui_first.Repository;

import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.MyException.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo{
    //Vector<PrgState> programStates;
    List<PrgState> programStates;
    String myFile;
    int counter;
    public Repo(PrgState state, String logFilePath){
        programStates=new ArrayList<>();
        //counter=0;
        addPrg(state);
        myFile=logFilePath;
    }

    public void addPrg(PrgState prgg) {
        programStates.add(prgg);
        //counter++;
    }

    //@Override
    //public PrgState getCrtPrg() {
      //  return programStates.elementAt(counter);
    //}

    @Override
    public List<PrgState> getPrgList() {
        return programStates;
    }

    @Override
    public void setPrgList(List<PrgState> newlist) {
        programStates=newlist;
    }

    @Override
    public void logPrgStateExec(PrgState programstate) throws MyException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(myFile, true)));
        logFile.println(programstate.toString());
        logFile.close();
    }
    public String getFile(){
        return myFile;
    }
}
