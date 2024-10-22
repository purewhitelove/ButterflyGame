// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Enchiridion.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Enchiridion extends AbstractRelic
{

    public Enchiridion()
    {
        super("Enchiridion", "enchiridion.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle()
    {
        flash();
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER).makeCopy();
        if(c.cost != -1)
            c.setCostForTurn(0);
        UnlockTracker.markCardAsSeen(c.cardID);
        addToBot(new MakeTempCardInHandAction(c));
    }

    public AbstractRelic makeCopy()
    {
        return new Enchiridion();
    }

    public static final String ID = "Enchiridion";
}
