// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IncenseBurner.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class IncenseBurner extends AbstractRelic
{

    public IncenseBurner()
    {
        super("Incense Burner", "incenseBurner.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        if(counter == -1)
            counter += 2;
        else
            counter++;
        if(counter == 6)
        {
            counter = 0;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, null, new IntangiblePlayerPower(AbstractDungeon.player, 1), 1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new IncenseBurner();
    }

    public static final String ID = "Incense Burner";
    private static final int NUM_TURNS = 6;
}
