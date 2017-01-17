package com.gamedesign.mario.control.pickup.mushroom;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import static com.gamedesign.mario.Config.*;

public class MushroomSpawnControl extends AbstractControl
{
    private GameEntity gameEntity;
    private Point2D origin;

    private PhysicsComponent physics()
    {
        return gameEntity.getComponentUnsafe(PhysicsComponent.class);
    }

    @Override
    public void onAdded(Entity entity)
    {
        gameEntity = (GameEntity) entity;
        origin = gameEntity.getPosition();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(gameEntity.getPosition().getY() >
                origin.getY() - BLOCK_SIZE)
            physics().setLinearVelocity(0, MUSHROOM_SPEED * -v);
    }




}
