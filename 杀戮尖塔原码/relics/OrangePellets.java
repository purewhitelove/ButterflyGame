// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrangePellets.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class OrangePellets extends AbstractRelic
{

    public OrangePellets()
    {
        super("OrangePellets", "pellets.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart()
    {
        SKILL = false;
        POWER = false;
        ATTACK = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
            ATTACK = true;
        else
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            SKILL = true;
        else
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER)
            POWER = true;
        if(ATTACK && SKILL && POWER)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new RemoveDebuffsAction(AbstractDungeon.player));
            SKILL = false;
            POWER = false;
            ATTACK = false;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new OrangePellets();
    }

    public static final String ID = "OrangePellets";
    private static boolean SKILL = false;
    private static boolean POWER = false;
    private static boolean ATTACK = false;

}
