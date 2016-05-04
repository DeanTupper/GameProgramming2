package com.mygdx.game.utils.collision;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.CircleCollidable;

public class CircleCircleCollision extends Collision
{
    public Vector2 velocityA;
    public Vector2 velocityB;

    public Vector2 combinedVelocity;

    public Vector2 velNorA;
    public Vector2 velNorB;

    private CircleCollidable circleA;
    private CircleCollidable circleB;

    public CircleCircleCollision(CircleCollidable a, CircleCollidable b, long deltaInMillis)
    {
        super(a, b, deltaInMillis);

        circleA = a;
        circleB = b;

        velocityA = circleA.getVelocity();
        velocityB = circleB.getVelocity();
    }

    public void calculateTimeToIntersection()
    {
        Vector2 vA = circleA.getVelocity();
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

        float discriminant = b * b - (4 * a * c);

        if (discriminant < 0)
        {
            timeToCollision = Float.MAX_VALUE;
        }
        else if (discriminant == 0)
        {
            timeToCollision = -b / (2 * a);
        }
        else
        {
            discriminant = (float) Math.sqrt(discriminant);

            float a2 = a * 2;

            float t1 = (-b - discriminant) / a2;
            float t2 = (-b + discriminant) / a2;

            boolean t1Neg = t1 < 0;
            boolean t2Neg = t2 < 0;

            if (t1Neg && t2Neg)
            {
                timeToCollision = Float.MAX_VALUE;
            }
            else if (t1Neg)
            {
                timeToCollision = t2;
            }
            else if (t2Neg)
            {
                timeToCollision = t1;
            }
            else
            {
                timeToCollision = t1 < t2 ? t1 : t2;
            }

            if (timeToCollision < Float.MAX_VALUE)
            {
                willCollide = true;
            }

            System.err.println("CircleCircleCollision::calculateTimeToIntersection - timeToCollision: " + timeToCollision);
        }
    }

    public CircleCircleCollision update(long deltaInMillis)
    {
        timeToCollision -= (float) deltaInMillis / GameWorld.updateThreshold;
        System.err.println("CircleCircleCollision::update - deltaInMillis:[" + deltaInMillis + "], timeToCollision: " + timeToCollision);

        float distSq = circleA.getPosition().dst2(circleB.getPosition());
        float rSq = circleA.getRadius() + circleB.getRadius();
        rSq *= rSq;

        System.err.println("CircleCircleCollision::update - distSq: " + distSq + ", rSq: " + rSq);

        return this;
    }
}
