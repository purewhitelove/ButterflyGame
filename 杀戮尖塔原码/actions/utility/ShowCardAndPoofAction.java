// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowCardAndPoofAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;

public class ShowCardAndPoofAction extends AbstractGameAction
{

    public ShowCardAndPoofAction(AbstractCard card)
    {
        this.card = null;
        setValues(AbstractDungeon.player, null, 1);
        this.card = card;
        duration = 0.2F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if(duration == 0.2F)
        {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(card));
            if(AbstractDungeon.player.limbo.contains(card))
                AbstractDungeon.player.limbo.removeCard(card);
            AbstractDungeon.player.cardInUse = null;
        }
        tickDuration();
    }

    private AbstractCard card;
    private static final float PURGE_DURATION = 0.2F;
}
