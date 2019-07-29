package com.srpgbattlesimulator;

/**
 * Created by Carl on 29/07/2019.
 */
public class Terrain
{
    public TerrainType terrainType;
    public float cost, accumulatedCost;

    public Terrain(TerrainType terrainType)
    {
        this.cost = 0f;
        this.accumulatedCost = 0f;
        this.terrainType = terrainType;
    }
}
