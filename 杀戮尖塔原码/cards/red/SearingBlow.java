// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearingBlow.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

public class SearingBlow extends AbstractCard
{

    public SearingBlow()
    {
        this(0);
    }

    public SearingBlow(int upgrades)
    {
        super("Searing Blow", cardStrings.NAME, "red/attack/searing_blow", 2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 12;
        timesUpgraded = upgrades;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null)
            addToBot(new VFXAction(new SearingBlowEffect(m.hb.cX, m.hb.cY, timesUpgraded), 0.2F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade()
    {
        upgradeDamage(4 + timesUpgraded);
        timesUpgraded++;
        upgraded = true;
        name = (new StringBuilder()).append(cardStrings.NAME).append("+").append(timesUpgraded).toString();
        initializeTitle();
    }

    public boolean canUpgrade()
    {
        return true;
    }

    public AbstractCard makeCopy()
    {
        return new SearingBlow(timesUpgraded);
    }

    public static final String ID = "Searing Blow";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Searing Blow");
    }
}
