// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AngryPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower, StrengthPower

public class AngryPower extends AbstractPower
{

    public AngryPower(AbstractCreature owner, int attackAmount)
    {
        name = NAME;
        ID = "Angry";
        this.owner = owner;
        amount = attackAmount;
        updateDescription();
        isPostActionPower = true;
        loadRegion("anger");
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.owner != null && damageAmount > 0 && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS)
        {
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount));
            flash();
        }
        return damageAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[1]).append(amount).append(DESCRIPTIONS[2]).toString();
    }

    public static final String POWER_ID = "Angry";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Angry");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
