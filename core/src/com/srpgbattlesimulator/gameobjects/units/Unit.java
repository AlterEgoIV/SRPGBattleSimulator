package com.srpgbattlesimulator.gameobjects.units;

import com.badlogic.gdx.math.Vector2;
import com.srpgbattlesimulator.gameobjects.BattleObject;
import com.srpgbattlesimulator.gameobjects.tiles.Tile;
import com.srpgbattlesimulator.rendering.Shape;

import java.util.ArrayList;
import java.util.List;

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
