package com.gamedesign.mario.control.mario;

import static com.gamedesign.mario.Config.LEFT_KEY;
import static com.gamedesign.mario.Config.RIGHT_KEY;

public class StandingStateControl extends OnPlatformStateControl
{
    @Override
    protected void handleInput()
    {
        super.handleInput();

        if(input.isHeld(LEFT_KEY) || input.isHeld(RIGHT_KEY))
        {
            gameEntity.removeControl(getClass());
            gameEntity.addControl(new RunningStateControl());
        }
    }
}
