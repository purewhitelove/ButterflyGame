// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VioletLotus.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.CalmStance;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class VioletLotus extends AbstractRelic
{

    public VioletLotus()
    {
        super("VioletLotus", "violet_lotus.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance)
    {
        if(!prevStance.ID.equals(newStance.ID) && prevStance.ID.equals("Calm"))
        {
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new VioletLotus();
    }

    public static final String ID = "VioletLotus";
}
