package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.utilities.Timer;

/**
 * Created by Carl on 24/06/2019.
 */
public abstract class BattleObject extends GameObject
{
    public Tile startTile, currentTile, targetTile;
    private boolean isMoving;
    private float distancePerFrame;
    public Vector2 direction;
    private Timer timer;

    public BattleObject(Vector2 position, float width, float height, Shape shape, Tile startTile)
    {
        super(position, width, height, shape);
        this.startTile = startTile;
        this.currentTile = startTile;
        this.targetTile = startTile;
        this.isMoving = false;
        this.distancePerFrame = 0f;
        this.direction = new Vector2();
        this.timer = new Timer(.2f);
    }

    public void setTargetTile(Tile tile)
    {
        if(tile != currentTile)
        {
            isMoving = true;
            targetTile = tile;
            direction.set(targetTile.position.x - currentTile.position.x, targetTile.position.y - currentTile.position.y);
            distancePerFrame = direction.len() / timer.getTargetTime();
            direction.nor();
        }
    }

    public void move()
    {
        position.add(direction.x * distancePerFrame * Gdx.graphics.getDeltaTime(), direction.y * distancePerFrame * Gdx.graphics.getDeltaTime());

        timer.update();

        if(timer.hasReachedTargetTime())
        {
            isMoving = false;
            currentTile = targetTile;
            position.set(targetTile.position);
            timer.reset();
        }
    }

    public void resetPosition()
    {
        currentTile = startTile;
        position.set(currentTile.position);
    }

    public boolean isMoving()
    {
        return isMoving;
    }
}
