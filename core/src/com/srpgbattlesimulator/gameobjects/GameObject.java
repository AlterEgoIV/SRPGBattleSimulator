package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.rendering.Shape;

/**
 * Created by Carl on 06/05/2019.
 */
public abstract class GameObject
{
    public Vector2 position;
    private float width, height;
    public Shape shape;

    public GameObject(Vector2 position, float width, float height, Shape shape)
    {
        this.position = position;
        this.width = width;
        this.height = height;
        this.shape = shape;
    }

    public abstract void update();

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }
}
