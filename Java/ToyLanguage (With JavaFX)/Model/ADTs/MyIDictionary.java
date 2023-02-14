package com.example.gui_first.Domain.ADTs;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<K,V> {
    public void addDictionary(K key, V value);

    public V lookup(K id);

    public boolean isDefined(K id);

    public void update(K id, V val);

    public void deleteDictionary(K key);

    Collection<V> values();

    Map<K, V> getContent();

    MyIDictionary<K,V> deepCopy();
}
