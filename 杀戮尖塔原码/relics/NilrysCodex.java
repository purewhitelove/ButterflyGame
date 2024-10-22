// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NilrysCodex.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.CodexAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NilrysCodex extends AbstractRelic
{

    public NilrysCodex()
    {
        super("Nilry's Codex", "codex.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onPlayerEndTurn()
    {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new CodexAction());
    }

    public AbstractRelic makeCopy()
    {
        return new NilrysCodex();
    }

    public static final String ID = "Nilry's Codex";
}
