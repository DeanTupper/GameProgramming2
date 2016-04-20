package com.mygdx.game.entities;

import com.mygdx.game.components.Component;

import java.util.HashSet;
import java.util.Set;

public abstract class Entity2
{
    protected final Set<Component> components = new HashSet<Component>();

    void remove(Component component)
    {
        components.remove(component);
    }
}
