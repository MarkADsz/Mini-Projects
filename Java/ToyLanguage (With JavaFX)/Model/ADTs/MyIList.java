package com.example.gui_first.Domain.ADTs;

import java.util.Vector;

public interface MyIList<T>{
    public void addList(T elem);
    public Vector<T> getList();
}
