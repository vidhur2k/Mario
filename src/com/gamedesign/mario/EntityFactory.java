package com.gamedesign.mario;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.gamedesign.mario.component.DirectionComponent;
import com.gamedesign.mario.component.MarioStateTypeComponent;
import com.gamedesign.mario.control.block.QuestionBlockControl;
import com.gamedesign.mario.control.mario.StandingStateControl;
import com.gamedesign.mario.control.pickup.mushroom.MushroomSpawnControl;
import com.gamedesign.mario.type.EntityType;
import com.gamedesign.mario.type.MarioStateType;
import com.gamedesign.mario.type.PickupType;
import com.gamedesign.mario.type.PlatformType;
import com.gamedesign.mario.component.SubTypeComponent;
import javafx.geometry.Point2D;
import org.jbox2d.dynamics.BodyType;

import static com.gamedesign.mario.Config.*;

public class EntityFactory
{
    public static GameEntity makeGenericPlatform(double x, double y)
    {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLATFORM)
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity makeBlock(double x, double y)
    {
        GameEntity gameEntity = makeGenericPlatform(x, y);
        gameEntity.setViewFromTextureWithBBox("block.png");

        PhysicsComponent physicsComponent = new PhysicsComponent();
        gameEntity.addComponent(physicsComponent);

        return gameEntity;
    }

    public static GameEntity makeQuestionBlock(double x, double y)
    {
        GameEntity gameEntity = makeGenericPlatform(x, y);
        gameEntity.addComponent(new SubTypeComponent(PlatformType.QUESTION_BLOCK));
        gameEntity.setViewFromTextureWithBBox(QUESTION_BLOCK_TEXTURES[0]);
        gameEntity.addControl(new QuestionBlockControl());

        PhysicsComponent physicsComponent = new PhysicsComponent();
        gameEntity.addComponent(physicsComponent);  // default BodyType.STATIC

        return gameEntity;
    }

    public static GameEntity makeUsedBlock(double x, double y)
    {
        GameEntity gameEntity = makeGenericPlatform(x, y);
        gameEntity.addComponent(new SubTypeComponent(PlatformType.USED_BLOCK));
        gameEntity.setViewFromTextureWithBBox(USED_BLOCK_TEXTURES[0]);
        gameEntity.addComponent(new PhysicsComponent());

        return gameEntity;
    }

    public static GameEntity makeGenericPickup(double x, double y)
    {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PICKUP)
                .with(new CollidableComponent(true))
                .build();
    }

    public static GameEntity makeMushroom(double x, double y)
    {
        GameEntity gameEntity = makeGenericPickup(x, y);
        gameEntity.setViewFromTexture(MUSHROOM_TEXTURE);
        gameEntity.addComponent(new SubTypeComponent(PickupType.MUSHROOM));

        return gameEntity;
    }

    public static GameEntity spawnMushroom(double x, double y)
    {
        GameEntity gameEntity = makeMushroom(x, y);
        gameEntity.getBoundingBoxComponent().clearHitBoxes();

        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);
        gameEntity.addComponent(physicsComponent);

        gameEntity.addControl(new MushroomSpawnControl());

        return gameEntity;
    }

    public static GameEntity makeMario(double x, double y)
    {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLAYER)
                .viewFromTexture("mario.png")
                .bbox(new HitBox("TOP", BoundingShape.circle(23)))
                .with(new CollidableComponent(true))
                .with(physicsComponent)
                .with(new DirectionComponent(Direction.RIGHT))
                .with(new MarioStateTypeComponent(MarioStateType.SMALL))
                .with(new StandingStateControl())
                .build();
    }

    public static GameEntity makeBigMario(double x, double y)
    {
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .type(EntityType.PLAYER)
                .viewFromTexture("bigMario.png")
                .bbox(new HitBox("TOP", new Point2D(0, 2), BoundingShape.circle(23)))
                .bbox(new HitBox("BOTTOM", new Point2D(0, 48), BoundingShape.circle(24)))
                .with(new CollidableComponent(true))
                .with(physicsComponent)
                .with(new DirectionComponent(Direction.RIGHT))
                .with(new MarioStateTypeComponent(MarioStateType.BIG))
                .with(new StandingStateControl())
                .build();
    }

    public static GameEntity makeCloud(double x, double y)
    {
        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .viewFromTexture(CLOUD_TEXTURES[0])
                .build();
    }

    public static GameEntity makeMountain(double x, double y)
    {
        int i = (int) (Math.random() * MOUNTAIN_TEXTURES.length);

        return Entities.builder()
                .at(x * BLOCK_SIZE, (y - i) * BLOCK_SIZE)
                .viewFromTexture(MOUNTAIN_TEXTURES[i])
                .build();
    }

    public static GameEntity makeBush(double x, double y)
    {
        int i = (int) (Math.random() * BUSH_TEXTURES.length);

        return Entities.builder()
                .at(x * BLOCK_SIZE, y * BLOCK_SIZE)
                .viewFromTexture(BUSH_TEXTURES[i])
                .build();
    }
}
