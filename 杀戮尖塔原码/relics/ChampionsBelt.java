// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChampionsBelt.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.WeakPower;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ChampionsBelt extends AbstractRelic
{

    public ChampionsBelt()
    {
        super("Champion Belt", "championBelt.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void onTrigger(AbstractCreature target)
    {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false), 1));
    }

    public AbstractRelic makeCopy()
    {
        return new ChampionsBelt();
    }

    public static final String ID = "Champion Belt";
    public static final int EFFECT = 1;
}
