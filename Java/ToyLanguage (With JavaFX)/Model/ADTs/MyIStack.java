package com.example.gui_first.Domain.ADTs;

public interface MyIStack<T>{
    public T pop();
    public void push(T elem);

    public boolean isEmpty();
}
