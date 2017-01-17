package com.gamedesign.mario.control.mario;

import com.gamedesign.mario.Direction;

import static com.gamedesign.mario.Config.*;

public abstract class OnPlatformStateControl extends PlayerStateControl
{
    @Override
    protected void handleInput()
    {
        if(input.isHeld(JUMP_KEY))
        {
            jump(JUMPING_SPEED);
            gameEntity.removeControl(getClass());
            gameEntity.addControl(new JumpingStateControl());
        }
        else if(input.isHeld(LEFT_KEY) || input.isHeld(RIGHT_KEY))
        {
            Direction direction = input.isHeld(LEFT_KEY) ?
                    Direction.LEFT : Direction.RIGHT;

            double speed = input.isHeld(RUN_KEY) ?
                    RUNNING_SPEED : WALKING_SPEED;

            move(direction, speed);
        }
        else if(!input.isHeld(LEFT_KEY) && !input.isHeld(RIGHT_KEY))
        {
            if(physics().getLinearVelocity().getX() == 0)
            {
                gameEntity.removeControl(getClass());
                gameEntity.addControl(new StandingStateControl());
            }
        }
    }

    protected void move(Direction direction, double speed)
    {
        physics().setLinearVelocity(direction.SIGN * speed * v,
                physics().getLinearVelocity().getY());
    }

    private void jump(double speed)
    {
        physics()
                .setLinearVelocity(physics().getLinearVelocity().getX(),
                        -speed);
    }
}
