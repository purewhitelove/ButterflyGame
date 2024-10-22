// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Toolbox.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ChooseOneColorless;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Toolbox extends AbstractRelic
{

    public Toolbox()
    {
        super("Toolbox", "toolbox.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw()
    {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChooseOneColorless());
    }

    public AbstractRelic makeCopy()
    {
        return new Toolbox();
    }

    public static final String ID = "Toolbox";
}
