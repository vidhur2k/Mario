package com.gamedesign.mario.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gamedesign.mario.component.SubTypeComponent;
import com.gamedesign.mario.event.PickupEvent;
import com.gamedesign.mario.event.PlatformEvent;
import com.gamedesign.mario.type.EntityType;
import com.gamedesign.mario.type.PlatformType;

public class PlayerPlatformHandler extends CollisionHandler
{
    public PlayerPlatformHandler()
    {
        super(EntityType.PLAYER, EntityType.PLATFORM);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity platform)
    {
        PlatformType platformType = (PlatformType) platform.
                getComponentUnsafe(SubTypeComponent.class).getValue();

        switch(platformType)
        {
            case QUESTION_BLOCK:
                FXGL.getEventBus()
                        .fireEvent(new PlatformEvent(PlatformEvent.QUESTION_BLOCK, platform));
        }
    }
}











