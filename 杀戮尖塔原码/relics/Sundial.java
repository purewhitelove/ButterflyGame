// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sundial.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Sundial extends AbstractRelic
{

    public Sundial()
    {
        super("Sundial", "sundial.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
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
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void onEquip()
    {
        counter = 0;
    }

    public void onShuffle()
    {
        counter++;
        if(counter == 3)
        {
            counter = 0;
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(2));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Sundial();
    }

    public static final String ID = "Sundial";
    private static final int NUM_TURNS = 3;
    private static final int ENERGY_AMT = 2;
}
