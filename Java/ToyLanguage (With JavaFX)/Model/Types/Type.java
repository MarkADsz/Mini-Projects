package com.example.gui_first.Domain.Types;

import com.example.gui_first.Domain.Values.Value;

public interface Type {
    public Value defaultValue();
    Type deepCopy();
}
