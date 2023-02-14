package com.example.gui_first.Domain.ADTs;

import com.example.gui_first.Domain.Values.Value;
import com.example.gui_first.MyException.MyException;

import java.util.HashMap;
import java.util.Set;

public class MyHeap implements MyIHeap{
    HashMap<Integer, Value> heap;
    int freeLocationValue;

    public int newValue() {
        freeLocationValue += 1;
        while (freeLocationValue == 0 || heap.containsKey(freeLocationValue))
            freeLocationValue += 1;
        return freeLocationValue;
    }

    public MyHeap() {
        this.heap = new HashMap<>();
        freeLocationValue = 1;
    }

    @Override
    public int getFreeValue() {
        return freeLocationValue;
    }

    @Override
    public HashMap<Integer, Value> getContent() {/////////////////////////////////////////////////////////
        return heap;
    }

    @Override
    public void setContentHeap(HashMap<Integer, Value> newMap) {
        this.heap = newMap;
    }

    @Override
    public int addHeap(Value value) {
        heap.put(freeLocationValue, value);
        Integer toReturn = freeLocationValue;
        freeLocationValue = newValue();
        return toReturn;
    }

    @Override
    public void updateHeap(Integer position, Value value) throws MyException  {
        if (!heap.containsKey(position))
            throw new MyException (String.format("%d is not present in the heap", position));
        heap.put(position, value);
    }

    @Override
    public Value getHeapPosition(Integer position) throws MyException {
        if (!heap.containsKey(position))
            throw new MyException (String.format("%d is not present in the heap", position));
        return heap.get(position);
    }

    @Override
    public boolean containsKey(Integer position) {
        return this.heap.containsKey(position);
    }

    @Override
    public void removeHeap(Integer key) throws MyException {
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
