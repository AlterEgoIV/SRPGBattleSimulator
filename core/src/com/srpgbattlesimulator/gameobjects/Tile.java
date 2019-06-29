package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.rendering.Shape;

/**
 * Created by Carl on 06/05/2019.
 */
public class Tile extends GameObject
{
    private int column, row;

    public Tile(Vector2 position, float width, float height, int column, int row, Shape shape)
    {
        super(position, width, height, shape);
        this.column = column;
        this.row = row;
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
}
