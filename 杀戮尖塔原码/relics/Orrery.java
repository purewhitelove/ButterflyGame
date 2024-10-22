// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Orrery.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Orrery extends AbstractRelic
{

    public Orrery()
    {
        super("Orrery", "orrery.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        for(int i = 0; i < 4; i++)
            AbstractDungeon.getCurrRoom().addCardToRewards();

        AbstractDungeon.combatRewardScreen.open(DESCRIPTIONS[1]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
    }

    public AbstractRelic makeCopy()
    {
        return new Orrery();
    }

    public static final String ID = "Orrery";
}
