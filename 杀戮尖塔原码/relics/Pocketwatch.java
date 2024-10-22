// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Pocketwatch.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Pocketwatch extends AbstractRelic
{

    public Pocketwatch()
    {
        super("Pocketwatch", "pocketwatch.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
        firstTurn = true;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        counter = 0;
        firstTurn = true;
    }

    public void atTurnStartPostDraw()
    {
        if(counter <= 3 && !firstTurn)
            addToBot(new DrawCardAction(AbstractDungeon.player, 3));
        else
            firstTurn = false;
        counter = 0;
        beginLongPulse();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        counter++;
        if(counter > 3)
            stopPulse();
    }

    public void onVictory()
    {
        counter = -1;
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new Pocketwatch();
    }

    public static final String ID = "Pocketwatch";
    private static final int AMT = 3;
    private boolean firstTurn;
}
