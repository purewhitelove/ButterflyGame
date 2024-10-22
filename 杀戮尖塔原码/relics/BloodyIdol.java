// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BloodyIdol.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BloodyIdol extends AbstractRelic
{

    public BloodyIdol()
    {
        super("Bloody Idol", "bloodyChalice.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(5).append(DESCRIPTIONS[1]).toString();
    }

    public void onGainGold()
    {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.heal(5, true);
    }

    public AbstractRelic makeCopy()
    {
        return new BloodyIdol();
    }

    public static final String ID = "Bloody Idol";
    private static final int HEAL_AMOUNT = 5;
}
