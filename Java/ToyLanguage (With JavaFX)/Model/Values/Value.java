package com.example.gui_first.Domain.Values;

import com.example.gui_first.Domain.Types.Type;

public interface Value {
    public Type getType();
    Value deepCopy();
}
