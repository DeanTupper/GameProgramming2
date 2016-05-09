package com.mygdx.game.subsystems;

import com.mygdx.game.utils.UpdateDelta;

public interface Subsystem
{
    void update(long deltaInMillis, UpdateDelta updateDelta);
}
