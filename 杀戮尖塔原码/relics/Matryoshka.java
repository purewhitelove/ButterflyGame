// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Matryoshka.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Matryoshka extends AbstractRelic
{

    public Matryoshka()
    {
        super("Matryoshka", "matryoshka.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
        counter = 2;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onChestOpen(boolean bossChest)
    {
        if(!bossChest && counter > 0)
        {
            counter--;
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if(AbstractDungeon.relicRng.randomBoolean(0.75F))
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.COMMON);
            else
                AbstractDungeon.getCurrRoom().addRelicToRewards(AbstractRelic.RelicTier.UNCOMMON);
            if(counter == 0)
            {
                setCounter(-2);
                description = DESCRIPTIONS[2];
            } else
            {
                description = DESCRIPTIONS[1];
            }
        }
    }

    public void setCounter(int setCounter)
    {
        counter = setCounter;
        if(setCounter == -2)
        {
            usedUp();
            counter = -2;
        }
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }

    public AbstractRelic makeCopy()
    {
        return new Matryoshka();
    }

    public static final String ID = "Matryoshka";
}
