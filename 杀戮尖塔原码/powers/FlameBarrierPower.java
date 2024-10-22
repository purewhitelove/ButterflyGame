// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlameBarrierPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class FlameBarrierPower extends AbstractPower
{

    public FlameBarrierPower(AbstractCreature owner, int thornsDamage)
    {
        name = NAME;
        ID = "Flame Barrier";
        this.owner = owner;
        amount = thornsDamage;
        updateDescription();
        loadRegion("flameBarrier");
    }

    public void stackPower(int stackAmount)
    {
        if(amount == -1)
        {
            logger.info((new StringBuilder()).append(name).append(" does not stack").toString());
            return;
        } else
        {
            fontScale = 8F;
            amount += stackAmount;
            updateDescription();
            return;
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.owner != owner)
        {
            flash();
            addToTop(new DamageAction(info.owner, new DamageInfo(owner, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        }
        return damageAmount;
    }

    public void atStartOfTurn()
    {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Flame Barrier"));
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/powers/FlameBarrierPower.getName());
    public static final String POWER_ID = "Flame Barrier";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Flame Barrier");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
