// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AncientTeaSet.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class AncientTeaSet extends AbstractRelic
{

    public AncientTeaSet()
    {
        super("Ancient Tea Set", "tea_set.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.SOLID);
        firstTurn = true;
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
        return DESCRIPTIONS[0];
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atTurnStart()
    {
        if(firstTurn)
        {
            if(counter == -2)
            {
                pulse = false;
                counter = -1;
                flash();
                addToTop(new GainEnergyAction(2));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
            firstTurn = false;
        }
    }

    public void atPreBattle()
    {
        firstTurn = true;
    }

    public void setCounter(int counter)
    {
        super.setCounter(counter);
        if(counter == -2)
            pulse = true;
    }

    public void onEnterRestRoom()
    {
        flash();
        counter = -2;
        pulse = true;
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new AncientTeaSet();
    }

    public static final String ID = "Ancient Tea Set";
    private static final int ENERGY_AMT = 2;
    private boolean firstTurn;
}
