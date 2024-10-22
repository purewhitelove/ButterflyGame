// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Strawberry.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Strawberry extends AbstractRelic
{

    public Strawberry()
    {
        super("Strawberry", "strawberry.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(7).append(LocalizedStrings.PERIOD).toString();
    }

    public void onEquip()
    {
        AbstractDungeon.player.increaseMaxHp(7, true);
    }

    public AbstractRelic makeCopy()
    {
        return new Strawberry();
    }

    public static final String ID = "Strawberry";
    private static final int HP_AMT = 7;
}
