// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheSpecimen.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class TheSpecimen extends AbstractRelic
{

    public TheSpecimen()
    {
        super("The Specimen", "the_specimen.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onMonsterDeath(AbstractMonster m)
    {
        if(m.hasPower("Poison"))
        {
            int amount = m.getPower("Poison").amount;
            if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
            {
                flash();
                addToBot(new RelicAboveCreatureAction(m, this));
                addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new PoisonPower(null, AbstractDungeon.player, amount), amount, false, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
            } else
            {
                logger.info("no target for the specimen");
            }
        }
    }

    public AbstractRelic makeCopy()
    {
        return new TheSpecimen();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/relics/TheSpecimen.getName());
    public static final String ID = "The Specimen";

}
