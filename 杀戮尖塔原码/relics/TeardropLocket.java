// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TeardropLocket.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.CalmStance;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class TeardropLocket extends AbstractRelic
{

    public TeardropLocket()
    {
        super("TeardropLocket", "tear_drop_locket.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        flash();
        addToTop(new ChangeStanceAction("Calm"));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy()
    {
        return new TeardropLocket();
    }

    public static final String ID = "TeardropLocket";
}
