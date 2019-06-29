package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.rendering.Shape;

/**
 * Created by Carl on 24/06/2019.
 */
public abstract class BattleObject extends GameObject
{
    public Tile startTile, currentTile, targetTile;
    private boolean isMoving;
    private float moveTime, distancePerFrame;
    public Vector2 direction;
    private float elapsedTime;

    public BattleObject(Vector2 position, float width, float height, Shape shape, Tile startTile)
    {
        super(position, width, height, shape);
        this.startTile = startTile;
        this.currentTile = startTile;
        this.targetTile = startTile;
        this.isMoving = false;
        this.moveTime = .2f;
        this.distancePerFrame = 0f;
        this.direction = new Vector2();
        this.elapsedTime = 0f;
    }

    public void setTargetTile(Tile tile)
    {
        if(tile != currentTile)
        {
            isMoving = true;
            targetTile = tile;
            direction.set(targetTile.position.x - currentTile.position.x, targetTile.position.y - currentTile.position.y);
            distancePerFrame = direction.len() / moveTime;
            direction.nor();
        }
    }

    public void move()
    {
        position.add(direction.x * distancePerFrame * Gdx.graphics.getDeltaTime(), direction.y * distancePerFrame * Gdx.graphics.getDeltaTime());

        elapsedTime += Gdx.graphics.getDeltaTime();

        if(elapsedTime >= moveTime)
        {
            isMoving = false;
            currentTile = targetTile;
            position.set(targetTile.position);
            elapsedTime = 0f;
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
