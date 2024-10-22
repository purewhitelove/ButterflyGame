// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SacredBark.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SacredBark extends AbstractRelic
{

    public SacredBark()
    {
        super("SacredBark", "bark.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        AbstractPotion p;
        for(Iterator iterator = AbstractDungeon.player.potions.iterator(); iterator.hasNext(); p.initializeData())
            p = (AbstractPotion)iterator.next();

    }

    public AbstractRelic makeCopy()
    {
        return new SacredBark();
    }

    public static final String ID = "SacredBark";
}
