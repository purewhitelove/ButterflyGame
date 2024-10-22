// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BeatOfDeathPower.java

package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

// Referenced classes of package com.megacrit.cardcrawl.powers:
//            AbstractPower

public class BeatOfDeathPower extends AbstractPower
{

    public BeatOfDeathPower(AbstractCreature owner, int amount)
    {
        name = NAME;
        ID = "BeatOfDeath";
        this.owner = owner;
        this.amount = amount;
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
        loadRegion("beat");
        type = AbstractPower.PowerType.BUFF;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action)
    {
        flash();
        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, amount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        updateDescription();
    }

    public void updateDescription()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[0]).append(amount).append(DESCRIPTIONS[1]).toString();
    }

    public static final String POWER_ID = "BeatOfDeath";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];

    static 
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("BeatOfDeath");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
