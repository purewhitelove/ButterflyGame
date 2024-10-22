// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDBalancedViolence.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.StanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.WrathStance;

public class DEPRECATEDBalancedViolence extends AbstractCard
{

    public DEPRECATEDBalancedViolence()
    {
        super("DEPRECATEDBalancedViolence", cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 7;
        baseBlock = 4;
        baseMagicNumber = 2;
        magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(m, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new StanceCheckAction("Wrath", new DamageAction(m, new DamageInfo(m, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)));
        addToBot(new StanceCheckAction("Calm", new DrawCardAction(magicNumber)));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDBalancedViolence();
    }

    public static final String ID = "DEPRECATEDBalancedViolence";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("DEPRECATEDBalancedViolence");
    }
}
