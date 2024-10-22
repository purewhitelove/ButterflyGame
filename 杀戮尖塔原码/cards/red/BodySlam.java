// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodySlam.java

package com.megacrit.cardcrawl.cards.red;

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

public class BodySlam extends AbstractCard
{

    public BodySlam()
    {
        super("Body Slam", cardStrings.NAME, "red/attack/body_slam", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        baseDamage = p.currentBlock;
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers()
    {
        baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        rawDescription;
        append();
        cardStrings.UPGRADE_DESCRIPTION;
        append();
        toString();
        rawDescription;
        initializeDescription();
        return;
    }

    public void onMoveToDiscard()
    {
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        rawDescription = cardStrings.DESCRIPTION;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        rawDescription;
        append();
        cardStrings.UPGRADE_DESCRIPTION;
        append();
        toString();
        rawDescription;
        initializeDescription();
        return;
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    public AbstractCard makeCopy()
    {
        return new BodySlam();
    }

    public static final String ID = "Body Slam";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Body Slam");
    }
}
