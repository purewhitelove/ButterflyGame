// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BurningBlood.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BurningBlood extends AbstractRelic
{

    public BurningBlood()
    {
        super("Burning Blood", "burningBlood.png", AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(6).append(DESCRIPTIONS[1]).toString();
    }

    public void onVictory()
    {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        if(p.currentHealth > 0)
            p.heal(6);
    }

    public AbstractRelic makeCopy()
    {
        return new BurningBlood();
    }

    public static final String ID = "Burning Blood";
    private static final int HEALTH_AMT = 6;
}
