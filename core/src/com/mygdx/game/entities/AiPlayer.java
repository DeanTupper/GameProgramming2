package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameWorld;
import com.mygdx.game.components.*;
import com.mygdx.game.components.renderables.PlayerRenderable;
import static com.mygdx.game.entities.Player.DIMEN_MAJOR;
import static com.mygdx.game.entities.Player.DIMEN_MINOR;
import static com.mygdx.game.entities.Player.POS_X_HORIZONTAL_MAX;
import static com.mygdx.game.entities.Player.POS_X_HORIZONTAL_MIN;
import static com.mygdx.game.entities.Player.POS_Y_VERITCAL_MAX;
import static com.mygdx.game.entities.Player.POS_Y_VERITCAL_MIN;
import com.mygdx.game.subsystems.AiSubsystem;
import com.mygdx.game.subsystems.MovableSubsystem;
import com.mygdx.game.subsystems.RenderSubsystem;
import com.mygdx.game.utils.Quad;
import com.mygdx.game.utils.shapes.Rectangle;

import java.util.Set;

/**
 * Created by dean on 5/5/16.
 */
public class AiPlayer extends OverallPlayer
{
    private final PaddleAi ai;
    private Boolean positive;
    private Boolean negative;

    public AiPlayer(float x, float y, Vector2 deltaVelocity , Color color, int player, Set<Quad> quads)
    {
        this(new Vector2(x, y), deltaVelocity,  color, player,quads);
    }

    public AiPlayer(Vector2 position, Vector2 deltaVelocity, Color color, int player, Set<Quad> quads)
    {
        width = DIMEN_MINOR;
        height = DIMEN_MINOR;

        this.quads = quads;

        if (deltaVelocity.x != 0f)
        {
            width = DIMEN_MAJOR;
            System.out.println("POS_X_HORIZONTAL_MAX = " + POS_X_HORIZONTAL_MAX);
            movementBounds = new Rectangle(POS_X_HORIZONTAL_MIN, position.y, POS_X_HORIZONTAL_MAX, 0f);
        }
        else
        {
            height = DIMEN_MAJOR;

            System.out.println("POS_Y_VERITCAL_MAX = " + POS_Y_VERITCAL_MAX);
            movementBounds = new Rectangle(position.x, POS_Y_VERITCAL_MIN, 0f, POS_Y_VERITCAL_MAX);
        }

        positive = false;
        negative = false;

        AiPaddleMovable aimovable = new AiPaddleMovable(position, deltaVelocity, movementBounds, positive, negative);
        this.movable = aimovable;
        MovableSubsystem.get().register(movable);

        renderable = new PlayerRenderable(position, width, height, color);
        RenderSubsystem.get().register(renderable);

        ai = new PaddleAi(position,positive,negative,aimovable,width,height,player);

        AiSubsystem.get().registerAI(ai);
    }

    @Override
    public void destroy()
    {
        MovableSubsystem.get().remove(movable);
        RenderSubsystem.get().remove(renderable);
        AiSubsystem.get().removeAI(ai);
    }
}
