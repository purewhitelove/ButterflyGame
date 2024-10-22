// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BlackStar.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BlackStar extends AbstractRelic
{

    public BlackStar()
    {
        super("Black Star", "blackstar.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEnterRoom(AbstractRoom room)
    {
        if(room instanceof MonsterRoomElite)
        {
            pulse = true;
            beginPulse();
        } else
        {
            pulse = false;
        }
    }

    public void onVictory()
    {
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)
        {
            flash();
            pulse = false;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new BlackStar();
    }

    public static final String ID = "Black Star";
}
