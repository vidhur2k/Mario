package com.gamedesign.mario;

public enum Direction
{
    LEFT(-1), RIGHT(1);

    public final int SIGN;

    Direction(int sign)
    {
        SIGN = sign;
    }
}
