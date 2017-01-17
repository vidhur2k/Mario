package com.gamedesign.mario.control.mario;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.BoundingBoxComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.gamedesign.mario.Config;
import com.gamedesign.mario.Direction;
import com.gamedesign.mario.component.DirectionComponent;
import com.gamedesign.mario.component.MarioStateTypeComponent;
import com.gamedesign.mario.type.EntityType;
import com.gamedesign.mario.type.MarioStateType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.lang.reflect.Field;


public abstract class PlayerStateControl extends AbstractControl
{
    protected Input input;
    protected GameEntity gameEntity;
    protected String[] textures = new String[0];
    protected int i;
    protected double v;

    protected BoundingBoxComponent bbox()
    {
        return gameEntity.getBoundingBoxComponent();
    }

    protected PhysicsComponent physics()
    {
        return gameEntity.getComponentUnsafe(PhysicsComponent.class);
    }

    protected Direction direction()
    {
        return gameEntity
                .getComponentUnsafe(DirectionComponent.class)
                .getValue();
    }

    protected MarioStateType marioStateType()
    {
        return gameEntity
                .getComponentUnsafe(MarioStateTypeComponent.class)
                .getValue();
    }

    protected void updateTexture()
    {
        String var = marioStateType() + "_" + getClass().getSimpleName() + "_TEXTURES";
        try {
            Field field = Config.class.getField(var);
            textures = (String[]) field.get(new Config());
        } catch (Exception e) {}

        gameEntity.getMainViewComponent()
                .setView(handleTexture(textures[i]));
    }

    protected void updateHitBox()
    {
        switch(marioStateType())
        {
            case BIG:
                Point2D point2D = gameEntity.getBoundingBoxComponent().getCenterWorld();
                double x = point2D.getX();
                double y = point2D.getY() + 42;
                HitBox bottom = new HitBox("BOTTOM", new Point2D(x, y), BoundingShape.circle(21));
                gameEntity.getBoundingBoxComponent().addHitBox(bottom);
                break;
        }
    }

    protected ImageView handleTexture(String texture)
    {
        ImageView imageView = new ImageView(new Image("assets/textures/" + texture));

        if(direction() == Direction.LEFT)
        {
            imageView.setTranslateZ(imageView.getBoundsInLocal().getHeight() / 2.0);
            imageView.setRotationAxis(Rotate.Y_AXIS);
            imageView.setRotate(180);
        }

        return imageView;
    }

    @Override
    public void onAdded(Entity entity)
    {
        this.gameEntity = (GameEntity) entity;
        updateTexture();
        input = FXGL.getInput();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        this.v = v;
        handleInput();
        updateTexture();
        //updateHitBox();
    }

    protected abstract void handleInput();
}
