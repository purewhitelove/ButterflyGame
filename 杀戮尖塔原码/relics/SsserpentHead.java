// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SsserpentHead.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SsserpentHead extends AbstractRelic
{

    public SsserpentHead()
    {
        super("SsserpentHead", "serpentHead.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).toString();
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if(room instanceof EventRoom)
        {
            flash();
            AbstractDungeon.player.gainGold(50);
        }
    }

    public AbstractRelic makeCopy()
    {
        return new SsserpentHead();
    }

    public static final String ID = "SsserpentHead";
    private static final int GOLD_AMT = 50;
}
