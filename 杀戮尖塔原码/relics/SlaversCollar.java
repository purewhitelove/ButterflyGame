// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlaversCollar.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class SlaversCollar extends AbstractRelic
{

    public SlaversCollar()
    {
        super("SlaversCollar", "collar.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        if(AbstractDungeon.player != null)
            return setDescription(AbstractDungeon.player.chosenClass);
        else
            return setDescription(null);
    }

    private String setDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return DESCRIPTIONS[0];
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void beforeEnergyPrep()
    {
        boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.type == com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS)
                isEliteOrBoss = true;
        } while(true);
        if(isEliteOrBoss)
        {
            beginLongPulse();
            flash();
            AbstractDungeon.player.energy.energyMaster++;
        }
    }

    public void onVictory()
    {
        if(pulse)
        {
            AbstractDungeon.player.energy.energyMaster--;
            stopPulse();
        }
    }

    public AbstractRelic makeCopy()
    {
        return new SlaversCollar();
    }

    public static final String ID = "SlaversCollar";
}
