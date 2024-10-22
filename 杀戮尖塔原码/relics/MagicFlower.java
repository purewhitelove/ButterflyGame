// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MagicFlower.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MagicFlower extends AbstractRelic
{

    public MagicFlower()
    {
        super("Magic Flower", "magicFlower.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public int onPlayerHeal(int healAmount)
    {
        if(AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            flash();
            return MathUtils.round((float)healAmount * 1.5F);
        } else
        {
            return healAmount;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new MagicFlower();
    }

    public static final String ID = "Magic Flower";
    private static final float HEAL_MULTIPLIER = 1.5F;
}
