package com.example.gui_first.Domain.ADTs;

import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.MyException;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int getFreeValue();
    HashMap<Integer, Value> getContent();
    void setContentHeap(HashMap<Integer, Value> newMap);
    int addHeap(Value value);
    void updateHeap(Integer position, Value value) throws MyException;
    Value getHeapPosition(Integer position) throws MyException;
    boolean containsKey(Integer position);
    void removeHeap(Integer key) throws MyException;
    Set<Integer> keySet();

}
