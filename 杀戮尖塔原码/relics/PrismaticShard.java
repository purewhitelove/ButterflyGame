// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PrismaticShard.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PrismaticShard extends AbstractRelic
{

    public PrismaticShard()
    {
        super("PrismaticShard", "prism.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy()
    {
        return new PrismaticShard();
    }

    public void onEquip()
    {
        if(AbstractDungeon.player.chosenClass != com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT && AbstractDungeon.player.masterMaxOrbs == 0)
            AbstractDungeon.player.masterMaxOrbs = 1;
    }

    public static final String ID = "PrismaticShard";
}
