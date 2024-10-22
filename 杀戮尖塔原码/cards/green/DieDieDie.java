// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DieDieDie.java

package com.megacrit.cardcrawl.cards.green;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.DieDieDieEffect;

public class DieDieDie extends AbstractCard
{

    public DieDieDie()
    {
        super("Die Die Die", cardStrings.NAME, "green/attack/die_die_die", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        exhaust = true;
        baseDamage = 13;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.LIGHT_GRAY)));
        addToBot(new VFXAction(new DieDieDieEffect(), 0.7F));
        addToBot(new ShakeScreenAction(0.0F, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH));
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeDamage(4);
        }
    }

    public AbstractCard makeCopy()
    {
        return new DieDieDie();
    }

    public static final String ID = "Die Die Die";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Die Die Die");
    }
}
