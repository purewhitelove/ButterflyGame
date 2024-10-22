// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Feed.java

package com.megacrit.cardcrawl.cards.red;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
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

public class Feed extends AbstractCard
{

    public Feed()
    {
        super("Feed", cardStrings.NAME, "red/attack/feed", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 10;
        exhaust = true;
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
        tags.add(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null)
            addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        addToBot(new FeedAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Feed();
    }

    public static final String ID = "Feed";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Feed");
    }
}
