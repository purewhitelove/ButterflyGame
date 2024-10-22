// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Halt.java

package com.megacrit.cardcrawl.cards.purple;

import com.megacrit.cardcrawl.actions.watcher.HaltAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Halt extends AbstractCard
{

    public Halt()
    {
        super("Halt", cardStrings.NAME, "purple/skill/halt", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF);
        block = baseBlock = 3;
        magicNumber = baseMagicNumber = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        applyPowers();
        addToBot(new HaltAction(p, block, magicNumber));
    }

    public void applyPowers()
    {
        baseBlock += 6 + timesUpgraded * 4;
        baseMagicNumber = baseBlock;
        super.applyPowers();
        magicNumber = block;
        isMagicNumberModified = isBlockModified;
        baseBlock -= 6 + timesUpgraded * 4;
        super.applyPowers();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBlock(1);
            baseMagicNumber = baseBlock + 6 + timesUpgraded * 4;
            upgradedMagicNumber = upgradedBlock;
        }
    }

    public AbstractCard makeCopy()
    {
        return new Halt();
    }

    public static final String ID = "Halt";
    private static final CardStrings cardStrings;
    private static final int BLOCK_AMOUNT = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;
    private static final int BLOCK_DIFFERENCE = 6;
    private static final int UPGRADE_PLUS_BLOCK_DIFFERENCE = 4;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Halt");
    }
}
