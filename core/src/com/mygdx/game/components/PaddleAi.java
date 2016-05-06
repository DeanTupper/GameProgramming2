package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.subsystems.QuadSubsystem;

import java.util.Set;

public class PaddleAi implements Component
{

    private final AiPaddleMovable movable;
    private final float width;
    private final float height;
    private final boolean horizontal;
    private final int player;

    private Vector2 position;

    private Boolean positive;
    private Boolean negative;

    private Collidable currentTarget = null;

    public PaddleAi(Vector2 position, Boolean positive, Boolean negative, AiPaddleMovable movable, float width, float height, int player)
    {
        this.position = position;
        this.positive = positive;
        this.negative = negative;
        this.movable = movable;
        this.width = width;
        this.height = height;
        this.horizontal = width > height;
        this.player = player;

    }

    public void think(Set<Ball> balls)
    {
        if (currentTarget == null || !targetStillInRange())
        {
            findTarget();
        }
        if (currentTarget != null)
        {
            if(this.horizontal)
            {
                calcMovement(position.x,width,currentTarget.getPosition().x);
            }
            else
            {
                calcMovement(position.y,height,currentTarget.getPosition().y);
            }
        }
        else
        {
            if(horizontal){
                seekCenter(position.x, width);
            }
            else{
                seekCenter(position.y, height);
            }

            System.out.println("target is null");
        }
    }

    private void calcMovement(float paddlePosition, float offset, float ballPosition)
    {
        if (paddlePosition + offset / 2 < ballPosition)
        {
            System.out.println("up");
            movable.setPositive(true);
            movable.setNegative(false);
        }
        else if (paddlePosition + offset / 2 > ballPosition)
        {
            System.out.println("down");
            movable.setPositive(false);
            movable.setNegative(true);
        }
        else
        {
            System.out.println("stay");
            movable.setPositive(false);
            movable.setNegative(false);
        }
    }

    private void seekCenter(float paddlePosition, float offset)
    {

        if (paddlePosition > 50 - offset / 2)
        {
            System.out.println("up");
            movable.setPositive(false);
            movable.setNegative(true);
        }
        else if (paddlePosition < 50 - offset / 2)
        {
            System.out.println("down");
            movable.setPositive(true);
            movable.setNegative(false);
        }
        else
        {
            System.out.println("stay");
            movable.setPositive(false);
            movable.setNegative(false);
        }
    }

    private void findTarget()
    {
        System.out.println("find target");
        Set<Collidable> targets = QuadSubsystem.get().getGoalCollidable(player);
        currentTarget = null;
        System.out.println("targets = " + targets);
        for (Collidable target : targets)
        {
            if (target instanceof BallCollidable)
            {
                System.out.println("found target");
                currentTarget = target;
            }
        }

    }

    private boolean targetStillInRange()
    {
        return QuadSubsystem.get().get().getGoalCollidable(player).contains(currentTarget);
    }

    private Vector2 calcPursuit(Vector2 desiredPos, Vector2 velocity)
    {
        Vector2 distance = desiredPos.sub(currentTarget.getPosition()    );
        float thing = (distance.len() / 100);
        Vector2 futurePosition = desiredPos.add(velocity.scl(thing));
        return calcSeek(futurePosition);
    }

    private Vector2 calcSeek(Vector2 desiredPos) {
        return (desiredPos.sub(this.position.cpy())).nor().sub(this.movable.getVelocity().cpy());
    }


}
