package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.Terrain;
import com.srpgbattlesimulator.TerrainType;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.rendering.ShapeName;
import com.srpgbattlesimulator.utilities.Timer;

/**
 * Created by Carl on 06/05/2019.
 */
public class Tile extends GameObject
{
    private int column, row;
    public Shape overlay;
    public float hue, deltaColor;
    public Timer timer;
    private Terrain terrain;

    public Tile(Vector2 position, float width, float height, int column, int row, Shape shape, Terrain terrain)
    {
        super(position, width, height, shape);
        this.column = column;
        this.row = row;
        this.overlay = new Shape(position.cpy(), width, height, ShapeName.RECT, new Color(0f, 0f, 0f, .7f), Color.CLEAR, 0);
        this.timer = new Timer(.6f);
        this.hue = 0f;
        this.deltaColor = 1f / timer.getTargetTime();
        this.terrain = terrain;
    }

    @Override
    public void update()
    {
        overlay.fillColor.set(hue, hue, hue, overlay.fillColor.a);
        hue += deltaColor * Gdx.graphics.getDeltaTime();
        timer.update();

        if(timer.hasReachedTargetTime())
        {
            timer.reset();
            deltaColor *= -1;
        }
    }

    public int getColumn()
    {
        return column;
    }

    public int getRow()
    {
        return row;
    }

    public float getAccumulatedTerrainCost()
    {
        return terrain.accumulatedCost;
    }

    public void setAccumulatedTerrainCost(float terrainCost)
    {
        this.terrain.accumulatedCost = terrainCost;
    }

    public TerrainType getTerrainType()
    {
        return terrain.terrainType;
    }
}
