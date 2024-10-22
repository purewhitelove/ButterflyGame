// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlackBlood.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, BurningBlood

public class BlackBlood extends AbstractRelic
{

    public BlackBlood()
    {
        super("Black Blood", "blackBlood.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(12).append(DESCRIPTIONS[1]).toString();
    }

    public void onVictory()
    {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new RelicAboveCreatureAction(p, this));
        if(p.currentHealth > 0)
            p.heal(12);
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player.hasRelic("Burning Blood");
    }

    public AbstractRelic makeCopy()
    {
        return new BlackBlood();
    }

    public static final String ID = "Black Blood";
}
