// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FullHealthAdditionalDamageAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FullHealthAdditionalDamageAction extends AbstractGameAction
{

    public FullHealthAdditionalDamageAction(AbstractCreature target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public FullHealthAdditionalDamageAction(AbstractCreature target, AbstractCreature source, int damage)
    {
        this(target, source, damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL);
    }

    public FullHealthAdditionalDamageAction(AbstractCreature target, AbstractCreature source, int damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType type)
    {
        info = new DamageInfo(source, damage, type);
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(duration == 0.5F)
        {
            if(target.currentHealth != target.maxHealth)
                target.damage(info);
            else
                target.damage(new DamageInfo(info.owner, info.output + 6, info.type));
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }

    private DamageInfo info;
    private static final int ADDITIONAL_DAMAGE = 6;
}
