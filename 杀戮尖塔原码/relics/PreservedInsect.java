// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PreservedInsect.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PreservedInsect extends AbstractRelic
{

    public PreservedInsect()
    {
        super("PreservedInsect", "insect.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        MODIFIER_AMT = 0.25F;
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(25).append(DESCRIPTIONS[1]).toString();
    }

    public void atBattleStart()
    {
        if(AbstractDungeon.getCurrRoom().eliteTrigger)
        {
            flash();
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m.currentHealth > (int)((float)m.maxHealth * (1.0F - MODIFIER_AMT)))
                {
                    m.currentHealth = (int)((float)m.maxHealth * (1.0F - MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            } while(true);
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 52;
    }

    public AbstractRelic makeCopy()
    {
        return new PreservedInsect();
    }

    public static final String ID = "PreservedInsect";
    private float MODIFIER_AMT;
}
