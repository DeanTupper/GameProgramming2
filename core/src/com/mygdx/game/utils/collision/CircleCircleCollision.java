package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.CircleCollidable;

public class CircleCircleCollision extends Collision
{
    private CircleCollidable circleA;
    private CircleCollidable circleB;

    public CircleCircleCollision(CircleCollidable a, CircleCollidable b)
    {
        super(a, b);

        circleA = a;
        circleB = b;
    }

    public void calculateTimeToCollision()
    {
        resetInitialVelocities();

        Vector2 vA = circleA.getVelocity().cpy();
        Vector2 vB = circleB.getVelocity().cpy();
        Vector2 vAB = vA.cpy().sub(vB);

        Vector2 pA = circleA.getPosition();
        Vector2 pB = circleB.getPosition();
        Vector2 pAB = pA.cpy().sub(pB);

        float a = vAB.dot(vAB);

        float pABdotvAB = pAB.dot(vAB);
        float b = 2 * pABdotvAB;

        float rAB = circleA.getRadius() + circleB.getRadius();

        float c = pAB.dot(pAB) - (rAB * rAB);

        timeToCollision = CollisionUtils.solveQuadraticEquation(a, b, c);

        willCollide = timeToCollision < Float.MAX_VALUE && timeToCollision > 0f;
        System.err.println("CircleCircleCollision::calculateTimeToCollision - timeToCollision: " + timeToCollision);
    }
}
