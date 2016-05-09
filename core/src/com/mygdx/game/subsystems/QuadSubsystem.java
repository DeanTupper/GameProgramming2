package com.mygdx.game.subsystems;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.collidables.Collidable;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.QuadMap;
import com.mygdx.game.utils.UpdateDelta;

import java.util.HashSet;
import java.util.Set;

public class QuadSubsystem implements Subsystem
{
    private final Set<Quad> p1Goal;
    private final Set<Quad> p2Goal;
    private final Set<Quad> p3Goal;
    private final Set<Quad> p4Goal;

    private static QuadSubsystem instance;
    private final QuadMap quadmap;

    private QuadSubsystem()
    {
        quadmap = new QuadMap();
        p1Goal = getGoalQuads(0,0,10,3);
        p2Goal = getGoalQuads(7,0,10,10);
        p3Goal = getGoalQuads(0,7,10,10);
        p4Goal = getGoalQuads(0,0,3,10);
    }

    public static QuadSubsystem get()
    {
        if (instance == null)
        {
            instance = new QuadSubsystem();
        }

        return instance;
    }

    private Set<Quad> getGoalQuads(int xStart, int yStart, int xEnd, int yEnd)
    {
        Set<Quad> quadList = new HashSet<Quad>();
        for (float x = xStart; x < xEnd; x++)
        {
            for (float y = yStart; y < yEnd; y++)
            {
                quadList.add(quadmap.getQuad(x*10,y*10));
            }
        }
        return quadList;
    }

    public Quad getQuad(Vector2 position)
    {
        return quadmap.getQuad(position);
    }

    @Override
    public void update(long deltaInMillis, UpdateDelta updateDelta)
    {
        Set<Collidable> collidablesInWorld = CollidableSubsystem.get().getCollidables();
        quadmap.update(collidablesInWorld);
    }

    public void render(ShapeRenderer renderer)
    {
        quadmap.render(renderer);
    }

//    public Set<Collidable> getGoalLineCollidables(int player)
//    {
//        Set<Collidable> collidables = new HashSet<Collidable>();
//        if (player == 0)
//        {
//            for (float x = 0; x < 10; x++)
//            {
//                for (float y = 0; y < 3; y++)
//                {
//                    collidables.addAll(quadmap.getQuad(x*10, y*10).collidables);
//                }
//            }
//        }
//        else if(player == 1){
//            for (float x = 7; x < 10; x++)
//            {
//                for (float y = 0; y < 10; y++)
//                {
//                    collidables.addAll(quadmap.getQuad(x*10, y*10).collidables);
//                }
//            }
//        }
//        else if(player == 2){
//            for (float x = 0; x < 10; x++)
//            {
//                for (float y = 7; y < 10; y++)
//                {
//                    collidables.addAll(quadmap.getQuad(x*10, y*10).collidables);
//                }
//            }
//        }
//        else{
//            for (float x = 0; x < 3; x++)
//            {
//                for (float y = 0; y < 10; y++)
//                {
//                    collidables.addAll(quadmap.getQuad(x*10, y*10).collidables);
//                }
//            }
//        }
//
//        if(!collidables.isEmpty())
//        {
//            System.err.println(collidables);
//        }
//        return collidables;
//    }

    public Set<Quad> getGoalQuad(int player)
    {
        Set<Quad> returnedSet;
        switch (player)
        {
            case 1:
            {
                returnedSet = p1Goal;
                break;
            }
            case 2:
            {
                returnedSet = p2Goal;
                break;
            }
            case 3:
            {
                returnedSet = p3Goal;
                break;
            }
            case 4:
            {
                returnedSet = p4Goal;
                break;
            }
            default:
            {
                throw new AssertionError("Received incorrect player number. Received: " + player);
            }
        }
        return returnedSet;
    }

    public Set<Collidable> getGoalCollidable(int player)
    {
        Set<Collidable> collidables = new HashSet<Collidable>();
        for(Quad current:getGoalQuad(player))
        {
            collidables.addAll(current.collidables);
        }
        return collidables;
    }
}
