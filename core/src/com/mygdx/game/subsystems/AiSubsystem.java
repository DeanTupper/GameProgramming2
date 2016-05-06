package com.mygdx.game.subsystems;

import com.mygdx.game.components.PaddleAi;
import com.mygdx.game.entities.Ball;

import java.util.HashSet;
import java.util.Set;

public class AiSubsystem implements Subsystem
{
    private static AiSubsystem instance;
    private Set<Ball> balls = new HashSet<Ball>();
    private Set<PaddleAi> cpus = new HashSet<PaddleAi>();

    public static AiSubsystem get()
    {
        if (instance == null)
        {
            instance = new AiSubsystem();
        }

        return instance;
    }

    private AiSubsystem()
    {
    }

    @Override
    public void update(long deltaInMillis)
    {
        for(PaddleAi cpu: cpus)
        {
            cpu.think(balls);
        }
    }

    public void registerAI(PaddleAi ai)
    {
        this.cpus.add(ai);
    }

    public void registerBall(Ball ball)
    {
        balls.add(ball);
    }
}
