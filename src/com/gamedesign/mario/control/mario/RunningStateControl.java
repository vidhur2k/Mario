package com.gamedesign.mario.control.mario;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.time.LocalTimer;
import com.gamedesign.mario.Direction;
import com.gamedesign.mario.component.DirectionComponent;
import javafx.util.Duration;

import static com.gamedesign.mario.Config.*;

public class RunningStateControl extends OnPlatformStateControl
{
    private LocalTimer localTimer;

    @Override
    public void onAdded(Entity entity)
    {
        super.onAdded(entity);
        localTimer = FXGL.newLocalTimer();
        localTimer.capture();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        super.onUpdate(entity, v);

        double tpf = Math.min(1 / Math.abs(physics()
                .getLinearVelocity()
                .getX()) * 50000, 255);

        if(localTimer.elapsed(Duration.millis(tpf)))
        {
            i = (i + 1) % (textures.length - 1);

            physics()
                    .setLinearVelocity(physics()
                            .getLinearVelocity()
                            .multiply(SLOW_FACTOR));

            localTimer.capture();
        }
    }

    @Override
    protected void handleInput()
    {
        if(input.isHeld(LEFT_KEY) || input.isHeld(RIGHT_KEY))
        {
            Direction direction = input.isHeld(LEFT_KEY) ?
                    Direction.LEFT : Direction.RIGHT;

            if(direction != direction())
            {
                gameEntity.getComponentUnsafe(DirectionComponent.class)
                        .setValue(direction);

                i = textures.length - 1;

                localTimer.capture();
            }
        }

        super.handleInput();
    }
}
