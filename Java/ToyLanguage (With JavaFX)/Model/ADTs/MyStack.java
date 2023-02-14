package com.example.gui_first.Domain.ADTs;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{

    Stack<T> mystack=new Stack<>();
    @Override
    public T pop() {
        return mystack.pop();
    }

    @Override
    public void push(T elem) {
        mystack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return mystack.isEmpty();
    }

    @Override
    public String toString(){
        return mystack.toString();
    }

}
