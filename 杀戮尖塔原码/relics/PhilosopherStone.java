// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PhilosopherStone.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PhilosopherStone extends AbstractRelic
{

    public PhilosopherStone()
    {
        super("Philosopher's Stone", "philosopherStone.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(1).append(DESCRIPTIONS[1]).toString();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atBattleStart()
    {
        AbstractMonster m;
        for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); m.addPower(new StrengthPower(m, 1)))
        {
            m = (AbstractMonster)iterator.next();
            addToTop(new RelicAboveCreatureAction(m, this));
        }

        AbstractDungeon.onModifyPower();
    }

    public void onSpawnMonster(AbstractMonster monster)
    {
        monster.addPower(new StrengthPower(monster, 1));
        AbstractDungeon.onModifyPower();
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public AbstractRelic makeCopy()
    {
        return new PhilosopherStone();
    }

    public static final String ID = "Philosopher's Stone";
    public static final int STR = 1;
}
