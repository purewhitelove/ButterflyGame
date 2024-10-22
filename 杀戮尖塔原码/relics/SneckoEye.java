// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SneckoEye.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SneckoEye extends AbstractRelic
{

    public SneckoEye()
    {
        super("Snecko Eye", "sneckoEye.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        AbstractDungeon.player.masterHandSize += 2;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.masterHandSize -= 2;
    }

    public void atPreBattle()
    {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConfusionPower(AbstractDungeon.player)));
    }

    public AbstractRelic makeCopy()
    {
        return new SneckoEye();
    }

    public static final String ID = "Snecko Eye";
    public static final int HAND_MODIFICATION = 2;
}
