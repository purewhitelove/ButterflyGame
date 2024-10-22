// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StaticDischargePower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.Lightning;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class StaticDischargePower extends AbstractPower
{

    public StaticDischargePower(AbstractCreature owner, int lightningAmount)
    {
        name = powerStrings.NAME;
        ID = "StaticDischarge";
        this.owner = owner;
        amount = lightningAmount;
        updateDescription();
        loadRegion("static_discharge");
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != owner && damageAmount > 0)
        {
            flash();
            for(int i = 0; i < amount; i++)
                addToTop(new ChannelAction(new Lightning()));

        }
        return damageAmount;
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "StaticDischarge";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("StaticDischarge");
    }
}
