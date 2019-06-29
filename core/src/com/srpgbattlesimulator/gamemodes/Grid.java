package com.srpgbattlesimulator.gamemodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.gameobjects.Tile;
import com.srpgbattlesimulator.gameobjects.Unit;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.rendering.ShapeName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carl on 06/05/2019.
 */
public class Grid
{
    private int columns, rows;
    private float tileWidth, tileHeight;
    public Tile[][] tiles;
    public List<Tile> activeTiles;

    public Grid(int columns, int rows, float tileWidth, float tileHeight)
    {
        this.columns = columns;
        this.rows = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tiles = new Tile[columns][rows];
        this.activeTiles = new ArrayList<Tile>();

        for(int i = 0; i < columns; ++i)
        {
            for(int j = 0; j < rows; ++j)
            {
                tiles[i][j] = createTile(new Vector2((i * tileWidth) + tileWidth / 2f, (j * tileHeight) + tileHeight / 2f), tileWidth, tileHeight, i, j, Color.SKY, Color.BLACK);
            }
        }
    }

    public void setActiveTiles(Unit unit)
    {
        Tile startTile = unit.startTile;
        int movement = unit.getMovement();
        int horizontalTiles = 1;
        activeTiles.clear();

        for(int i = movement; i >= 0; --i)
        {
            for(int j = -horizontalTiles / 2; j <= horizontalTiles / 2; ++j)
            {
                if(i > 0)
                {
                    if(!isOutOfBounds(startTile.getColumn() + j, startTile.getRow() + i)) activeTiles.add(tiles[startTile.getColumn() + j][startTile.getRow() + i]);
                    if(!isOutOfBounds(startTile.getColumn() + j, startTile.getRow() - i)) activeTiles.add(tiles[startTile.getColumn() + j][startTile.getRow() - i]);
                }
                else
                {
                    if(!isOutOfBounds(startTile.getColumn() + j, startTile.getRow())) activeTiles.add(tiles[startTile.getColumn() + j][startTile.getRow()]);
                }
            }

            horizontalTiles += 2;
        }
    }

    public boolean isOutOfBounds(int column, int row)
    {
        return column < 0 || column >= columns || row < 0 || row >= rows;
    }

    private Tile createTile(Vector2 position, float width, float height, int i, int j, Color fillColor, Color outlineColor)
    {
        return new Tile(position, width, height, i, j, new Shape(position, width, height, ShapeName.RECT, fillColor, outlineColor, 1));
    }

    public int getColumns()
    {
        return columns;
    }

    public int getRows()
    {
        return rows;
    }

    public float getTileWidth()
    {
        return tileWidth;
    }

    public float getTileHeight()
    {
        return tileHeight;
    }
}
