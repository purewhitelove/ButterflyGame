// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Lantern.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Lantern extends AbstractRelic
{

    public Lantern()
    {
        super("Lantern", "lantern.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
        firstTurn = true;
        energyBased = true;
    }

    public String getUpdatedDescription()
    {
        if(AbstractDungeon.player != null)
            return setDescription(AbstractDungeon.player.chosenClass);
        else
            return setDescription(null);
    }

    private String setDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(DESCRIPTIONS[1]).append(LocalizedStrings.PERIOD).toString();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atPreBattle()
    {
        firstTurn = true;
    }

    public void atTurnStart()
    {
        if(firstTurn)
        {
            flash();
            addToTop(new GainEnergyAction(1));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            firstTurn = false;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Lantern();
    }

    public static final String ID = "Lantern";
    private static final int ENERGY_AMT = 1;
    private boolean firstTurn;
}
