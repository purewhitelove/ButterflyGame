// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FossilizedHelix.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class FossilizedHelix extends AbstractRelic
{

    public FossilizedHelix()
    {
        super("FossilizedHelix", "helix.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, 1), 1));
        grayscale = true;
    }

    public void justEnteredRoom(AbstractRoom room)
    {
        grayscale = false;
    }

    public AbstractRelic makeCopy()
    {
        return new FossilizedHelix();
    }

    public static final String ID = "FossilizedHelix";
}
