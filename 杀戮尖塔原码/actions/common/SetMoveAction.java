// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SetMoveAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SetMoveAction extends AbstractGameAction
{

    public SetMoveAction(AbstractMonster monster, String moveName, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent, int baseDamage, int multiplierAmt, boolean multiplier)
    {
        isMultiplier = false;
        theNextName = moveName;
        theNextMove = nextMove;
        theNextIntent = intent;
        theNextDamage = baseDamage;
        this.monster = monster;
        theMultiplier = multiplierAmt;
        isMultiplier = multiplier;
    }

    public SetMoveAction(AbstractMonster monster, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent, int baseDamage, int multiplierAmt, boolean multiplier)
    {
        this(monster, null, nextMove, intent, baseDamage, multiplierAmt, multiplier);
    }

    public SetMoveAction(AbstractMonster monster, String moveName, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent, int baseDamage)
    {
        this(monster, moveName, nextMove, intent, baseDamage, 0, false);
    }

    public SetMoveAction(AbstractMonster monster, String moveName, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent)
    {
        this(monster, moveName, nextMove, intent, -1);
    }

    public SetMoveAction(AbstractMonster monster, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent, int baseDamage)
    {
        this(monster, null, nextMove, intent, baseDamage);
    }

    public SetMoveAction(AbstractMonster monster, byte nextMove, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent intent)
    {
        this(monster, null, nextMove, intent, -1);
    }

    public void update()
    {
        monster.setMove(theNextName, theNextMove, theNextIntent, theNextDamage, theMultiplier, isMultiplier);
        isDone = true;
    }

    private AbstractMonster monster;
    private byte theNextMove;
    private com.megacrit.cardcrawl.monsters.AbstractMonster.Intent theNextIntent;
    private int theNextDamage;
    private String theNextName;
    private int theMultiplier;
    private boolean isMultiplier;
}
