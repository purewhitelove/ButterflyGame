// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Torii.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Torii extends AbstractRelic
{

    public Torii()
    {
        super("Torii", "torii.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(5).append(DESCRIPTIONS[1]).toString();
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && damageAmount > 1 && damageAmount <= 5)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return 1;
        } else
        {
            return damageAmount;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Torii();
    }

    public static final String ID = "Torii";
    private static final int THRESHOLD = 5;
}
