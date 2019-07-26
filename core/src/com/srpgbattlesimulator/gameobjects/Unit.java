package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.MovementType;
import com.srpgbattlesimulator.rendering.Shape;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Carl on 06/05/2019.
 */
public class Unit extends BattleObject
{
    private int movement, agility;
    private boolean isPlayerUnit;
    public MovementType movementType;

    public Unit(Vector2 position, float width, float height, Shape shape, Tile startTile, int movement, MovementType movementType)
    {
        super(position, width, height, shape, startTile);
        this.movement = movement;
        this.agility = ThreadLocalRandom.current().nextInt(1, 11);
        this.isPlayerUnit = Math.random() >= .5f;
        this.movementType = movementType;
    }

    @Override
    public void update()
    {

    }

    public int getMovement()
    {
        return movement;
    }

    public int getAgility()
    {
        return agility;
    }

    public boolean isPlayerUnit()
    {
        return isPlayerUnit;
    }
}
