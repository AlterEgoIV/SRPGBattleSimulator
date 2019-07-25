package com.srpgbattlesimulator.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Carl on 06/05/2019.
 */
public class Shape extends Renderable
{
    private ShapeName shapeName;
    public Color fillColor, outlineColor, defaultFillColor;
    private float outlineThickness;

    public Shape(Vector2 position, float width, float height, ShapeName shapeName, Color fillColor, Color outlineColor, float outlineThickness)
    {
        super(position, width, height);
        this.shapeName = shapeName;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.outlineThickness = outlineThickness;
        this.defaultFillColor = fillColor;
    }

    public ShapeName getShapeName()
    {
        return shapeName;
    }

    public float getOutlineThickness()
    {
        return outlineThickness;
    }
}
