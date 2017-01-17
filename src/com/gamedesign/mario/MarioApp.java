package com.gamedesign.mario;

import com.almasb.ents.Entity;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.EntityView;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.component.MainViewComponent;
import com.almasb.fxgl.gameplay.Level;
import com.almasb.fxgl.parser.TextLevelParser;
import com.almasb.fxgl.settings.GameSettings;
import com.gamedesign.mario.collision.PlayerPickupHandler;
import com.gamedesign.mario.collision.PlayerPlatformHandler;
import com.gamedesign.mario.component.MarioStateTypeComponent;
import com.gamedesign.mario.control.mario.StandingStateControl;
import com.gamedesign.mario.event.PickupEvent;
import com.gamedesign.mario.event.PlatformEvent;
import com.gamedesign.mario.type.EntityType;
import com.gamedesign.mario.type.MarioStateType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.gamedesign.mario.Config.*;

public class MarioApp extends GameApplication
{
    public GameEntity mario()
    {
        return (GameEntity) getGameWorld()
                .getEntitiesByType(EntityType.PLAYER)
                .get(0);
    }

    @Override
    protected void initSettings(GameSettings gameSettings)
    {
        gameSettings.setWidth(1024);
        gameSettings.setHeight(768);
        gameSettings.setTitle("Mario");
        gameSettings.setVersion("0.1");
        gameSettings.setIntroEnabled(false);
        gameSettings.setMenuEnabled(false);
        gameSettings.setProfilingEnabled(false);        // disable FPS
        MainViewComponent.turnOnDebugBBox(Color.RED);
    }

    @Override
    protected void initInput()
    {

    }

    @Override
    protected void initAssets()
    {
        getAssetLoader().cache();
    }

    @Override
    protected void initGame()
    {
        TextLevelParser textLevelParser = new TextLevelParser();
        textLevelParser.setEmptyChar(' ');

        textLevelParser.addEntityProducer('b', EntityFactory::makeBlock);
        textLevelParser.addEntityProducer('?', EntityFactory::makeQuestionBlock);
        textLevelParser.addEntityProducer('m', EntityFactory::makeMushroom);
        textLevelParser.addEntityProducer('M', EntityFactory::makeMario);

        Level level = textLevelParser.parse("levels/1-1.txt");

        getGameWorld().setLevel(level);

        Entity player = level.getEntities()
                .stream()
                .filter(e -> e.hasControl(StandingStateControl.class))
                .findAny()
                .get();

        getGameScene().getViewport()
                .bindToEntity(player, getWidth() / 2, getHeight() / 2);

        getGameScene().getViewport()
                .setBounds(0, 0, level.getWidth() * BLOCK_SIZE,
                        level.getHeight() * BLOCK_SIZE);

        initEventHandlers();
        initBackground(level.getWidth(), level.getHeight());
//        initPlayer();
//        initBlocks();
    }

    private void initBackground(double width, double height)
    {
        GameEntity background = Entities.builder()
                .type(EntityType.BACKGROUND)
                .viewFromNode(new Rectangle(getWidth(), getHeight(), Color.rgb(107, 136, 255)))
                .buildAndAttach(getGameWorld());

        background.setRenderLayer(RenderLayer.BACKGROUND);

        background.getPositionComponent()
                .xProperty()
                .bind(getGameScene().getViewport().xProperty());

        background.getPositionComponent()
                .yProperty()
                .bind(getGameScene().getViewport().yProperty());

        // make clouds
        for(int i = 0; i < width / 5; i++)
        {
            double x = Math.random() * width;
            double y = Math.random() * (height / 3);

            GameEntity gameEntity = EntityFactory.makeCloud(x, y);
            getGameWorld().addEntity(gameEntity);
            gameEntity.setRenderLayer(RenderLayer.BACKGROUND);
        }

        // make mountains
        for(int i = 0; i < width / 20; i++)
        {
            double x = Math.random() * width;
            double y = height - 3;

            GameEntity gameEntity = EntityFactory.makeMountain(x, y);
            getGameWorld().addEntity(gameEntity);
            gameEntity.setRenderLayer(RenderLayer.BACKGROUND);
        }

        // make bushes
        for(int i = 0; i < width / 10; i++)
        {
            double x = Math.random() * width;
            double y = height - 3;

            GameEntity gameEntity = EntityFactory.makeBush(x, y);
            getGameWorld().addEntity(gameEntity);
            gameEntity.setRenderLayer(RenderLayer.BACKGROUND);
        }
    }

    private void initPlayer()
    {
        getGameWorld()
                .addEntity(EntityFactory
                        .makeMario(0, getHeight() / BLOCK_SIZE - 2));
    }

    private void initBlocks()
    {
        for(int i = 0; i < getWidth() / BLOCK_SIZE; i++)
            getGameWorld().addEntity(EntityFactory.makeBlock(i, getHeight() / BLOCK_SIZE - 1));
    }

    @Override
    protected void initPhysics()
    {
        getPhysicsWorld().addCollisionHandler(new PlayerPlatformHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerPickupHandler());
    }

    private void initEventHandlers()
    {
        getEventBus().addEventHandler(PickupEvent.MUSHROOM, event -> {

            double x = mario().getX() / BLOCK_SIZE;
            double y = mario().getY() / BLOCK_SIZE - 1;

            GameEntity bigMario = EntityFactory.makeBigMario(x, y);

            mario().removeFromWorld();
            getGameWorld().addEntity(bigMario);

            getGameScene().getViewport()
                    .bindToEntity(bigMario, getWidth() / 2, getHeight() / 2);

            event.getPickup().removeFromWorld();
        });

        getEventBus().addEventHandler(PlatformEvent.QUESTION_BLOCK, event ->
        {
            GameEntity gameEntity = (GameEntity) event.getPlatform();
            double x = gameEntity.getX() / BLOCK_SIZE;
            double y = gameEntity.getY() / BLOCK_SIZE;

            MarioStateType marioStateType =
                    mario().getComponentUnsafe(MarioStateTypeComponent.class)
                            .getValue();

            switch(marioStateType)
            {
                case SMALL:
                    getGameWorld().addEntity(EntityFactory.spawnMushroom(x, y));
                    break;
            }

            event.getPlatform().removeFromWorld();
            getGameWorld().addEntity(EntityFactory.makeUsedBlock(x, y));
        });
    }

    @Override
    protected void initUI()
    {

    }

    @Override
    protected void onUpdate(double v)
    {

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}