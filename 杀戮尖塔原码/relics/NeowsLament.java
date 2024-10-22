// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowsLament.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class NeowsLament extends AbstractRelic
{

    public NeowsLament()
    {
        super("NeowsBlessing", "lament.png", AbstractRelic.RelicTier.SPECIAL, AbstractRelic.LandingSound.FLAT);
        counter = 3;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        if(counter > 0)
        {
            counter--;
            if(counter == 0)
            {
                setCounter(-2);
                description = DESCRIPTIONS[1];
                tips.clear();
                tips.add(new PowerTip(name, description));
                initializeTips();
            }
            flash();
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); m.healthBarUpdatedEvent())
            {
                m = (AbstractMonster)iterator.next();
                m.currentHealth = 1;
            }

            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void setCounter(int setCounter)
    {
        counter = setCounter;
        if(setCounter <= 0)
            usedUp();
    }

    public AbstractRelic makeCopy()
    {
        return new NeowsLament();
    }

    public static final String ID = "NeowsBlessing";
}
