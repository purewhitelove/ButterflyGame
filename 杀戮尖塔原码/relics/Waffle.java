// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Waffle.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Waffle extends AbstractRelic
{

    public Waffle()
    {
        super("Lee's Waffle", "waffle.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(7).append(DESCRIPTIONS[1]).toString();
    }

    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(7, false);
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
    }

    public AbstractRelic makeCopy()
    {
        return new Waffle();
    }

    public static final String ID = "Lee's Waffle";
    private static final int HP_AMT = 7;
}
