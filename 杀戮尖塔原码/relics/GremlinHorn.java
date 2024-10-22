// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinHorn.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class GremlinHorn extends AbstractRelic
{

    public GremlinHorn()
    {
        super("Gremlin Horn", "gremlin_horn.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
        energyBased = true;
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
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(DESCRIPTIONS[1]).toString();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onMonsterDeath(AbstractMonster m)
    {
        if(m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            flash();
            addToBot(new RelicAboveCreatureAction(m, this));
            addToBot(new GainEnergyAction(1));
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    public AbstractRelic makeCopy()
    {
        return new GremlinHorn();
    }

    public static final String ID = "Gremlin Horn";
}
