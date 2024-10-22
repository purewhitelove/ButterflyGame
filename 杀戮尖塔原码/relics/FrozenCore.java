// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FrozenCore.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, CrackedCore

public class FrozenCore extends AbstractRelic
{

    public FrozenCore()
    {
        super("FrozenCore", "frozenOrb.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onPlayerEndTurn()
    {
        if(AbstractDungeon.player.hasEmptyOrb())
        {
            flash();
            AbstractDungeon.player.channelOrb(new Frost());
        }
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player.hasRelic("Cracked Core");
    }

    public AbstractRelic makeCopy()
    {
        return new FrozenCore();
    }

    public static final String ID = "FrozenCore";
}
