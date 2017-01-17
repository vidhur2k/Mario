package com.gamedesign.mario;

import javafx.scene.input.KeyCode;

public class Config
{
    public static final int BLOCK_SIZE = 48;

    public static final double WALKING_SPEED = 300 * 60;
    public static final double RUNNING_SPEED = 350 * 60;
    public static final double JUMPING_SPEED = 7 * 60;
    public static final double JUMPING_DELTA = 5;
    public static final double SLOW_FACTOR = 0.65;

    public static final double MUSHROOM_SPEED = 60 * 60;

    public static final String[] SMALL_StandingStateControl_TEXTURES = {"mario.png"};
    public static final String[] BIG_StandingStateControl_TEXTURES = {"bigMario.png"};
    public static final String[] SMALL_JumpingStateControl_TEXTURES = {"marioJump.png"};
    public static final String[] BIG_JumpingStateControl_TEXTURES = {"bigMarioJump.png"};
    public static final String[] SMALL_RunningStateControl_TEXTURES = {"marioRun00.png", "marioRun01.png", "marioRun02.png", "marioTurn.png"};
    public static final String[] BIG_RunningStateControl_TEXTURES = {"bigMarioRun00.png", "bigMarioRun01.png", "bigMarioRun02.png", "bigMarioTurn.png"};

    public static final String[] MUSHROOM_TEXTURES = {"mushroom.png"};
    public static final String[] QUESTION_BLOCK_TEXTURES = {"questionBlock00.png", "questionBlock01.png", "questionBlock02.png", "questionBlock01.png", "questionBlock00.png"};
    public static final String[] USED_BLOCK_TEXTURES = {"usedBlock.png"};

    public static final KeyCode LEFT_KEY = KeyCode.A;
    public static final KeyCode RIGHT_KEY = KeyCode.D;
    public static final KeyCode JUMP_KEY = KeyCode.SPACE;
    public static final KeyCode RUN_KEY = KeyCode.SHIFT;

    public static final String[] CLOUD_TEXTURES = {"cloud.png"};
    public static final String[] MOUNTAIN_TEXTURES = {"mountain00.png", "mountain01.png"};
    public static final String[] BUSH_TEXTURES = {"bush00.png", "bush01.png", "bush02.png"};






}
