package com.srpgbattlesimulator.states;

import com.srpgbattlesimulator.gamemodes.Battle;

/**
 * Created by Carl on 18/08/2019.
 */
public class AIUpdateBattleState extends State
{
    private Battle battle;

    public AIUpdateBattleState(Battle battle)
    {
        this.battle = battle;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void enter()
    {

    }

    @Override
    public void exit()
    {
        battle.currentUnit = battle.currentUnit < battle.units.size() - 1 ? battle.currentUnit + 1 : 0;
    }
}
