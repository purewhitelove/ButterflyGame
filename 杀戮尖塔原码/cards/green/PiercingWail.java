// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PiercingWail.java

package com.megacrit.cardcrawl.cards.green;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class PiercingWail extends AbstractCard
{

    public PiercingWail()
    {
        super("PiercingWail", cardStrings.NAME, "green/skill/piercing_wail", 1, cardStrings.DESCRIPTION, com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY);
        exhaust = true;
        baseMagicNumber = 6;
        magicNumber = baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if(Settings.FAST_MODE)
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        else
            addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        AbstractMonster mo;
        for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext(); addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -magicNumber), -magicNumber, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)))
            mo = (AbstractMonster)iterator.next();

        Iterator iterator1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AbstractMonster mo = (AbstractMonster)iterator1.next();
            if(!mo.hasPower("Artifact"))
                addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, magicNumber), magicNumber, true, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        } while(true);
    }

    public void upgrade()
    {
        if(!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }

    public AbstractCard makeCopy()
    {
        return new PiercingWail();
    }

    public static final String ID = "PiercingWail";
    private static final CardStrings cardStrings;

    static 
    {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("PiercingWail");
    }
}
