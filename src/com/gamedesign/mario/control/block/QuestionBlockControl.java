package com.gamedesign.mario.control.block;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import static com.gamedesign.mario.Config.*;

public class QuestionBlockControl extends AbstractControl
{
    private GameEntity gameEntity;
    private String[] textures;
    private int i;
    private LocalTimer localTimer;

    @Override
    public void onAdded(Entity entity)
    {
        gameEntity = (GameEntity) entity;
        textures = QUESTION_BLOCK_TEXTURES;
        localTimer = FXGL.newLocalTimer();
        localTimer.capture();
    }

    @Override
    public void onUpdate(Entity entity, double v)
    {
        if(localTimer.elapsed(Duration.millis(250)))
        {
            i = (i + 1) % textures.length;

            gameEntity.getMainViewComponent().setTexture(textures[i]);

            localTimer.capture();
        }
    }





}
