// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ForesightPower.java

package com.megacrit.cardcrawl.powers.watcher;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ForesightPower extends AbstractPower
{

    public ForesightPower(AbstractCreature owner, int scryAmt)
    {
        name = powerStrings.NAME;
        ID = "WireheadingPower";
        this.owner = owner;
        amount = scryAmt;
        updateDescription();
        loadRegion("wireheading");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void atStartOfTurn()
    {
        if(AbstractDungeon.player.drawPile.size() <= 0)
            addToTop(new EmptyDeckShuffleAction());
        flash();
        addToBot(new ScryAction(amount));
    }

    public static final String POWER_ID = "WireheadingPower";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("WireheadingPower");
    }
}
