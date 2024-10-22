// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Shiv.java

package com.megacrit.cardcrawl.cards.tempCards;

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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AccuracyPower;

public class Shiv extends AbstractCard
{

    public Shiv()
    {
        super("Shiv", cardStrings.NAME, "colorless/attack/shiv", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        if(AbstractDungeon.player != null && AbstractDungeon.player.hasPower("Accuracy"))
            baseDamage = 4 + AbstractDungeon.player.getPower("Accuracy").amount;
        else
            baseDamage = 4;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public AbstractCard makeCopy()
    {
        return new Shiv();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public static final String ID = "Shiv";
    private static final CardStrings cardStrings;
    public static final int ATTACK_DMG = 4;
    public static final int UPG_ATTACK_DMG = 2;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Shiv");
    }
}
