// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MeatOnTheBone.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MeatOnTheBone extends AbstractRelic
{

    public MeatOnTheBone()
    {
        super("Meat on the Bone", "meat.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(12).append(DESCRIPTIONS[1]).toString();
    }

    public void onTrigger()
    {
        AbstractPlayer p = AbstractDungeon.player;
        if((float)p.currentHealth <= (float)p.maxHealth / 2.0F && p.currentHealth > 0)
        {
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            p.heal(12);
            stopPulse();
        }
    }

    public void onBloodied()
    {
        flash();
        pulse = true;
    }

    public void onNotBloodied()
    {
        stopPulse();
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new MeatOnTheBone();
    }

    public static final String ID = "Meat on the Bone";
    private static final int HEAL_AMT = 12;
}
