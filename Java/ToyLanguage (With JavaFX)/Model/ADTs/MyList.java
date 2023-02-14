package com.example.gui_first.Domain.ADTs;

import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T>{

    Vector<T> mylist=new Vector<T>();

    @Override
    public void addList(T elem) {
        mylist.add(elem);
    }

    @Override
    public String toString(){
        return mylist.toString();
    }

    public void setContent(List<T> elems) {
        mylist= (Vector<T>) elems;
    }

    public Vector<T> getList(){
        return mylist;
    }
}
