package com.example.gui_first.Domain.ADTs;

import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.MyException;

import java.util.HashMap;
import java.util.Set;

public class LatchTable implements ILatchTable{

    HashMap<Integer, Integer> heap;
    int freeLocationValue;

    public int newValue() {
        freeLocationValue += 1;
        while (freeLocationValue == 0 || heap.containsKey(freeLocationValue))
            freeLocationValue += 1;
        return freeLocationValue;
    }
    public LatchTable() {
        this.heap = new HashMap<>();
        freeLocationValue = 1;
    }
    @Override
    public int getFreeValue() {
        return freeLocationValue;
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        return heap;
    }

    @Override
    public void setContentTable(HashMap<Integer, Integer> newMap) {
        synchronized (this){
            this.heap = newMap;}
    }

    @Override
    public int addTable(Integer value) {
        synchronized (this){
            heap.put(freeLocationValue, value);
            Integer toReturn = freeLocationValue;
            freeLocationValue = newValue();
            return toReturn;
        }
    }

    @Override
    public void updateTable(Integer position, Integer value) throws MyException {
        synchronized (this){
            if (!heap.containsKey(position))
                throw new MyException (String.format("%d is not present in the heap", position));
            heap.put(position, value);
        }
    }

    @Override
    public Integer getTablePosition(Integer position) throws MyException {
        synchronized (this){
            if (!heap.containsKey(position))
                throw new MyException (String.format("%d is not present in the heap", position));
            return heap.get(position);
        }
    }

    @Override
    public boolean containsKey(Integer position) {
        synchronized (this){
            return this.heap.containsKey(position);
        }
    }

    @Override
    public void removeTable(Integer key) throws MyException {
        if (!containsKey(key))
            throw new MyException (key + " is not defined.");
        freeLocationValue = key;
        this.heap.remove(key);
    }

    @Override
    public Set<Integer> keySet() {
        return heap.keySet();
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }
}
