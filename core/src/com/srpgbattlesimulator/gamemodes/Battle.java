package com.srpgbattlesimulator.gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.MovementType;
import com.srpgbattlesimulator.input.BattleInputHandler;
import com.srpgbattlesimulator.input.InputState;
import com.srpgbattlesimulator.gameobjects.BattleCursor;
import com.srpgbattlesimulator.gameobjects.Tile;
import com.srpgbattlesimulator.gameobjects.Unit;
import com.srpgbattlesimulator.rendering.Renderable;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.rendering.ShapeName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Carl on 16/06/2019.
 */
public class Battle
{
    private List<Renderable> renderables;
    private boolean isOver, isNewTurn;
    private int turnCount, currentUnit;
    private Grid grid;
    private int gridColumns, gridRows;
    private BattleState battleState;
    private List<Unit> units;
    private Unit activeUnit;
    private BattleCursor battleCursor;
    private BattleInputHandler inputHandler;

    public Battle(InputState inputState, List<Renderable> renderables)
    {
        this.renderables = renderables;
        this.isOver = false;
        this.isNewTurn = true;
        this.turnCount = 0;
        this.gridColumns = 20;
        this.gridRows = 20;
        this.currentUnit = 0;
        grid = new Grid(gridColumns, gridRows, (float)Gdx.graphics.getWidth() / gridColumns, (float)Gdx.graphics.getHeight() / gridRows);
        battleState = BattleState.TRANSITION_CURSOR_STATE;
        units = new ArrayList<Unit>();
        createUnits();
        setTurnOrder();
        activeUnit = units.get(currentUnit);
        battleCursor = createGridCursor(activeUnit.position.cpy(), grid.getTileWidth(), grid.getTileHeight(), Color.CLEAR, Color.WHITE, activeUnit.startTile);
        inputHandler = new BattleInputHandler(inputState, this);
    }

    public void update()
    {
        if(isNewTurn)
        {
            ++turnCount;
            isNewTurn = false;
        }

        activeUnit = units.get(currentUnit);

        updateBattleState();
        updateRenderables();
    }

    public boolean isOver()
    {
        return isOver;
    }

    private void updateBattleState()
    {
        switch(battleState)
        {
            case TRANSITION_CURSOR_STATE:
            {
                if(!battleCursor.isMoving()) battleCursor.setTargetTile(activeUnit.startTile);
                if(battleCursor.isMoving()) battleCursor.move();
                if(!battleCursor.isMoving()) transitionBattleStateTo(activeUnit.isPlayerUnit() ? BattleState.CONTROL_CURSOR_STATE : BattleState.ENEMY_UNIT_UPDATE_STATE);

                break;
            }

            case CONTROL_CURSOR_STATE:
            {
                if(!battleCursor.isMoving()) inputHandler.update();
                if(battleCursor.isMoving()) battleCursor.move();

                break;
            }

            case CONTROL_PLAYER_UNIT_STATE:
            {
                if(!activeUnit.isMoving()) inputHandler.update();
                if(activeUnit.isMoving()) activeUnit.move();

                for(Tile tile : grid.activeTiles)
                {
                    tile.update();
                }

                break;
            }

            case ENEMY_UNIT_UPDATE_STATE:
            {
                transitionBattleStateTo(BattleState.TRANSITION_CURSOR_STATE);

                break;
            }
        }
    }

    public void transitionBattleStateTo(BattleState newState)
    {
        exitBattleState(battleState);
        battleState = newState;
        enterBattleState(newState);
    }

    private void enterBattleState(BattleState battleState)
    {
        switch(battleState)
        {
            case TRANSITION_CURSOR_STATE:
            {
                battleCursor.show();

                break;
            }

            case CONTROL_CURSOR_STATE:
            {
                battleCursor.show();

                break;
            }

            case CONTROL_PLAYER_UNIT_STATE:
            {
                activeUnit.shape.outlineColor = Color.WHITE;
                grid.setActiveTiles(activeUnit);

                for(Tile tile : grid.activeTiles)
                {
                    tile.hue = 0f;
                    tile.timer.reset();
                    if(tile.deltaColor < 0) tile.deltaColor *= -1;
                }

                break;
            }

            case ENEMY_UNIT_UPDATE_STATE:
            {
                break;
            }
        }
    }

    private void exitBattleState(BattleState battleState)
    {
        switch(battleState)
        {
            case TRANSITION_CURSOR_STATE:
            {
                battleCursor.startTile = battleCursor.currentTile;
                battleCursor.hide();

                break;
            }

            case CONTROL_CURSOR_STATE:
            {
                inputHandler.clearKey(Input.Keys.ENTER);
                battleCursor.hide();

                break;
            }

            case CONTROL_PLAYER_UNIT_STATE:
            {
                inputHandler.clearKey(Input.Keys.ENTER);
                currentUnit = currentUnit < units.size() - 1 ? currentUnit + 1 : 0;
                activeUnit.startTile = activeUnit.currentTile;
                activeUnit.shape.outlineColor = Color.CLEAR;
                battleCursor.startTile = activeUnit.startTile;
                battleCursor.currentTile = battleCursor.startTile;
                battleCursor.position.set(battleCursor.startTile.position);
                grid.activeTiles.clear();

                break;
            }

            case ENEMY_UNIT_UPDATE_STATE:
            {
                currentUnit = currentUnit < units.size() - 1 ? currentUnit + 1 : 0;

                break;
            }
        }
    }

    private void setTurnOrder()
    {
        Collections.sort(units, Comparator.comparing(Unit::getAgility).reversed());
    }

    public void setBattleCursorTargetTile(int columnOffset, int rowOffset)
    {
        if(!grid.isOutOfBounds(battleCursor.currentTile.getColumn() + columnOffset, battleCursor.currentTile.getRow() + rowOffset))
        {
            battleCursor.setTargetTile(grid.tiles[battleCursor.currentTile.getColumn() + columnOffset][battleCursor.currentTile.getRow() + rowOffset]);
        }
    }

    public void setPlayerUnitTargetTile(int columnOffset, int rowOffset)
    {
        for(Tile tile : grid.activeTiles)
        {
            if(tile.getColumn() == activeUnit.currentTile.getColumn() + columnOffset && tile.getRow() == activeUnit.currentTile.getRow() + rowOffset)
            {
                activeUnit.setTargetTile(tile);
                break;
            }
        }
    }

    public void resetBattleCursorPosition()
    {
        battleCursor.resetPosition();
    }

    public void resetActiveUnitPosition()
    {
        activeUnit.resetPosition();
    }

    private void updateRenderables()
    {
        for(int i = 0; i < grid.getColumns(); ++i)
        {
            for(int j = 0; j < grid.getRows(); ++j)
            {
                renderables.add(grid.tiles[i][j].shape);
            }
        }

        for(Tile tile : grid.activeTiles)
        {
            renderables.add(tile.overlay);
        }

        for(Unit unit : units)
        {
            if(!unit.equals(activeUnit)) renderables.add(unit.shape);
        }

        renderables.add(activeUnit.shape);
        renderables.add(battleCursor.shape);
    }

    private void createUnits()
    {
        for(int i = 0; i < 10; ++i)
        {
            int tileColumn = ThreadLocalRandom.current().nextInt(0, gridColumns);
            int tileRow = ThreadLocalRandom.current().nextInt(0, gridRows);
            float red = (float)Math.random();
            float green = (float)Math.random();
            float blue = (float)Math.random();
            float alpha = 1;

            units.add(createUnit(
            grid.tiles[tileColumn][tileRow].position.cpy(),
            grid.getTileWidth(),
            grid.getTileHeight(),
            new Color(red, green, blue, alpha),
            Color.CLEAR,
            grid.tiles[tileColumn][tileRow], 6,
            Math.random() >= .5f ? MovementType.FOOT : MovementType.FLY));
        }
    }

    private BattleCursor createGridCursor(Vector2 position, float width, float height, Color fillColor, Color outlineColor, Tile startTile)
    {
        return new BattleCursor(position, width, height, new Shape(position, width, height, ShapeName.RECT, fillColor, outlineColor, 4), startTile);
    }

    private Unit createUnit(Vector2 position, float width, float height, Color fillColor, Color outlineColor, Tile startTile, int movement, MovementType movementType)
    {
        return new Unit(position, width, height, new Shape(position, width, height, ShapeName.ELLIPSE, fillColor, outlineColor, 4), startTile, movement, movementType);
    }

    public BattleState getBattleState()
    {
        return battleState;
    }
}
