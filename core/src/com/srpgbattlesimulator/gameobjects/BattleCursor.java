package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.gameobjects.tiles.Tile;
import com.srpgbattlesimulator.rendering.Shape;

/**
 * Created by Carl on 06/05/2019.
 */
public class BattleCursor extends BattleObject
{
    public BattleCursor(Vector2 position, float width, float height, Shape shape, Tile startTile)
    {
        super(position, width, height, shape, startTile);
    }

    @Override
    public void update()
    {

    }

    public void show()
    {
        shape.outlineColor = Color.WHITE;
    }

    public void hide()
    {
        shape.outlineColor = Color.CLEAR;
    }
}
