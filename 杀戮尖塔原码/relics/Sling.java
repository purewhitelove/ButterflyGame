// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sling.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Sling extends AbstractRelic
{

    public Sling()
    {
        super("Sling", "sling.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        if(AbstractDungeon.getCurrRoom().eliteTrigger)
        {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Sling();
    }

    public static final String ID = "Sling";
    private static final int STR = 2;
}
