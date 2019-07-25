package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.TerrainName;
import com.srpgbattlesimulator.rendering.Shape;

/**
 * Created by Carl on 06/05/2019.
 */
public class Tile extends GameObject
{
    private int column, row;
    public TerrainName terrainName;
    private float terrainCost, accumulatedTerrainCost;

    public Tile(Vector2 position, float width, float height, int column, int row, Shape shape)
    {
        super(position, width, height, shape);
        this.column = column;
        this.row = row;
        this.terrainCost = 1;
        this.accumulatedTerrainCost = 1;

        double r = Math.random();

        if(r <= .4)
        {
            terrainName = TerrainName.PLAIN;
            shape.fillColor = Color.GREEN;
            //terrainCost = 1f;
            terrainCost = 1;
        }
        else if(r <= .7)
        {
            terrainName = TerrainName.FOREST;
            shape.fillColor = Color.FOREST;
            //terrainCost = 1.5f;
            terrainCost = 1.5f;
        }
        else if(r <= .9)
        {
            terrainName = TerrainName.MOUNTAIN;
            shape.fillColor = Color.BROWN;
            //terrainCost = 3f;
            terrainCost = 2.5f;
        }
        else
        {
            terrainName = TerrainName.WATER;
            shape.fillColor = Color.BLUE;
        }

        shape.defaultFillColor = shape.fillColor;

//        terrainName = TerrainName.PLAIN;
//        shape.fillColor = Color.GREEN;
//        terrainCost = 2.5f;
        accumulatedTerrainCost = terrainCost;
    }

    @Override
    public void update()
    {

    }

    public int getColumn()
    {
        return column;
    }

    public int getRow()
    {
        return row;
    }

    public float getTerrainCost()
    {
        return terrainCost;
    }

    public float getAccumulatedTerrainCost()
    {
        return accumulatedTerrainCost;
    }

    public void setAccumulatedTerrainCost(float terrainCost)
    {
        this.accumulatedTerrainCost = terrainCost;
    }
}
