package com.mygdx.game.subsystems;

import com.mygdx.game.components.Renderable;
import com.mygdx.game.entities.Ball;
import com.mygdx.game.entities.Pylon;
import com.mygdx.game.utils.collision.CollisionUtils;

import java.util.HashSet;
import java.util.Set;

public class PylonSubSystem implements Subsystem
{
    public static PylonSubSystem instance;

    private final Set<Pylon> pylons = new HashSet<Pylon>();
    private final Set<Ball> balls = new HashSet<Ball>();

    public static PylonSubSystem get()
    {
        if (instance == null)
        {
            instance = new PylonSubSystem();
        }
        return instance;
    }

    private PylonSubSystem()
    {
    }

    @Override
    public void update(long deltaInMillis)
    {
        for(Ball curBall: balls)
        {
            for(Pylon curPylon:pylons)
            {
                if(CollisionUtils.circleIntersectsCircle(curBall.getPosition(),curBall.getRadius(),curPylon.getPosition(),curPylon.getRadius()))
                {
                    System.out.println("GOING TO INFLUENCE");
                    curPylon.getInfluencer().influence(curBall);
                }
            }
        }
    }

    public void removePylon(Pylon pylon)
    {
        pylons.remove(pylon);
    }

    public void registerPylon(Pylon pylon)
    {
        pylons.add(pylon);
    }

    public void removeBall(Ball ball)
    {
        balls.remove(ball);
    }

    public void registerBall(Ball ball)
    {
        balls.add(ball);
    }

    public static void render(Renderable movable)
    {
    }
}
