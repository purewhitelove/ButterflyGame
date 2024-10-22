// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Finisher.java

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

public class Finisher extends AbstractCard
{

    public Finisher()
    {
        super("Finisher", cardStrings.NAME, "green/attack/finisher", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamagePerAttackPlayedAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers()
    {
        int count;
        super.applyPowers();
        count = 0;
        Iterator iterator = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
                count++;
        } while(true);
        rawDescription = cardStrings.DESCRIPTION;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        rawDescription;
        append();
        cardStrings.EXTENDED_DESCRIPTION[0];
        append();
        count;
        append();
        toString();
        rawDescription;
        if(count != 1) goto _L2; else goto _L1
_L1:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        rawDescription;
        append();
        cardStrings.EXTENDED_DESCRIPTION[1];
        append();
        toString();
        rawDescription;
          goto _L3
_L2:
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        rawDescription;
        append();
        cardStrings.EXTENDED_DESCRIPTION[2];
        append();
        toString();
        rawDescription;
_L3:
        initializeDescription();
        return;
    }

    public void onMoveToDiscard()
    {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Finisher();
    }

    public static final String ID = "Finisher";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Finisher");
    }
}
