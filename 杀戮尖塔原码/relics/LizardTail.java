// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LizardTail.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class LizardTail extends AbstractRelic
{

    public LizardTail()
    {
        super("Lizard Tail", "lizardTail.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void setCounter(int setCounter)
    {
        if(setCounter == -2)
        {
            usedUp();
            counter = -2;
        }
    }

    public void onTrigger()
    {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        int healAmt = AbstractDungeon.player.maxHealth / 2;
        if(healAmt < 1)
            healAmt = 1;
        AbstractDungeon.player.heal(healAmt, true);
        setCounter(-2);
    }

    public AbstractRelic makeCopy()
    {
        return new LizardTail();
    }

    public static final String ID = "Lizard Tail";
}
