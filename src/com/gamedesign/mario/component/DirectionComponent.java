package com.gamedesign.mario.component;

import com.almasb.ents.component.ObjectComponent;
import com.gamedesign.mario.Direction;

public class DirectionComponent extends ObjectComponent<Direction>
{
    public DirectionComponent(Direction initialValue)
    {
        super(initialValue);
    }
}
