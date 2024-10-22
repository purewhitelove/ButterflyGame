// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GoForTheEyes.java

package com.megacrit.cardcrawl.cards.blue;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ForTheEyesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class GoForTheEyes extends AbstractCard
{

    public GoForTheEyes()
    {
        super("Go for the Eyes", cardStrings.NAME, "blue/attack/go_for_the_eyes", 0, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY);
        baseDamage = 3;
        baseMagicNumber = 1;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ForTheEyesAction(magicNumber, m));
    }

    public void triggerOnGlowCheck()
    {
        glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.isDeadOrEscaped() || m.getIntentBaseDmg() < 0)
                continue;
            glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            break;
        } while(true);
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeDamage(1);
            upgradeMagicNumber(1);
            upgradeName();
        }
    }

    public AbstractCard makeCopy()
    {
        return new GoForTheEyes();
    }

    public static final String ID = "Go for the Eyes";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Go for the Eyes");
    }
}
