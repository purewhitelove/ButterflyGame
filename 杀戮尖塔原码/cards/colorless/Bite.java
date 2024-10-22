// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bite.java

package com.megacrit.cardcrawl.cards.colorless;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;

public class Bite extends AbstractCard
{

    public Bite()
    {
        super("Bite", cardStrings.NAME, "colorless/attack/bite", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null)
            if(Settings.FAST_MODE)
                addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.1F));
            else
                addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.3F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        addToBot(new HealAction(p, p, magicNumber));
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
        return new Bite();
    }

    public static final String ID = "Bite";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Bite");
    }
}
