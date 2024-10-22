// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelfFormingClay.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SelfFormingClay extends AbstractRelic
{

    public SelfFormingClay()
    {
        super("Self Forming Clay", "clay.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(3).append(DESCRIPTIONS[1]).toString();
    }

    public void wasHPLost(int damageAmount)
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && damageAmount > 0)
        {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, 3, name), 3));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new SelfFormingClay();
    }

    public static final String ID = "Self Forming Clay";
    private static final int BLOCK_AMT = 3;
}
