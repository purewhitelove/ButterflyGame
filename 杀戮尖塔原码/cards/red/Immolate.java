// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Immolate.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Immolate extends AbstractCard
{

    public Immolate()
    {
        super("Immolate", cardStrings.NAME, "red/attack/immolate", 2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseDamage = 21;
        isMultiDamage = true;
        cardsToPreview = new Burn();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(7);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Immolate();
    }

    public static final String ID = "Immolate";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Immolate");
    }
}
