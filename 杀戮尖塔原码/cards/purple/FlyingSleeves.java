// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlyingSleeves.java

package com.megacrit.cardcrawl.cards.purple;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class FlyingSleeves extends AbstractCard
{

    public FlyingSleeves()
    {
        super("FlyingSleeves", cardStrings.NAME, "purple/attack/flying_sleeves", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 4;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(m != null)
        {
            addToBot(new SFXAction("ATTACK_WHIFF_2", 0.3F));
            addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            addToBot(new VFXAction(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30F * Settings.scale, 500F, 200F, 290F, 3F, Color.VIOLET, Color.PINK)));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        if(m != null)
        {
            addToBot(new SFXAction("ATTACK_WHIFF_1", 0.2F));
            addToBot(new SFXAction("ATTACK_FAST", 0.2F));
            addToBot(new VFXAction(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30F * Settings.scale, 500F, -200F, 250F, 3F, Color.VIOLET, Color.PINK)));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
    }

    public AbstractCard makeCopy()
    {
        return new FlyingSleeves();
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(2);
        }
    }

    public static final String ID = "FlyingSleeves";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FlyingSleeves");
    }
}
