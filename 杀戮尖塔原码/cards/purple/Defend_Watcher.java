// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Defend_Watcher.java

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class Defend_Watcher extends AbstractCard
{

    public Defend_Watcher()
    {
        super("Defend_P", cardStrings.NAME, "purple/skill/defend", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        baseBlock = 5;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(Settings.isDebug)
            addToBot(new GainBlockAction(p, p, 50));
        else
            addToBot(new GainBlockAction(p, p, block));
    }

    public AbstractCard makeCopy()
    {
        return new Defend_Watcher();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBlock(3);
        }
    }

    public static final String ID = "Defend_P";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_P");
    }
}
