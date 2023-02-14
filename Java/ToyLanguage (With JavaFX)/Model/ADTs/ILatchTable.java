package com.example.gui_first.Domain.ADTs;

import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.MyException;

import java.util.HashMap;
import java.util.Set;

public interface ILatchTable {
    int getFreeValue();
    HashMap<Integer, Integer> getContent();
    void setContentTable(HashMap<Integer, Integer> newMap);
    int addTable(Integer value);
    void updateTable(Integer position, Integer value) throws MyException;
    Integer getTablePosition(Integer position) throws MyException;
    boolean containsKey(Integer position);
    void removeTable(Integer key) throws MyException;
    Set<Integer> keySet();
}
