// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FireBreathingPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class FireBreathingPower extends AbstractPower
{

    public FireBreathingPower(AbstractCreature owner, int newAmount)
    {
        name = powerStrings.NAME;
        ID = "Fire Breathing";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("firebreathing");
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(powerStrings.DESCRIPTIONS[0]).append(amount).append(powerStrings.DESCRIPTIONS[1]).toString();
    }

    public void onCardDraw(AbstractCard card)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS || card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
        {
            flash();
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, true));
        }
    }

    public static final String POWER_ID = "Fire Breathing";
    private static final PowerStrings powerStrings;

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Fire Breathing");
    }
}
