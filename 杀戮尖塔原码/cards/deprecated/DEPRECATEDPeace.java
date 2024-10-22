// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDPeace.java

package com.megacrit.cardcrawl.cards.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class DEPRECATEDPeace extends AbstractCard
{

    public DEPRECATEDPeace()
    {
        super("Peace", cardStrings.NAME, null, 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseMagicNumber = 5;
        magicNumber = baseMagicNumber;
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractMonster mo;
        for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -magicNumber), -magicNumber, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)))
            mo = (AbstractMonster)iterator.next();

        Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AbstractMonster mo = (AbstractMonster)iterator1.next();
            if(!mo.hasPower("Artifact"))
                addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, magicNumber), magicNumber, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        } while(true);
    }

    public AbstractCard makeCopy()
    {
        return new DEPRECATEDPeace();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public static final String ID = "Peace";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Peace");
    }
}
