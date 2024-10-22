// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FTL.java

package com.megacrit.cardcrawl.cards.blue;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.defect.FTLAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class FTL extends AbstractCard
{

    public FTL()
    {
        super("FTL", cardStrings.NAME, "blue/attack/ftl", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 5;
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new FTLAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers()
    {
        int count;
        super.applyPowers();
        count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
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

    public void triggerOnGlowCheck()
    {
        glowColor = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= magicNumber ? AbstractCard.BLUE_BORDER_GLOW_COLOR : AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(1);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new FTL();
    }

    public static final String ID = "FTL";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FTL");
    }
}
