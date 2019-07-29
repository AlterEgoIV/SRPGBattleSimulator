package com.srpgbattlesimulator.utilities;

import com.badlogic.gdx.Gdx;

/**
 * Created by Carl on 29/07/2019.
 */
public class Timer
{
    private float elapsedTime, targetTime;
    private boolean hasReachedTargetTime;

    public Timer(float targetTime)
    {
        this.elapsedTime = 0f;
        this.targetTime = targetTime;
        this.hasReachedTargetTime = false;
    }

    public void update()
    {
        elapsedTime += Gdx.graphics.getDeltaTime();

        if(elapsedTime >= targetTime)
        {
            hasReachedTargetTime = true;
        }
    }

    public boolean hasReachedTargetTime()
    {
        return hasReachedTargetTime;
    }

    public void reset()
    {
        elapsedTime = 0f;
        hasReachedTargetTime = false;
    }

    public float getTargetTime()
    {
        return targetTime;
    }

    public void setTargetTime(float targetTime)
    {
        this.targetTime = targetTime;
    }
}
