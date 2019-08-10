package com.srpgbattlesimulator.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.MovementType;
import com.srpgbattlesimulator.UnitAttack;
import com.srpgbattlesimulator.UnitData;
import com.srpgbattlesimulator.rendering.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Carl on 06/05/2019.
 */
public class Unit extends BattleObject
{
    public UnitData unitData;
    public List<Unit> attackTargets;
    public List<UnitAttack> unitAttacks;
    public UnitAttack currentUnitAttack;
    public Unit currentTarget;

    public Unit(Vector2 position, float width, float height, Shape shape, Tile startTile, UnitData unitData)
    {
        super(position, width, height, shape, startTile);
        this.unitData = unitData;
        this.attackTargets = new ArrayList<>();
        this.unitAttacks = new ArrayList<>();
        this.currentUnitAttack = new UnitAttack(1, 4, 1);
    }

    @Override
    public void update()
    {

    }

    public boolean findAttackTargets(List<Unit> units)
    {
        for(Unit unit : units)
        {
            if(unitData.isPlayerUnit() != unit.unitData.isPlayerUnit())
            {
                Tile targetTile = unit.currentTile;

                if(Math.abs(currentTile.getColumn() - targetTile.getColumn()) + Math.abs(currentTile.getRow() - targetTile.getRow()) >= currentUnitAttack.rangeMin &&
                   Math.abs(currentTile.getColumn() - targetTile.getColumn()) + Math.abs(currentTile.getRow() - targetTile.getRow()) <= currentUnitAttack.rangeMax)
                {
                    attackTargets.add(unit);
                }
            }
        }

        return attackTargets.size() > 0;
    }
}
