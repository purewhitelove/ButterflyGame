// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Boot.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Boot extends AbstractRelic
{

    public Boot()
    {
        super("Boot", "boot.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(4).append(DESCRIPTIONS[1]).toString();
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < 5)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return 5;
        } else
        {
            return damageAmount;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new Boot();
    }

    public static final String ID = "Boot";
    private static final int THRESHOLD = 5;
}
