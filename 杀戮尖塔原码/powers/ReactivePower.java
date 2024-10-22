// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReactivePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class ReactivePower extends AbstractPower
{

    public ReactivePower(AbstractCreature owner)
    {
        name = NAME;
        ID = "Compulsive";
        this.owner = owner;
        updateDescription();
        loadRegion("reactive");
        priority = 50;
    }

    public void updateDescription()
    {
        description = DESCRIPTIONS[0];
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && damageAmount > 0 && damageAmount < owner.currentHealth)
        {
            flash();
            addToBot(new RollMoveAction((AbstractMonster)owner));
        }
        return damageAmount;
    }

    public static final String POWER_ID = "Compulsive";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Compulsive");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
