// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GainBlockAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class GainBlockAction extends AbstractGameAction
{

    public GainBlockAction(AbstractCreature target, int amount)
    {
        this.target = target;
        this.amount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        duration = 0.25F;
        startDuration = 0.25F;
    }

    public GainBlockAction(AbstractCreature target, AbstractCreature source, int amount)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        duration = 0.25F;
        startDuration = 0.25F;
    }

    public GainBlockAction(AbstractCreature target, int amount, boolean superFast)
    {
        this(target, amount);
        if(superFast)
            duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public GainBlockAction(AbstractCreature target, AbstractCreature source, int amount, boolean superFast)
    {
        this(target, source, amount);
        if(superFast)
            duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(!target.isDying && !target.isDead && duration == startDuration)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SHIELD));
            target.addBlock(amount);
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext(); c.applyPowers())
                c = (AbstractCard)iterator.next();

        }
        tickDuration();
    }

    private static final float DUR = 0.25F;
}
