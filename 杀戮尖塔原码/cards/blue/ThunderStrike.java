// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThunderStrike.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import java.util.ArrayList;
import java.util.Iterator;

public class ThunderStrike extends AbstractCard
{

    public ThunderStrike()
    {
        super("Thunder Strike", cardStrings.NAME, "blue/attack/thunder_strike", 3, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseMagicNumber = 0;
        magicNumber = 0;
        baseDamage = 7;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        baseMagicNumber = 0;
        Iterator iterator = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o instanceof Lightning)
                baseMagicNumber++;
        } while(true);
        magicNumber = baseMagicNumber;
        for(int i = 0; i < magicNumber; i++)
            addToBot(new NewThunderStrikeAction(this));

    }

    public void applyPowers()
    {
        super.applyPowers();
        baseMagicNumber = 0;
        magicNumber = 0;
        Iterator iterator = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o instanceof Lightning)
                baseMagicNumber++;
        } while(true);
        if(baseMagicNumber > 0)
        {
            rawDescription = (new StringBuilder()).append(cardStrings.DESCRIPTION).append(cardStrings.EXTENDED_DESCRIPTION[0]).toString();
            initializeDescription();
        }
    }

    public void onMoveToDiscard()
    {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        if(baseMagicNumber > 0)
            rawDescription = (new StringBuilder()).append(cardStrings.DESCRIPTION).append(cardStrings.EXTENDED_DESCRIPTION[0]).toString();
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
        return new ThunderStrike();
    }

    public static final String ID = "Thunder Strike";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Thunder Strike");
    }
}
