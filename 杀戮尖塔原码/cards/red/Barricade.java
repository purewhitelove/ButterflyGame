// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Barricade.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import java.util.ArrayList;
import java.util.Iterator;

public class Barricade extends AbstractCard
{

    public Barricade()
    {
        super("Barricade", cardStrings.NAME, "red/power/barricade", 3, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        boolean powerExists = false;
        Iterator iterator = p.powers.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPower pow = (AbstractPower)iterator.next();
            if(!pow.ID.equals("Barricade"))
                continue;
            powerExists = true;
            break;
        } while(true);
        if(!powerExists)
            addToBot(new ApplyPowerAction(p, p, new BarricadePower(p)));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Barricade();
    }

    public static final String ID = "Barricade";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Barricade");
    }
}
