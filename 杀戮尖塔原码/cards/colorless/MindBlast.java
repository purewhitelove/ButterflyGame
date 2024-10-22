// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MindBlast.java

package com.megacrit.cardcrawl.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class MindBlast extends AbstractCard
{

    public MindBlast()
    {
        super("Mind Blast", cardStrings.NAME, "colorless/attack/mind_blast", 2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        isInnate = true;
        baseDamage = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers()
    {
        baseDamage = AbstractDungeon.player.drawPile.size();
        super.applyPowers();
        rawDescription = (new StringBuilder()).append(cardStrings.DESCRIPTION).append(cardStrings.EXTENDED_DESCRIPTION[0]).toString();
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        super.calculateCardDamage(mo);
        rawDescription = (new StringBuilder()).append(cardStrings.DESCRIPTION).append(cardStrings.EXTENDED_DESCRIPTION[0]).toString();
        initializeDescription();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeBaseCost(1);
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new MindBlast();
    }

    public static final String ID = "Mind Blast";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Mind Blast");
    }
}
