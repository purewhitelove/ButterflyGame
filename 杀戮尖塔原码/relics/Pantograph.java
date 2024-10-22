// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Pantograph.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Pantograph extends AbstractRelic
{

    public Pantograph()
    {
        super("Pantograph", "pantograph.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.type == com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS)
            {
                flash();
                addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 25, 0.0F));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                return;
            }
        }

    }

    public AbstractRelic makeCopy()
    {
        return new Pantograph();
    }

    public static final String ID = "Pantograph";
    private static final int HEAL_AMT = 25;
}
