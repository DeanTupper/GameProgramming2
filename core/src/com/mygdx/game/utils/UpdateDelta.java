package com.mygdx.game.utils;

public enum UpdateDelta
{
    FAST(1000L / 60L),
    MEDIUM(250L),
    SLOW(500L),
    SLOWER_YET(750L),
    SLOWEST(1000L);

    public final long threshold;

    UpdateDelta(long threshold)
    {
        this.threshold = threshold;
    }

    public UpdateDelta next()
    {
        int ordinalNext = ordinal() + 1;

        if (ordinalNext == UpdateDelta.values().length)
        {
            return this;
        }

        return UpdateDelta.values()[ordinalNext];
    }

    public UpdateDelta prev()
    {
        int ordinalPrev = ordinal() - 1;

        if (ordinalPrev < 0)
        {
            return this;
        }

        return UpdateDelta.values()[ordinalPrev];
    }
}

