// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BloodForBlood.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BloodForBlood extends AbstractCard
{

    public BloodForBlood()
    {
        super("Blood for Blood", cardStrings.NAME, "red/attack/blood_for_blood", 4, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 18;
    }

    public void tookDamage()
    {
        updateCost(-1);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            if(cost < 4)
            {
                upgradeBaseCost(cost - 1);
                if(cost < 0)
                    cost = 0;
            } else
            {
                upgradeBaseCost(3);
            }
            upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy()
    {
        AbstractCard tmp = new BloodForBlood();
        if(AbstractDungeon.player != null)
            tmp.updateCost(-AbstractDungeon.player.damagedThisCombat);
        return tmp;
    }

    public static final String ID = "Blood for Blood";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blood for Blood");
    }
}
