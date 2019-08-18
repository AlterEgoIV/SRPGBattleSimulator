package com.srpgbattlesimulator.gamemodes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.enums.BattleState;
import com.srpgbattlesimulator.enums.MovementType;
import com.srpgbattlesimulator.gameobjects.units.UnitData;
import com.srpgbattlesimulator.input.InputState;
import com.srpgbattlesimulator.gameobjects.BattleCursor;
import com.srpgbattlesimulator.gameobjects.tiles.Tile;
import com.srpgbattlesimulator.gameobjects.units.Unit;
import com.srpgbattlesimulator.rendering.Renderable;
import com.srpgbattlesimulator.rendering.Shape;
import com.srpgbattlesimulator.enums.ShapeName;
import com.srpgbattlesimulator.states.ControlCursorBattleState;
import com.srpgbattlesimulator.states.ControlUnitBattleState;
import com.srpgbattlesimulator.states.State;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Carl on 16/06/2019.
 */
public class Battle
{
    private List<Renderable> renderables;
    public boolean isOver, isNewTurn;
    public int turnCount, currentUnit;
    public Grid grid;
    private int gridColumns, gridRows;
    //private BattleState battleState;
    public List<Unit> units;
    public Unit activeUnit;
    public BattleCursor battleCursor;
    //public BattleInputHandler inputHandler;
    public InputState inputState;
    public int currentTarget;
    private State currentState;
    private Map<BattleState, State> states;

    public Battle(InputState inputState, List<Renderable> renderables)
    {
        this.renderables = renderables;
        this.isOver = false;
        this.isNewTurn = true;
        this.turnCount = 0;
        this.gridColumns = 20;
        this.gridRows = 20;
        this.currentUnit = 0;
        this.inputState = inputState;
        grid = new Grid(gridColumns, gridRows, (float)Gdx.graphics.getWidth() / gridColumns, (float)Gdx.graphics.getHeight() / gridRows);
        //battleState = BattleState.TRANSITION_CURSOR_STATE;
        units = new ArrayList<Unit>();
        createUnits();
        setTurnOrder();
        activeUnit = units.get(currentUnit);
        battleCursor = createGridCursor(activeUnit.position.cpy(), grid.getTileWidth(), grid.getTileHeight(), Color.CLEAR, Color.WHITE, activeUnit.startTile);
        //inputHandler = new BattleInputHandler(inputState, this);
        this.currentTarget = 0;
        states = new HashMap<>();
        initialiseStates();
        this.currentState = states.get(BattleState.CONTROL_CURSOR_STATE);
    }

    public void update()
    {
        currentState.update();
//        if(isNewTurn)
//        {
//            ++turnCount;
//            isNewTurn = false;
//        }
//
//        activeUnit = units.get(currentUnit);
//
//        updateBattleState();
        updateRenderables();
    }

    private void initialiseStates()
    {
        states.put(BattleState.CONTROL_CURSOR_STATE, new ControlCursorBattleState(this));
        states.put(BattleState.CONTROL_UNIT_STATE, new ControlUnitBattleState(this));
        states.put(BattleState.AI_UPDATE_STATE, new ControlUnitBattleState(this));
    }

    public boolean isOver()
    {
        return isOver;
    }

//    private void updateBattleState()
//    {
//        switch(battleState)
//        {
//            case TRANSITION_CURSOR_STATE:
//            {
//                if(!battleCursor.isMoving()) battleCursor.setTargetTile(activeUnit.startTile);
//                if(battleCursor.isMoving()) battleCursor.move();
//                if(!battleCursor.isMoving()) transitionBattleStateTo(activeUnit.unitData.isPlayerUnit() ? BattleState.CONTROL_CURSOR_STATE : BattleState.AI_UPDATE_STATE);
//
//                break;
//            }
//
//            case CONTROL_CURSOR_STATE:
//            {
//                if(!battleCursor.isMoving()) inputHandler.update();
//                if(battleCursor.isMoving()) battleCursor.move();
//
//                break;
//            }
//
//            case CONTROL_UNIT_STATE:
//            {
//                if(!activeUnit.isMoving()) inputHandler.update();
//                if(activeUnit.isMoving()) activeUnit.move();
//
//                for(Tile tile : grid.activeTiles)
//                {
//                    tile.update();
//                }
//
//                break;
//            }
//
//            case ENEMY_UNIT_UPDATE_STATE:
//            {
//                transitionBattleStateTo(BattleState.TRANSITION_CURSOR_STATE);
//
//                break;
//            }
//
//            case SELECT_TARGET_STATE:
//            {
//                if(!battleCursor.isMoving()) inputHandler.update();
//                if(battleCursor.isMoving()) battleCursor.move();
//
//                break;
//            }
//        }
//    }

    public void transitionBattleStateTo(BattleState newState)
    {
        currentState.exit();
        currentState = states.get(newState);
        currentState.enter();
    }

//    public void transitionBattleStateTo(BattleState newState)
//    {
//        exitBattleState(battleState);
//        battleState = newState;
//        enterBattleState(newState);
//    }

//    private void enterBattleState(BattleState battleState)
//    {
//        switch(battleState)
//        {
//            case TRANSITION_CURSOR_STATE:
//            {
//                battleCursor.show();
//
//                break;
//            }
//
//            case CONTROL_CURSOR_STATE:
//            {
//                battleCursor.show();
//
//                break;
//            }
//
//            case CONTROL_UNIT_STATE:
//            {
//                activeUnit.shape.outlineColor = Color.WHITE;
//                grid.setActiveTiles(activeUnit);
//
//                for(Tile tile : grid.activeTiles)
//                {
//                    tile.hue = 0f;
//                    tile.timer.reset();
//                    if(tile.deltaColor < 0) tile.deltaColor *= -1;
//                }
//
//                break;
//            }
//
//            case AI_UPDATE_STATE:
//            {
//                break;
//            }
//
//            case SELECT_TARGET_STATE:
//            {
//                System.out.println("Entered SELECT TARGET STATE");
//
//                battleCursor.show();
//
//                currentTarget = 0;
//                activeUnit.currentTarget = activeUnit.attackTargets.get(currentTarget);
//
//                battleCursor.currentTile = activeUnit.attackTargets.get(currentTarget).currentTile;
//                battleCursor.position.set(activeUnit.attackTargets.get(currentTarget).currentTile.position);
//
//                break;
//            }
//        }
//    }

//    private void exitBattleState(BattleState battleState)
//    {
//        switch(battleState)
//        {
//            case TRANSITION_CURSOR_STATE:
//            {
//                battleCursor.startTile = battleCursor.currentTile;
//                battleCursor.hide();
//
//                break;
//            }
//
//            case CONTROL_CURSOR_STATE:
//            {
//                inputHandler.clearKey(Input.Keys.ENTER);
//                battleCursor.hide();
//
//                break;
//            }
//
//            case CONTROL_UNIT_STATE:
//            {
//                inputHandler.clearKey(Input.Keys.ENTER);
////                currentUnit = currentUnit < units.size() - 1 ? currentUnit + 1 : 0;
////                activeUnit.startTile = activeUnit.currentTile;
////                activeUnit.shape.outlineColor = Color.CLEAR;
////                battleCursor.startTile = activeUnit.startTile;
////                battleCursor.currentTile = battleCursor.startTile;
////                battleCursor.position.set(battleCursor.startTile.position);
////                grid.activeTiles.clear();
//
//                break;
//            }
//
//            case AI_UPDATE_STATE:
//            {
//                currentUnit = currentUnit < units.size() - 1 ? currentUnit + 1 : 0;
//
//                break;
//            }
//
//            case SELECT_TARGET_STATE:
//            {
//                break;
//            }
//        }
//    }

    private void setTurnOrder()
    {
        units.sort(Comparator.comparing(u -> u.unitData.getAgility(), Comparator.reverseOrder()));
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

    public boolean findActiveUnitAttackTargets()
    {
        return activeUnit.findAttackTargets(units);
    }

    public void cycleTargets()
    {
        System.out.println("Entered Cycle Targets");

        currentTarget = currentTarget < activeUnit.attackTargets.size() - 1 ? currentTarget + 1 : 0;

        battleCursor.setTargetTile(activeUnit.attackTargets.get(currentTarget).currentTile);
        //battleCursor.currentTile = activeUnit.attackTargets.get(currentTarget).currentTile;
        //battleCursor.position.set(activeUnit.attackTargets.get(currentTarget).currentTile.position);
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
        for(int i = 0; i < 20; ++i)
        {
            int tileColumn = ThreadLocalRandom.current().nextInt(0, gridColumns);
            int tileRow = ThreadLocalRandom.current().nextInt(0, gridRows);

            int health = 10;
            int mana = 0;
            int attack = 7;
            int defense = 3;
            int agility = ThreadLocalRandom.current().nextInt(1, 11);
            int movement = 12;
            boolean isPlayerUnit = Math.random() >= .5f;
            MovementType movementType = Math.random() >= .5f ? MovementType.FOOT : MovementType.FLY;
            Color color = isPlayerUnit ? Color.SKY : Color.RED;

            UnitData unitData = new UnitData(health, mana, attack, defense, agility, movement, isPlayerUnit, movementType);

            units.add(createUnit(grid.tiles[tileColumn][tileRow].position.cpy(), grid.getTileWidth(), grid.getTileHeight(),
            color, Color.CLEAR, grid.tiles[tileColumn][tileRow], unitData));
        }
    }

    private BattleCursor createGridCursor(Vector2 position, float width, float height, Color fillColor, Color outlineColor, Tile startTile)
    {
        return new BattleCursor(position, width, height, new Shape(position, width, height, ShapeName.RECT, fillColor, outlineColor, 4), startTile);
    }

    private Unit createUnit(Vector2 position, float width, float height, Color fillColor, Color outlineColor, Tile startTile, UnitData unitData)
    {
        return new Unit(position, width, height, new Shape(position, width, height, ShapeName.ELLIPSE, fillColor, outlineColor, 4), startTile, unitData);
    }

//    public BattleState getBattleState()
//    {
//        return battleState;
//    }
}
