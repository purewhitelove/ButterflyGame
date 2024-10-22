// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegenerateMonsterPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class RegenerateMonsterPower extends AbstractPower
{

    public RegenerateMonsterPower(AbstractMonster owner, int regenAmt)
    {
        name = NAME;
        ID = "Regenerate";
        this.owner = owner;
        amount = regenAmt;
        updateDescription();
        loadRegion("regen");
        type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        flash();
        if(!owner.halfDead && !owner.isDying && !owner.isDead)
            addToBot(new HealAction(owner, owner, amount));
    }

    public static final String POWER_ID = "Regenerate";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Regenerate");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
