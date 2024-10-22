// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BarrageAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.ArrayList;

public class BarrageAction extends AbstractGameAction
{

    public BarrageAction(AbstractCreature m, DamageInfo info)
    {
        this.info = null;
        this.info = info;
        target = m;
    }

    public void update()
    {
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
            if(!(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot))
                addToTop(new DamageAction(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));

        isDone = true;
    }

    private DamageInfo info;
    private AbstractCreature target;
}
