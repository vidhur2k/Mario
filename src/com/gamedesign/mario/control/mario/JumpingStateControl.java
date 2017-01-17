package com.gamedesign.mario.control.mario;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.gamedesign.mario.Direction;
import com.gamedesign.mario.type.EntityType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import static com.gamedesign.mario.Config.*;

public class JumpingStateControl extends PlayerStateControl
{
    @Override
    protected void handleInput()
    {
        if(input.isHeld(LEFT_KEY) || input.isHeld(RIGHT_KEY))
        {
            Direction direction = input.isHeld(LEFT_KEY) ?
                    Direction.LEFT : Direction.RIGHT;

            move(direction);
        }

        if(onPlatform())
        {
            gameEntity.removeControl(getClass());
            gameEntity.addControl(new RunningStateControl());
        }
    }

    private void move(Direction direction)
    {
        Point2D vel = physics().getLinearVelocity();
        physics().setLinearVelocity(direction.SIGN * JUMPING_DELTA + vel.getX(), vel.getY());
    }

    private boolean onPlatform()
    {
        double minX = bbox().getMinXWorld();
        double maxY = bbox().getMaxYWorld();

        return FXGL.getApp()
                .getGameWorld()
                .getEntitiesByType(EntityType.PLATFORM)
                .stream()
                .filter(e -> Entities.getBBox(e)
                        .isWithin(new Rectangle2D(minX + bbox().getWidth() / 3, maxY, bbox().getWidth() / 3, 5)))
                .findAny()
                .isPresent();
    }
}
