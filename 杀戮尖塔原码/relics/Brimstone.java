// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Brimstone.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Brimstone extends AbstractRelic
{

    public Brimstone()
    {
        super("Brimstone", "brimstone.png", AbstractRelic.RelicTier.SHOP, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(2).append(DESCRIPTIONS[1]).append(1).append(DESCRIPTIONS[2]).toString();
    }

    public void atTurnStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
        AbstractMonster m;
        for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); addToTop(new ApplyPowerAction(m, m, new StrengthPower(m, 1), 1)))
            m = (AbstractMonster)iterator.next();

    }

    public AbstractRelic makeCopy()
    {
        return new Brimstone();
    }

    public static final String ID = "Brimstone";
    private static final int STR_AMT = 2;
    private static final int ENEMY_STR_AMT = 1;
}
