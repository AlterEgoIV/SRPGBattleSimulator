package com.srpgbattlesimulator.states;

import com.badlogic.gdx.Input;
import com.srpgbattlesimulator.enums.BattleState;
import com.srpgbattlesimulator.gamemodes.Battle;

/**
 * Created by Carl on 10/08/2019.
 */
public class ControlCursorBattleState extends State
{
    private Battle battle;

    public ControlCursorBattleState(Battle battle)
    {
        this.battle = battle;
    }

    @Override
    public void update()
    {
        System.out.println("Control Cursor State Updating");
        if(!battle.battleCursor.isMoving()) handleInput();
        if(battle.battleCursor.isMoving()) battle.battleCursor.move();
    }

    @Override
    public void enter()
    {
        battle.battleCursor.show();
    }

    @Override
    public void exit()
    {
        battle.battleCursor.hide();
        battle.inputState.keyUp(Input.Keys.ENTER);
    }

    private void handleInput()
    {
        if(battle.inputState.isKeyDown(Input.Keys.UP)) battle.setBattleCursorTargetTile(0, 1);
        if(battle.inputState.isKeyDown(Input.Keys.DOWN)) battle.setBattleCursorTargetTile(0, -1);
        if(battle.inputState.isKeyDown(Input.Keys.LEFT)) battle.setBattleCursorTargetTile(-1, 0);
        if(battle.inputState.isKeyDown(Input.Keys.RIGHT)) battle.setBattleCursorTargetTile(1, 0);
        if(battle.inputState.isKeyDown(Input.Keys.ENTER)) battle.transitionBattleStateTo(BattleState.CONTROL_UNIT_STATE);
        if(battle.inputState.isKeyDown(Input.Keys.BACKSPACE)) battle.resetBattleCursorPosition();
    }
}
