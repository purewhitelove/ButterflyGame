// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CultistMask.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CultistMask extends AbstractRelic
{

    public CultistMask()
    {
        super("CultistMask", "cultistMask.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new SFXAction("VO_CULTIST_1A"));
        addToBot(new TalkAction(true, DESCRIPTIONS[1], 1.0F, 2.0F));
    }

    public AbstractRelic makeCopy()
    {
        return new CultistMask();
    }

    public static final String ID = "CultistMask";
}
