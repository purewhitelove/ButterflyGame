// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CeramicFish.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CeramicFish extends AbstractRelic
{

    public CeramicFish()
    {
        super("CeramicFish", "ceramic_fish.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(9).append(DESCRIPTIONS[1]).toString();
    }

    public void use()
    {
        flash();
        counter--;
        if(counter == 0)
            setCounter(0);
        else
            description = DESCRIPTIONS[1];
    }

    public void onObtainCard(AbstractCard c)
    {
        AbstractDungeon.player.gainGold(9);
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public AbstractRelic makeCopy()
    {
        return new CeramicFish();
    }

    public static final String ID = "CeramicFish";
    private static final int GOLD_AMT = 9;
}
