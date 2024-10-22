// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MindBlastAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MindBlastAction extends AbstractGameAction
{

    public MindBlastAction(AbstractCreature target)
    {
        setValues(target, AbstractDungeon.player);
        duration = Settings.ACTION_DUR_FAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST && target != null)
        {
            DamageInfo info = new DamageInfo(source, AbstractDungeon.player.drawPile.size());
            info.applyPowers(source, target);
            addToTop(new DamageAction(target, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }
        tickDuration();
    }
}
