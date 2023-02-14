package com.example.gui_first.Controller;

import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Domain.Values.RefValue;
import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.CtrlException;
import com.example.gui_first.MyException.ExprException;
import com.example.gui_first.MyException.MyException;
import com.example.gui_first.MyException.StmtException;
import com.example.gui_first.Repository.IRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ctrl {
    IRepo r;/////////////
    ExecutorService executorService;
    public Ctrl(IRepo repo){
        r=repo;
    }


    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr,List<Integer> heapAddr, Map<Integer,Value>
            heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));}

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    public void oneStep() throws InterruptedException, ExprException, MyException, StmtException, IOException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(r.getPrgList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        //programStates = removeCompletedPrg(repository.getProgramList());
        executorService.shutdownNow();
        //repository.setProgramStates(programStates);
    }
    public void oneStepForAllPrograms(List<PrgState> programStates) throws InterruptedException, ExprException, MyException, StmtException, IOException{
        programStates.forEach(programState -> {
            try {
                r.logPrgStateExec(programState);
                //display(programState);
            } catch (IOException | MyException  | NullPointerException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });
        ///run concurrently one step for each of the existing PrgStates
        List<Callable<PrgState>> callList = programStates.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newProgramList = executorService.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (ExecutionException | InterruptedException e) {
                        System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                    }
                    return null;//////////////////////////////////////////
                })
                .filter(p->p!=null)
                .collect(Collectors.toList());

        Set<PrgState> set = new HashSet<>(); //
        set.addAll(programStates);
        set.addAll(newProgramList);
        programStates.clear();
        programStates.addAll(set);
        //programStates.addAll(newProgramList);

        programStates.forEach(programState -> {
            try {
                r.logPrgStateExec(programState);
            } catch (IOException | MyException e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
            }
        });

        r.setPrgList(programStates);
    }


    public void allStep() throws CtrlException, ExprException, StmtException, MyException, IOException, InterruptedException {
        executorService = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(r.getPrgList());
        while (programStates.size() > 0) {
            conservativeGarbageCollector(programStates);
            oneStepForAllPrograms(programStates);
            programStates = removeCompletedPrg(r.getPrgList());
        }
        executorService.shutdownNow();
        r.setPrgList(programStates);
    }

    private void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        programStates.forEach(p -> {
            p.getHeap().setContentHeap((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }
//    public void display(){
//        PrgState prg = r.getCrtPrg();
//        prg.toString();
//    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }
    public List<PrgState> getPrgStates(){
        return r.getPrgList();
    }

    public void setPrgStates(List<PrgState> newstates){
        r.setPrgList(newstates);
    }
}
