// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnlimboAction.java

package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;

public class UnlimboAction extends AbstractGameAction
{

    public UnlimboAction(AbstractCard card, boolean exhaust)
    {
        duration = Settings.ACTION_DUR_XFAST;
        this.card = card;
        this.exhaust = exhaust;
    }

    public UnlimboAction(AbstractCard card)
    {
        this(card, false);
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_XFAST)
        {
            if(exhaust);
            AbstractDungeon.player.limbo.removeCard(card);
            if(exhaust)
                AbstractDungeon.effectList.add(new ExhaustCardEffect(card));
            isDone = true;
        }
    }

    private AbstractCard card;
    private boolean exhaust;
}
