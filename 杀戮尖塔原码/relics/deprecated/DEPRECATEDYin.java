// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDYin.java

package com.megacrit.cardcrawl.relics.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DEPRECATEDYin extends AbstractRelic
{

    public DEPRECATEDYin()
    {
        super("Yin", "yin.png", com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON, com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
        {
            flash();
            com.megacrit.cardcrawl.characters.AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RelicAboveCreatureAction(p, this));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new DEPRECATEDYin();
    }

    public static final String ID = "Yin";
}
