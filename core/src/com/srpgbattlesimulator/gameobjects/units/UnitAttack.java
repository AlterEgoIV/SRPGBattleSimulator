package com.srpgbattlesimulator.gameobjects.units;

/**
 * Created by Carl on 03/08/2019.
 */
public class UnitAttack
{
    public int rangeMin, rangeMax, targetCount;

    public UnitAttack(int rangeMin, int rangeMax, int targetCount)
    {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.targetCount = targetCount;
    }
}
