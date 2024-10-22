// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Impatience.java

package com.megacrit.cardcrawl.cards.colorless;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.ConditionalDrawAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Iterator;

public class Impatience extends AbstractCard
{

    public Impatience()
    {
        super("Impatience", cardStrings.NAME, "colorless/skill/impatience", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE);
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ConditionalDrawAction(magicNumber, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK));
    }

    public void triggerOnGlowCheck()
    {
        glowColor = shouldGlow() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private boolean shouldGlow()
    {
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
                return false;
        }

        return true;
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Impatience();
    }

    public static final String ID = "Impatience";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Impatience");
    }
}
