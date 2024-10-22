// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StoneCalendar.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class StoneCalendar extends AbstractRelic
{

    public StoneCalendar()
    {
        super("StoneCalendar", "calendar.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(7).append(DESCRIPTIONS[1]).append(52).append(DESCRIPTIONS[2]).toString();
    }

    public void atBattleStart()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        counter++;
        if(counter == 7)
            beginLongPulse();
    }

    public void onPlayerEndTurn()
    {
        if(counter == 7)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(52, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            stopPulse();
            grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }

    public void onVictory()
    {
        counter = -1;
        stopPulse();
    }

    public AbstractRelic makeCopy()
    {
        return new StoneCalendar();
    }

    public static final String ID = "StoneCalendar";
    private static final int TURNS = 7;
    private static final int DMG = 52;
}
