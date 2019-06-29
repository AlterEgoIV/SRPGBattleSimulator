package com.srpgbattlesimulator.rendering;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Carl on 16/06/2019.
 */
public abstract class Renderable
{
    public Vector2 position;
    protected float width, height;

    public Renderable(Vector2 position, float width, float height)
    {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
}
