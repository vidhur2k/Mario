package com.gamedesign.mario.collision;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.CollisionHandler;
import com.gamedesign.mario.component.SubTypeComponent;
import com.gamedesign.mario.event.PickupEvent;
import com.gamedesign.mario.type.EntityType;
import com.gamedesign.mario.type.PickupType;

public class PlayerPickupHandler extends CollisionHandler
{
    public PlayerPickupHandler()
    {
        super(EntityType.PLAYER, EntityType.PICKUP);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity pickup)
    {
        PickupType pickupType =
                (PickupType) pickup
                        .getComponentUnsafe(SubTypeComponent.class)
                        .getValue();

        switch(pickupType)
        {
            case MUSHROOM:
                FXGL.getEventBus()
                        .fireEvent(new PickupEvent(PickupEvent.MUSHROOM, pickup));
                break;
        }
    }
}
