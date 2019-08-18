package com.srpgbattlesimulator.gameobjects.units;

import com.srpgbattlesimulator.enums.MovementType;

/**
 * Created by Carl on 02/08/2019.
 */
public class UnitData
{
    private int health, maxHealth, mana, maxMana, attack, defense, agility, movement;
    private boolean isPlayerUnit;
    public MovementType movementType;

    public UnitData(int health, int mana, int attack, int defense, int agility, int movement, boolean isPlayerUnit, MovementType movementType)
    {
        this.health = health;
        this.maxHealth = health;
        this.mana = mana;
        this.maxMana = mana;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
        this.movement = movement;
        this.isPlayerUnit = isPlayerUnit;
        this.movementType = movementType;
    }

    public int getHealth()
    {
        return health;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getMana()
    {
        return mana;
    }

    public int getMaxMana()
    {
        return maxMana;
    }

    public int getAttack()
    {
        return attack;
    }

    public int getDefense()
    {
        return defense;
    }

    public int getAgility()
    {
        return agility;
    }

    public int getMovement()
    {
        return movement;
    }

    public boolean isPlayerUnit()
    {
        return isPlayerUnit;
    }

    public void setHealth(int health)
    {
        if(health > maxHealth)
        {
            this.health = maxHealth;
        }
        else if(health < 0)
        {
            this.health = 0;
        }
        else
        {
            this.health = health;
        }
    }

    public void setMana(int mana)
    {
        if(mana > maxMana)
        {
            this.mana = maxMana;
        }
        else if(mana < 0)
        {
            this.mana = 0;
        }
        else
        {
            this.mana = mana;
        }
    }
}
