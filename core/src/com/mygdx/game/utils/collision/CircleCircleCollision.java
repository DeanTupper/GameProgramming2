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

            willCollide = timeToCollision < Float.MAX_VALUE;

            System.err.println("CircleCircleCollision::calculateTimeToCollision - timeToCollision: " + timeToCollision);
        }
    }
}
