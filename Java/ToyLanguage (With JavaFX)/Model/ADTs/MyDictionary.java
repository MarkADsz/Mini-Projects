package com.example.gui_first.Domain.ADTs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {

    HashMap<K,V> mydict=new HashMap<K,V>();
    @Override
    public void addDictionary(K key, V value) {
        synchronized (this){
            mydict.put(key,value);
        }

    }
    @Override
    public V lookup(K id){
        return mydict.get(id);
    }

    @Override
    public boolean isDefined(K id){
        if(mydict.get(id)==null)
            return false;
        else
            return true;
    }

    @Override
    public void update(K id, V val){
        mydict.put(id,val);
    }

    @Override
    public void deleteDictionary(K key) {
        mydict.remove(key);
    }

    @Override
    public Collection<V> values() {
        return mydict.values();
    }

    @Override
    public Map<K, V> getContent() {
        synchronized (this) {
            return mydict;
        }
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        MyIDictionary<K, V> copydict = new MyDictionary<>();
        for (K key: getContent().keySet())
            copydict.addDictionary(key, this.lookup(key));
        return copydict;
        //return (MyIDictionary<K, V>) mydict.clone();
    }

    public void setContent(Map<K, V> elems) {
        mydict= (HashMap<K, V>) elems;
    }

    @Override
    public String toString(){
        return mydict.toString();
    }
}
