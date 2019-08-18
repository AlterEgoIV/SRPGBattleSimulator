package com.srpgbattlesimulator.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.srpgbattlesimulator.enums.BattleState;
import com.srpgbattlesimulator.gamemodes.Battle;
import com.srpgbattlesimulator.gameobjects.tiles.Tile;

/**
 * Created by Carl on 10/08/2019.
 */
public class ControlUnitBattleState extends State
{
    private Battle battle;

    public ControlUnitBattleState(Battle battle)
    {
        this.battle = battle;
    }

    @Override
    public void update()
    {
        if(!battle.activeUnit.isMoving()) handleInput();
        if(battle.activeUnit.isMoving()) battle.activeUnit.move();

        for(Tile tile : battle.grid.activeTiles)
        {
            tile.update();
        }
    }

    @Override
    public void enter()
    {
        System.out.println("Entered Control Unit Battle State");

        battle.activeUnit.shape.outlineColor = Color.WHITE;
        battle.grid.setActiveTiles(battle.activeUnit);

        for(Tile tile : battle.grid.activeTiles)
        {
            tile.hue = 0f;
            tile.timer.reset();
            if(tile.deltaColor < 0) tile.deltaColor *= -1;
        }
    }

    @Override
    public void exit()
    {
        battle.inputState.keyUp(Input.Keys.ENTER);
//        currentunit = currentunit < units.size() - 1 ? currentunit + 1 : 0;
//        activeunit.starttile = activeunit.currenttile;
//        activeunit.shape.outlinecolor = color.clear;
//        battlecursor.starttile = activeunit.starttile;
//        battlecursor.currenttile = battlecursor.starttile;
//        battlecursor.position.set(battlecursor.starttile.position);
//        grid.activetiles.clear();
    }

    private void handleInput()
    {
        if(battle.inputState.isKeyDown(Input.Keys.UP)) battle.setPlayerUnitTargetTile(0, 1);
        if(battle.inputState.isKeyDown(Input.Keys.DOWN)) battle.setPlayerUnitTargetTile(0, -1);
        if(battle.inputState.isKeyDown(Input.Keys.LEFT)) battle.setPlayerUnitTargetTile(-1, 0);
        if(battle.inputState.isKeyDown(Input.Keys.RIGHT)) battle.setPlayerUnitTargetTile(1, 0);
        if(battle.inputState.isKeyDown(Input.Keys.ENTER))
        {
            if(battle.findActiveUnitAttackTargets())
            {
                //battle.transitionBattleStateTo(BattleState.SELECT_TARGET_STATE);
            }
            else
            {
                System.out.println("No Attack Targets");
                //battle.transitionBattleStateTo(BattleState.TRANSITION_CURSOR_STATE);
            }
        }
        if(battle.inputState.isKeyDown(Input.Keys.BACKSPACE)) battle.resetActiveUnitPosition();
    }
}
