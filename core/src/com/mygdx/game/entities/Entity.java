package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity implements ShapeRenderableEntity
{
    protected Vector2 position = new Vector2();
    protected Vector2 velocity = new Vector2();

    public abstract void draw(ShapeRenderer renderer);
    public abstract void update(long deltaInMillis);
}
