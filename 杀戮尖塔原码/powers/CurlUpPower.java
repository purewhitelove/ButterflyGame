// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CurlUpPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class CurlUpPower extends AbstractPower
{

    public CurlUpPower(AbstractCreature owner, int amount)
    {
        triggered = false;
        name = NAME;
        ID = "Curl Up";
        this.owner = owner;
        this.amount = amount;
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        loadRegion("closeUp");
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(!triggered && damageAmount < owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL)
        {
            flash();
            triggered = true;
            addToBot(new ChangeStateAction((AbstractMonster)owner, "CLOSED"));
            addToBot(new GainBlockAction(owner, owner, amount));
            addToBot(new RemoveSpecificPowerAction(owner, owner, "Curl Up"));
        }
        return damageAmount;
    }

    public static final String POWER_ID = "Curl Up";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    private boolean triggered;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Curl Up");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
