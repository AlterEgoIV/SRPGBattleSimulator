package com.srpgbattlesimulator.input;

import com.badlogic.gdx.Input;
import com.srpgbattlesimulator.gamemodes.Battle;
import com.srpgbattlesimulator.gamemodes.BattleState;

/**
 * Created by Carl on 29/07/2019.
 */
public class BattleInputHandler extends InputHandler
{
    private Battle battle;

    public BattleInputHandler(InputState inputState, Battle battle)
    {
        super(inputState);
        this.battle = battle;
    }

    @Override
    public void update()
    {
        switch(battle.getBattleState())
        {
            case CONTROL_CURSOR_STATE:
            {
                if(inputState.isKeyDown(Input.Keys.UP)) battle.setBattleCursorTargetTile(0, 1);
                if(inputState.isKeyDown(Input.Keys.DOWN)) battle.setBattleCursorTargetTile(0, -1);
                if(inputState.isKeyDown(Input.Keys.LEFT)) battle.setBattleCursorTargetTile(-1, 0);
                if(inputState.isKeyDown(Input.Keys.RIGHT)) battle.setBattleCursorTargetTile(1, 0);
                if(inputState.isKeyDown(Input.Keys.ENTER)) battle.transitionBattleStateTo(BattleState.CONTROL_PLAYER_UNIT_STATE);
                if(inputState.isKeyDown(Input.Keys.BACKSPACE)) battle.resetBattleCursorPosition();

                break;
            }

            case CONTROL_PLAYER_UNIT_STATE:
            {
                if(inputState.isKeyDown(Input.Keys.UP)) battle.setPlayerUnitTargetTile(0, 1);
                if(inputState.isKeyDown(Input.Keys.DOWN)) battle.setPlayerUnitTargetTile(0, -1);
                if(inputState.isKeyDown(Input.Keys.LEFT)) battle.setPlayerUnitTargetTile(-1, 0);
                if(inputState.isKeyDown(Input.Keys.RIGHT)) battle.setPlayerUnitTargetTile(1, 0);
                if(inputState.isKeyDown(Input.Keys.ENTER)) battle.transitionBattleStateTo(BattleState.TRANSITION_CURSOR_STATE);
                if(inputState.isKeyDown(Input.Keys.BACKSPACE)) battle.resetActiveUnitPosition();

                break;
            }
        }
    }

    public void clearKey(int keyCode)
    {
        inputState.keyUp(keyCode);
    }
}
