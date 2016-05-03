package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Collidable;

public abstract class Collision
{
    public final Collidable a;
    public final Collidable b;
    public final long deltaInMillis;

    public boolean willCollide = false;
    public boolean collidesThisUpdate = false;

    public Vector2 closestPointOnA = new Vector2();
    public Vector2 closestPointOnB = new Vector2();

    public float timeToCollisionX = Float.MAX_VALUE;
    public float timeToCollisionY = Float.MAX_VALUE;

    public Collision(Collidable a, Collidable b, long deltaInMillis)
    {
        this.a = a;
        this.b = b;
        this.deltaInMillis = deltaInMillis;
    }

    public boolean isOf(Collidable a, Collidable b)
    {
        return (a.equals(this.a) && b.equals(this.b))
                || (a.equals(this.b) && b.equals((this.a)));
    }
}
