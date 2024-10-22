// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HeavyBlade.java

package com.megacrit.cardcrawl.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

public class HeavyBlade extends AbstractCard
{

    public HeavyBlade()
    {
        super("Heavy Blade", cardStrings.NAME, "red/attack/heavy_blade", 2, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 14;
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null)
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4F, m.hb.cY - m.hb.height / 4F)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyPowers()
    {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if(strength != null)
            strength.amount *= magicNumber;
        super.applyPowers();
        if(strength != null)
            strength.amount /= magicNumber;
    }

    public void calculateCardDamage(AbstractMonster mo)
    {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if(strength != null)
            strength.amount *= magicNumber;
        super.calculateCardDamage(mo);
        if(strength != null)
            strength.amount /= magicNumber;
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public AbstractCard makeCopy()
    {
        return new HeavyBlade();
    }

    public static final String ID = "Heavy Blade";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Heavy Blade");
    }
}
