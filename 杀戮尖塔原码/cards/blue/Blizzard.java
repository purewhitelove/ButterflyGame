// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Blizzard.java

package com.megacrit.cardcrawl.cards.blue;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class Blizzard extends AbstractCard
{

    public Blizzard()
    {
        super("Blizzard", cardStrings.NAME, "blue/attack/blizzard", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        baseDamage = 0;
        baseMagicNumber = 2;
        magicNumber = baseMagicNumber;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int frostCount = 0;
        Iterator iterator = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o instanceof Frost)
                frostCount++;
        } while(true);
        baseDamage = frostCount * magicNumber;
        calculateCardDamage(null);
        if(Settings.FAST_MODE)
            addToBot(new VFXAction(new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        else
            addToBot(new VFXAction(new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
    }

    public void applyPowers()
    {
        int frostCount = 0;
        Iterator iterator = AbstractDungeon.actionManager.orbsChanneledThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o instanceof Frost)
                frostCount++;
        } while(true);
        if(frostCount > 0)
        {
            baseDamage = frostCount * magicNumber;
            super.applyPowers();
            rawDescription = (new StringBuilder()).append(cardStrings.DESCRIPTION).append(cardStrings.EXTENDED_DESCRIPTION[0]).toString();
            initializeDescription();
        }
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
        cardStrings.EXTENDED_DESCRIPTION[0];
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
            upgradeMagicNumber(1);
        }
    }

    public AbstractCard makeCopy()
    {
        return new Blizzard();
    }

    public static final String ID = "Blizzard";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Blizzard");
    }
}
