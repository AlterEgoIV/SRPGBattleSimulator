package com.srpgbattlesimulator.gamemodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.TerrainName;
import com.srpgbattlesimulator.gameobjects.Tile;
import com.srpgbattlesimulator.gameobjects.Unit;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.rendering.ShapeName;

import java.util.*;

/**
 * Created by Carl on 06/05/2019.
 */
public class Grid
{
    private int columns, rows;
    private float tileWidth, tileHeight;
    public Tile[][] tiles;
    public List<Tile> activeTiles, potentialTiles;

    public Grid(int columns, int rows, float tileWidth, float tileHeight)
    {
        this.columns = columns;
        this.rows = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tiles = new Tile[columns][rows];
        this.activeTiles = new ArrayList<Tile>();
        this.potentialTiles = new ArrayList<Tile>();

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
        startTile.setAccumulatedTerrainCost(0);
        activeTiles.clear();
        potentialTiles.clear();

        if(!isOutOfBounds(startTile.getColumn(), startTile.getRow())) potentialTiles.add(startTile);

        while(potentialTiles.size() > 0)
        {
            Tile tile = Collections.min(potentialTiles, Comparator.comparing(Tile::getAccumulatedTerrainCost));

            addPotentialTile(tile, movement, tile.getColumn(), tile.getRow() + 1);
            addPotentialTile(tile, movement, tile.getColumn(), tile.getRow() - 1);
            addPotentialTile(tile, movement, tile.getColumn() - 1, tile.getRow());
            addPotentialTile(tile, movement, tile.getColumn() + 1, tile.getRow());

            if(!activeTiles.contains(tile)) activeTiles.add(tile);
            potentialTiles.remove(tile);
        }
    }

    private void addPotentialTile(Tile tile, int movement, int column, int row)
    {
        if(!isOutOfBounds(column, row) && tiles[column][row].terrainName != TerrainName.WATER && movement >= tile.getAccumulatedTerrainCost() + tiles[column][row].getTerrainCost())
        {
            tiles[column][row].setAccumulatedTerrainCost(tiles[column][row].getTerrainCost() + tile.getAccumulatedTerrainCost());
            potentialTiles.add(tiles[column][row]);
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
